// src/types.ts

export type PredefinedPosition =
  | 'top-left'
  | 'top-right'
  | 'bottom-left'
  | 'bottom-right'
  | 'center';

export type CustomPosition = {
  x: number;
  y: number;
};

export interface BaseOverlay {
  position?: PredefinedPosition | CustomPosition;
  opacity?: number;
}

export interface TextOverlay extends BaseOverlay {
  type: 'text';
  text: string;
  fontSize?: number;
  fontColor?: string;
}

export interface ImageOverlay extends BaseOverlay {
  type: 'image';
  source: string;
  width?: number;
  height?: number;
}

export type Overlay = TextOverlay | ImageOverlay;

export interface OverlayOptions {
  inputPath: string;
  outputPath: string;
  overlays: Overlay[];
  onProgress?: (line: string) => void;
}
