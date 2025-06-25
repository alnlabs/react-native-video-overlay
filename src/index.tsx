// src/index.ts

import { Platform } from 'react-native';
import VideoOverlayModule from './specs/VideoOverlaySpec'; // TurboModule binding
import type { OverlayOptions } from './specs/VideoOverlaySpec';

/**
 * Applies one or more overlays (text/image) to a video on Android using FFmpeg.
 *
 * @param options - Overlay configuration including input/output paths and overlay details.
 * @returns A Promise that resolves to the output path if successful.
 */
export function applyOverlay(options: OverlayOptions): Promise<string> {
  if (Platform.OS !== 'android') {
    return Promise.reject(
      new Error('react-native-video-overlay is only supported on Android')
    );
  }

  if (
    !VideoOverlayModule ||
    typeof VideoOverlayModule.applyOverlay !== 'function'
  ) {
    return Promise.reject(
      new Error(
        'VideoOverlay native module is not correctly linked or unavailable.'
      )
    );
  }

  return VideoOverlayModule.applyOverlay(options);
}
