// src/specs/VideoOverlaySpec.ts

import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export type OverlayPosition =
  | 'top-left'
  | 'top-right'
  | 'bottom-left'
  | 'bottom-right'
  | 'center'
  | { x: number; y: number }; // custom position

export type ImageOverlay = {
  type: 'image';
  source: string;
  width?: number;
  height?: number;
  position?: OverlayPosition;
  opacity?: number; // 0 to 1
};

export type TextOverlay = {
  type: 'text';
  text: string;
  fontSize?: number;
  fontColor?: string;
  position?: OverlayPosition;
  fontPath?: string;
  opacity?: number; // 0 to 1
};

export type OverlayOptions = {
  inputPath: string;
  outputPath: string;
  overlays: Array<ImageOverlay | TextOverlay>; // ✅ at least one overlay required
  onProgress?: (log: string) => void; // optional callback for progress logs
};

export interface Spec extends TurboModule {
  applyOverlay(options: OverlayOptions): Promise<string>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('VideoOverlay');
