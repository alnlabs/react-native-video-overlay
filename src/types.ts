// src/types.ts

export type PredefinedPosition =
  | 'top-left'
  | 'top-center'
  | 'top-right'
  | 'center-left'
  | 'center'
  | 'center-right'
  | 'bottom-left'
  | 'bottom-center'
  | 'bottom-right';

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
  fontPath?: string;
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
