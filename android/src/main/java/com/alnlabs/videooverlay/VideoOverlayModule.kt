package com.alnlabs.videooverlay

import android.util.Log
import com.facebook.react.bridge.*
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.turbomodule.core.interfaces.TurboModule
import java.io.*

@ReactModule(name = VideoOverlayModule.NAME)
class VideoOverlayModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext), TurboModule {

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
      if (!ffmpegFile.exists()) return promise.reject("FFMPEG_MISSING", "FFmpeg binary is not bundled")
      ffmpegFile.setExecutable(true)

      val inputs = mutableListOf("-i", inputPath)
      val filterParts = mutableListOf<String>()
      var imageIndex = 1

      for (i in 0 until overlays.size()) {
        val overlay = overlays.getMap(i) ?: continue
        when (overlay.getString("type")) {
          "image" -> {
            val source = overlay.getString("source") ?: continue
            val width = overlay.getInt("width")
            val height = overlay.getInt("height")
            val opacity = overlay.getDouble("opacity")
            val (x, y) = getPosition(overlay)

            inputs.add("-i")
            inputs.add(source)

            val scaled = "[${imageIndex}:v]scale=${width}:${height}[img${i}]"
            val overlayCmd = "[0:v][img${i}]overlay=${x}:${y}:format=auto:alpha=${opacity}"
            filterParts.add("$scaled;$overlayCmd")

            imageIndex++
          }

          "text" -> {
            val text = overlay.getString("text") ?: ""
            val fontSize = if (overlay.hasKey("fontSize")) overlay.getInt("fontSize") else 24
            val color = overlay.getString("fontColor") ?: "white"
            val opacity = if (overlay.hasKey("opacity")) overlay.getDouble("opacity") else 1.0
            val font = overlay.getString("fontPath") ?: "/system/fonts/DroidSans.ttf"
            val (x, y) = getPosition(overlay)

            val draw = "drawtext=fontfile='$font':text='${text}':x=${x}:y=${y}:fontsize=$fontSize:fontcolor=${color}@${opacity}"
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

  private fun getPosition(overlay: ReadableMap): Pair<String, String> {
    // Handle custom position
    if (overlay.hasKey("position")) {
      val pos = overlay.getDynamic("position")
      if (pos.type == ReadableType.Map) {
        val map = pos.asMap()
        val x = map.getDouble("x").toInt()
        val y = map.getDouble("y").toInt()
        return Pair("$x", "$y")
      }

      val str = pos.asString()
      return when (str) {
        "top-left" -> Pair("10", "10")
        "top-center" -> Pair("(main_w-text_w)/2", "10")
        "top-right" -> Pair("main_w-overlay_w-10", "10")
        "center-left" -> Pair("10", "(main_h-text_h)/2")
        "center" -> Pair("(main_w-overlay_w)/2", "(main_h-overlay_h)/2")
        "center-right" -> Pair("main_w-overlay_w-10", "(main_h-overlay_h)/2")
        "bottom-left" -> Pair("10", "main_h-overlay_h-10")
        "bottom-center" -> Pair("(main_w-overlay_w)/2", "main_h-overlay_h-10")
        "bottom-right" -> Pair("main_w-overlay_w-10", "main_h-overlay_h-10")
        else -> Pair("10", "10") // fallback
      }
    }
    return Pair("10", "10") // default
  }
}