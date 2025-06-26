# react-native-video-overlay

[![npm version](https://img.shields.io/npm/v/react-native-video-overlay.svg)](https://www.npmjs.com/package/react-native-video-overlay)
[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE)

> A lightweight and native **Android-only React Native TurboModule** for applying **image and text overlays** on videos using FFmpeg. Ideal for branding, watermarking, or offline field use cases.

---

## ⚠️ Experimental Notice

> This library is currently **under testing** and may not be fully stable for production.
> Use at your own risk. We **do not guarantee correctness or provide support** at this stage.
> **External contributions are not being accepted** currently.

---

## ✨ Features

- ⚡ Fast FFmpeg-based video overlay processing
- 🖼️ Add multiple **image and text overlays**
- 🎯 Define **predefined or custom positions** per overlay
- 🖏️ Control **width, height, font size, opacity**, and more
- ✂️ Text **truncation** support based on space
- 🎮 Frame-safe overlay rendering
- 📱 **Android only**, offline-ready

---

## 📦 Installation

```bash
yarn add react-native-video-overlay
# or
npm install react-native-video-overlay
```

---

## 🛠️ Setup

### 1. Android Permissions

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

### 2. FFmpeg Binary Bundled

FFmpeg is included in the native module — no extra download or setup needed.

---

## 🚀 Usage Example

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

Use predefined string positions or custom coordinates:

- `'top-left'`
- `'top-center'`
- `'top-right'`
- `'center-left'`
- `'center'`
- `'center-right'`
- `'bottom-left'`
- `'bottom-center'`
- `'bottom-right'`
- `{ x: number, y: number }` – Custom absolute position

---

## 🔤 Text Overlay Options

| Property    | Type          | Description                    |
| ----------- | ------------- | ------------------------------ |
| `text`      | string        | **Required.** The overlay text |
| `fontSize`  | number        | Optional. Defaults to `24`     |
| `fontColor` | string        | Optional. Defaults to `white`  |
| `opacity`   | number        | Optional. Range: `0` to `1`    |
| `position`  | string/object | Predefined or custom position  |

> Text is automatically truncated if it exceeds layout bounds.

---

## 🖼️ Image Overlay Options

| Property   | Type          | Description                   |
| ---------- | ------------- | ----------------------------- |
| `source`   | string        | **Required.** Image file path |
| `width`    | number        | Optional. Image width         |
| `height`   | number        | Optional. Image height        |
| `opacity`  | number        | Optional. Range: `0` to `1`   |
| `position` | string/object | Predefined or custom position |

---

## 📞 Progress Callback

To stream FFmpeg output lines (for loading state, logging, etc.):

```ts
onProgress?: (logLine: string) => void;
```

---

## 🧪 Requirements

- React Native `>= 0.71`
- Android only (no iOS support)

---

## 👨‍💻 Author

**ALN Labs**
GitHub: [@alnlabs](https://github.com/alnlabs)
Email: [alnlabs1@gmail.com](mailto:alnlabs1@gmail.com)

---

## 📄 License

MIT © [ALN Labs](https://github.com/alnlabs)

---

## 🌐 Links

- [NPM Package](https://www.npmjs.com/package/react-native-video-overlay)
- [GitHub Repository](https://github.com/alnlabs/react-native-video-overlay)
