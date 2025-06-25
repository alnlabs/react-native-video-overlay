# react-native-video-overlay

[![npm version](https://img.shields.io/npm/v/react-native-video-overlay.svg)](https://www.npmjs.com/package/react-native-video-overlay)
[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE)

> A lightweight and native **Android-only React Native TurboModule** for applying **image and text overlays** on videos using FFmpeg. Ideal for branding, watermarking, or field apps that need offline video post-processing.

---

## ✨ Features

- ⚡ Fast FFmpeg-based video overlay processing
- 🖼️ Add multiple **image and text overlays**
- 🎯 Define **predefined or custom positions** per overlay
- 📏 Control **width, height, font size**, **opacity**, etc.
- ✂️ Auto text **truncation** based on space
- 🎞️ Full **frame-safe rendering** using FFmpeg
- 📱 **Android only**, works offline

---

## 📦 Installation

```bash
yarn add react-native-video-overlay
# or
npm install react-native-video-overlay
```

---

## 🛠️ Setup

### 1. Add Permission in Android

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

### 2. No FFmpeg download needed

The FFmpeg binary is already bundled inside the native module. No manual setup required.

---

## 🚀 Usage

```ts
import { applyOverlay } from 'react-native-video-overlay';

const options = {
  inputPath: '/storage/emulated/0/DCIM/input.mp4',
  outputPath: '/storage/emulated/0/DCIM/output.mp4',
  overlays: [
    {
      type: 'image',
      source: '/storage/emulated/0/DCIM/logo.png',
      position: 'top-left',
      width: 100,
      height: 100,
      opacity: 0.9,
    },
    {
      type: 'text',
      text: 'ReviewDekho - Hyderabad',
      position: 'bottom-center',
      fontSize: 28,
      fontColor: 'white',
      opacity: 1.0,
    },
  ],
  onProgress: (line: string) => {
    console.log('[FFmpeg]', line);
  },
};

const resultPath = await applyOverlay(options);
console.log('Video saved at:', resultPath);
```

---

## 🎯 Overlay Position Options

- `'top-left'`
- `'top-right'`
- `'bottom-left'`
- `'bottom-right'`
- `'center'`
- `{ x: number, y: number }` – (Custom absolute position)

---

## 🔤 Text Overlay Options

- `text: string` – Required
- `fontSize?: number` – Default `24`
- `fontColor?: string` – Default `white`
- `opacity?: number` – From `0` to `1`

> Text will be truncated automatically if it overflows.

---

## 🖼️ Image Overlay Options

- `source: string` – File path to image (required)
- `width?: number` – Fit image to this width (optional)
- `height?: number` – Fit image to this height (optional)
- `opacity?: number` – 0 to 1

---

## 📞 Progress Callback

Optional function to stream FFmpeg progress:

```ts
onProgress?: (logLine: string) => void
```

You can use this to show a loading spinner or log raw output.

---

## 🧪 Requirements

- React Native `>= 0.71`
- Android only (no iOS support)

---

## 👨‍💻 Author

**ALN Labs** – [github.com/alnlabs](https://github.com/alnlabs)
Email: [alnlabs1@gmail.com](mailto:alnlabs1@gmail.com)

---

## 📄 License

MIT © [ALN Labs](https://github.com/alnlabs)

---

## 🌐 Links

- [NPM Package](https://www.npmjs.com/package/react-native-video-overlay)
- [GitHub Repository](https://github.com/alnlabs/react-native-video-overlay)
