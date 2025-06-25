// src/specs/VideoOverlaySpec.ts

import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface OverlayOptions {
  inputPath: string;
  outputPath: string;
  overlays: {
    type: 'image' | 'text';
    source?: string; // For image
    text?: string; // For text
    position?: string; // 'top-left', 'center', 'bottom-right', or custom
    x?: number; // Optional manual x
    y?: number; // Optional manual y
    width?: number; // For image scaling
    height?: number;
    fontSize?: number;
    fontColor?: string;
    opacity?: number; // 0–1
  }[];
}

export interface Spec extends TurboModule {
  applyOverlay(options: OverlayOptions): Promise<string>; // Now resolves outputPath
}

export default TurboModuleRegistry.getEnforcing<Spec>('VideoOverlay');
