package com.alnlabs.videooverlay

import android.content.res.AssetManager
import android.util.Log
import com.facebook.react.bridge.*
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.turbomodule.core.interfaces.TurboModule
import java.io.*

@ReactModule(name = VideoOverlayModule.NAME)
class VideoOverlayModule(
  reactContext: ReactApplicationContext
) : ReactContextBaseJavaModule(reactContext), TurboModule {

  companion object {
    const val NAME = "VideoOverlay"
    private const val TAG = "VideoOverlayModule"
    private const val FFMPEG_FILENAME = "ffmpeg"
  }

  override fun getName(): String = NAME

  @ReactMethod
  fun applyOverlay(options: ReadableMap, promise: Promise) {
    try {
      val inputPath = options.getString("inputPath") ?: return promise.reject("NO_INPUT", "Missing inputPath")
      val outputPath = options.getString("outputPath") ?: return promise.reject("NO_OUTPUT", "Missing outputPath")
      val overlays = options.getArray("overlays") ?: return promise.reject("NO_OVERLAYS", "At least one overlay is required")

      val ffmpegFile = File(reactApplicationContext.filesDir, FFMPEG_FILENAME)
      if (!ffmpegFile.exists()) {
        return promise.reject("FFMPEG_MISSING", "FFmpeg binary is not bundled")
      }
      ffmpegFile.setExecutable(true)

      val inputs = mutableListOf("-i", inputPath)
      val filterParts = mutableListOf<String>()

      // Track input indices for images
      var imageIndex = 1
      for (i in 0 until overlays.size()) {
        val overlay = overlays.getMap(i) ?: continue
        when (overlay.getString("type")) {
          "image" -> {
            val path = overlay.getString("path") ?: continue
            val x = overlay.getInt("x")
            val y = overlay.getInt("y")
            val w = overlay.getInt("width")
            val h = overlay.getInt("height")
            val opacity = overlay.getDouble("opacity")

            inputs.add("-i")
            inputs.add(path)

            val overlayFilter = "[${imageIndex}:v]scale=${w}:${h}[img${i}];[0:v][img${i}]overlay=${x}:${y}:format=auto:alpha=${opacity}"
            filterParts.add(overlayFilter)
            imageIndex++
          }

          "text" -> {
            val text = overlay.getString("text") ?: ""
            val x = overlay.getInt("x")
            val y = overlay.getInt("y")
            val fontSize = overlay.getInt("fontSize")
            val color = overlay.getString("color") ?: "white"
            val font = "/system/fonts/DroidSans.ttf"

            val draw = "drawtext=fontfile='$font':text='${text}':x=${x}:y=${y}:fontsize=${fontSize}:fontcolor=${color}"
            filterParts.add(draw)
          }
        }
      }

      val cmd = mutableListOf(ffmpegFile.absolutePath, "-y") + inputs +
        listOf("-filter_complex", filterParts.joinToString(","), "-codec:a", "copy", outputPath)

      Log.d(TAG, "Running FFmpeg: ${cmd.joinToString(" ")}")

      val process = ProcessBuilder(cmd).redirectErrorStream(true).start()
      val reader = BufferedReader(InputStreamReader(process.inputStream))
      var line: String?
      while (reader.readLine().also { line = it } != null) {
        Log.d(TAG, line ?: "")
      }

      val exitCode = process.waitFor()
      if (exitCode == 0) {
        promise.resolve(outputPath)
      } else {
        promise.reject("FFMPEG_FAILED", "Exited with code $exitCode")
      }
    } catch (e: Exception) {
      Log.e(TAG, "applyOverlay failed", e)
      promise.reject("FFMPEG_ERROR", e.message, e)
    }
  }
}
