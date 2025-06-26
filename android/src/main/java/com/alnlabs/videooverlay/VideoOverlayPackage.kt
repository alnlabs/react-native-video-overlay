package com.alnlabs.videooverlay

import com.facebook.react.TurboReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
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
    return ReactModuleInfoProvider {
      val moduleInfoMap = HashMap<String, ReactModuleInfo>()
      val moduleName = VideoOverlayModule.NAME

      val moduleInfo = ReactModuleInfo(
        moduleName,
        VideoOverlayModule::class.java.name,
        /* canOverrideExistingModule */ false,
        /* needsEagerInit */ false,
        /* hasConstants */ true,
        /* isCxxModule */ false,
        /* isTurboModule */ true
      )
      moduleInfoMap[moduleName] = moduleInfo
      moduleInfoMap
    }
  }

  override fun createViewManagers(context: ReactApplicationContext): List<ViewManager<*, *>> {
    return emptyList()
  }
}