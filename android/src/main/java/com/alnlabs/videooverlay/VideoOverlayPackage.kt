package com.alnlabs.videooverlay

import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.uimanager.ViewManager

class VideoOverlayPackage : TurboReactPackage() {

  override fun getModule(name: String, context: ReactApplicationContext): NativeModule? {
    return when (name) {
      VideoOverlayModule.NAME -> VideoOverlayModule(context)
      else -> null
    }
  }

  override fun getReactModuleInfoProvider(): ReactModuleInfoProvider {
    return TurboReactPackage.getReactModuleInfoProviderForClass(VideoOverlayModule::class.java)
  }

  override fun createViewManagers(context: ReactApplicationContext): List<ViewManager<*, *>> {
    return emptyList()
  }
}
