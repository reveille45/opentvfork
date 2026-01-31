# Open TV Fork

A fork of [Open TV](https://github.com/Fredolx/open-tv) with Android APK support.

This fork adds full Android support, allowing you to run Open TV on your Android phone or tablet. Videos are played using external video players (VLC, MX Player, etc.).

## Download

**Android APK**: [Download from Releases](https://github.com/reveille45/opentvfork/releases)

For the official desktop versions (Windows, macOS, Linux), visit the [original project](https://github.com/Fredolx/open-tv).

## Android Installation

1. Download the APK from the Releases page
2. Enable "Install from unknown sources" in your Android settings
3. Install the APK
4. Install a video player app (VLC, MX Player, or any app that handles video streams)

When you tap a channel, the app will open your default video player with the stream URL.

## Features

- Import your IPTV channels from any source (M3U File, M3U link, Xtream)
- Multi IPTV sources
- Super low RAM usage, crazy speeds, and instant search
- Refresh your sources when you need it
- Add channels to favorites
- Make your own custom channels

## Building the Android APK

### Prerequisites

- Node.js and npm
- Rust with Android targets (`rustup target add aarch64-linux-android`)
- Android SDK and NDK
- Java JDK 17

### Build Steps

```bash
# Install dependencies
npm install

# Build the Tauri Android app
npm run tauri android build -- --target aarch64

# On Windows, you may need to manually copy the .so file and build with Gradle
cd src-tauri/gen/android
./gradlew assembleArm64Release
```

The APK will be in `src-tauri/gen/android/app/build/outputs/apk/arm64/release/`.

## Desktop Prerequisites

If you want to build/run the desktop version, see the [original project](https://github.com/Fredolx/open-tv) for dependencies (mpv, ffmpeg, yt-dlp).

## Credits

This is a fork of [Open TV by Fredolx](https://github.com/Fredolx/open-tv). All credit for the original application goes to the original author.

If you find this useful, please consider [supporting the original developer](https://github.com/sponsors/Fredolx).

## License

Same license as the original project - see [LICENSE](LICENSE).
