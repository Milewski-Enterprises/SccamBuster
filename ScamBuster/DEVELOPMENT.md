# ScamBuster Development Guide

## Getting Started

### Prerequisites
- Android Studio 2022.1 or later
- Android SDK 33
- Gradle 7.x or later
- Java 11 or later

### Initial Setup
1. Open the project in Android Studio
2. Sync Gradle dependencies: `File > Sync Now`
3. Build the project: `Build > Make Project`
4. Run on emulator: `Run > Run 'app'`

## Project Layout

### Main Source Code
- `app/src/main/java/com/scambuster/app/` - Application source code
- `app/src/main/res/` - Resources (layouts, strings, colors, etc.)

### Tests
- `app/src/test/java/` - Unit tests (JUnit)
- `app/src/androidTest/java/` - Instrumented tests (Android)

## Common Tasks

### Adding a New Activity
1. Create Java class in `ui/activity/` package
2. Extend `AppCompatActivity`
3. Create corresponding XML layout in `res/layout/`
4. Register in `AndroidManifest.xml`
5. Add navigation from `MainActivity`

### Adding a New Model
1. Create data class in `model/` package
2. Add getters/setters
3. Implement `Serializable` if needed
4. Update `ScamReportRepository` if applicable

### Adding Tests
1. Unit tests in `src/test/java/`
2. Instrumented tests in `src/androidTest/java/`
3. Run with `./gradlew test` or `./gradlew connectedAndroidTest`

## Build Variants

### Debug Build
```bash
./gradlew assembleDebug
```
Creates: `app/build/outputs/apk/debug/app-debug.apk`

### Release Build
```bash
./gradlew assembleRelease
```
Creates obfuscated APK with ProGuard rules

## Performance Optimization

### Current Optimizations
- Java 11 target for modern language features
- ProGuard/R8 code minification in release builds
- Efficient resource organization
- Repository pattern for data access

### Future Optimization Opportunities
- RecyclerView instead of ListView
- Data binding for layouts
- Background tasks with WorkManager
- Image caching for network requests

## Debugging

### Logcat Filtering
```bash
./gradlew logcat -e "scambuster|ScamBuster"
```

### Android Studio Debugger
1. Set breakpoints in code
2. Run in Debug mode: `Run > Debug 'app'`
3. Step through code with F10/F11

### Remote Debugging
1. Device must be in USB Debug mode
2. Connect via USB
3. Run: `adb devices` to verify connection

## Publishing Checklist

- [ ] Update version in `app/build.gradle`
- [ ] Update CHANGELOG
- [ ] Run full test suite
- [ ] Generate release notes
- [ ] Sign APK/AAB
- [ ] Upload to Play Store

## Dependencies Management

### Adding Dependencies
Edit `app/build.gradle`:
```gradle
dependencies {
    implementation 'group:artifact:version'
}
```
Then sync Gradle.

### Updating Dependencies
```bash
./gradlew dependencyUpdates
```

## Continuous Integration

### GitHub Actions (when configured)
- Builds on every push
- Runs all tests
- Generates APK
- Reports coverage

## Troubleshooting

### Clear Build Cache
```bash
./gradlew clean
```

### Rebuild from Scratch
```bash
./gradlew clean build
```

### Check Gradle Version
```bash
./gradlew --version
```

### Logcat Issues
Restart emulator and ensure logcat is showing all messages (not filtered)

## Resources

- [Android Developer Docs](https://developer.android.com/)
- [Android Architecture Guide](https://developer.android.com/guide/architecture)
- [Material Design Guidelines](https://material.io/design/)
- [Gradle Documentation](https://gradle.org/docs/)
