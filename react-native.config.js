// react-native.config.js
module.exports = {
  dependencies: {
    'react-native-video-overlay': {
      root: __dirname,
    },
  },
  project: {
    android: {
      sourceDir: './android',
    },
    ios: {
      sourceDir: './ios',
    },
  },
};
