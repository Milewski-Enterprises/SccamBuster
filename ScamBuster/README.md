# ScamBuster

ScamBuster is an Android application designed to help users identify and avoid scams.

## Project Structure

```
ScamBuster/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/scambuster/app/    # Main application source code
│   │   │   ├── res/                         # Resources (layouts, strings, colors)
│   │   │   └── AndroidManifest.xml         # App manifest
│   │   ├── test/                            # Unit tests
│   │   └── androidTest/                     # Instrumented tests
│   ├── build.gradle                         # App-level build config
│   └── proguard-rules.pro                   # ProGuard rules
├── build.gradle                             # Project-level build config
├── settings.gradle                          # Gradle settings
└── README.md                                # This file
```

## Prerequisites

- Android Studio 2022.1 or later
- Android SDK 33 (target SDK)
- Minimum SDK 21

## Building

To build the project:

```bash
./gradlew build
```

## Running Tests

**Unit Tests:**
```bash
./gradlew test
```

**Instrumented Tests:**
```bash
./gradlew connectedAndroidTest
```

## Installation

To install on a device or emulator:

```bash
./gradlew installDebug
```

## License

Proprietary - ScamBuster
