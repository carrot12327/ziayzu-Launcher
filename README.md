# Minecraft Java Edition Launcher for Android

A powerful Android launcher for Minecraft Java Edition, similar to PojavLauncher, with automatic APK building via GitHub Actions.

## Features

- 📱 **Full Android Support** - Run Minecraft Java Edition on your Android device
- 🎮 **Touch Controls** - Optimized touch interface for mobile gameplay
- 📦 **Version Management** - Download and manage multiple Minecraft versions
- ⚙️ **Customizable Settings** - Graphics, controls, and performance options
- 🔧 **Automatic Building** - GitHub Actions automatically builds APK files
- 🎯 **Modern UI** - Clean, dark-themed interface designed for mobile

## Installation

### Option 1: Download from Releases
1. Go to the [Releases page](../../releases)
2. Download the latest APK file
3. Enable "Unknown sources" in your Android settings
4. Install the APK
5. Grant storage permissions when prompted

### Option 2: Build from Source
1. Clone this repository
2. Open in Android Studio
3. Build and run on your device

## Usage

1. **Launch the app** and grant necessary permissions
2. **Select a Minecraft version** from the list
3. **Configure settings** if needed (graphics, controls)
4. **Tap "Launch Game"** to start Minecraft
5. **Use touch controls** to play the game

## Requirements

- Android 5.0 (API level 21) or higher
- At least 2GB of RAM recommended
- 4GB of free storage space
- ARM64, ARMv7, x86, or x86_64 processor

## Architecture

The launcher is built with:
- **Java** - Core application logic
- **OpenGL ES** - 3D rendering engine
- **Android SDK** - Platform integration
- **Gradle** - Build system
- **GitHub Actions** - Automated CI/CD

## Development

### Project Structure
```
app/
├── src/main/java/com/minecraft/launcher/
│   ├── MainActivity.java          # Main launcher interface
│   ├── GameActivity.java          # Game rendering activity
│   ├── game/                      # Game engine components
│   ├── download/                  # Version management
│   ├── models/                    # Data models
│   └── adapters/                  # UI adapters
├── src/main/res/                  # Android resources
└── build.gradle                   # App build configuration
```

### Building

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

## GitHub Actions

This project includes automated APK building:

1. **Push to main/master** triggers automatic builds
2. **APK artifacts** are generated for both debug and release
3. **Releases** are automatically created with downloadable APKs
4. **Multiple architectures** are supported (ARM64, ARMv7, x86, x86_64)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is for educational purposes. Minecraft is a trademark of Mojang Studios.

## Disclaimer

This launcher is an independent project and is not affiliated with or endorsed by Mojang Studios or Microsoft. Users must own a legitimate copy of Minecraft Java Edition to use this launcher.

## Support

- Create an issue for bug reports
- Check existing issues before reporting
- Provide device info and logs when reporting problems

---

**Note:** This launcher requires a valid Minecraft account and respects Mojang's terms of service.