# ScamBuster Quick Commands Reference

## Build Commands

### Build Debug APK
```bash
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

### Build Release APK (ProGuard optimized)
```bash
./gradlew assembleRelease
```
Output: `app/build/outputs/apk/release/app-release.apk` (unsigned)

### Build Project
```bash
./gradlew build
```

### Clean Build
```bash
./gradlew clean build
```

### Fast Build (skips tests)
```bash
./gradlew build -x test
```

## Testing Commands

### Run All Tests
```bash
./gradlew test
```

### Run Specific Test
```bash
./gradlew test --tests "com.scambuster.app.ExampleUnitTest"
```

### Run Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Run Tests with Coverage
```bash
./gradlew testDebugCoverage
```

## Installation Commands

### Install Debug APK to Device
```bash
./gradlew installDebug
```

### Install and Run
```bash
./gradlew installDebug
adb shell am start -n com.scambuster.app/.MainActivity
```

### Uninstall App
```bash
adb uninstall com.scambuster.app
```

## Gradle Dependency Commands

### List All Dependencies
```bash
./gradlew dependencies
```

### Check for Dependency Updates
```bash
./gradlew dependencyUpdates
```

### View Dependency Tree
```bash
./gradlew app:dependencies --configuration releaseRuntimeClasspath
```

## ADB Commands

### List Connected Devices
```bash
adb devices
```

### View Device Logs
```bash
adb logcat
```

### Filter Logs by App
```bash
adb logcat | grep "scambuster\|ScamBuster"
```

### Clear Application Data
```bash
adb shell pm clear com.scambuster.app
```

### Restart ADB Server
```bash
adb kill-server
adb start-server
```

## Android Studio Commands

### Run App
`Run > Run 'app'` or `Shift + F10`

### Run Tests
`Run > Run...` then select test configuration

### Debug App
`Run > Debug 'app'` or `Shift + F9`

### Build Project
`Build > Make Project` or `Ctrl + F9`

### Clean Project
`Build > Clean Project`

### Sync Gradle
`File > Sync Now` or wait for auto-sync

## Code Analysis

### Run Lint
```bash
./gradlew lint
```

### Format Code
```bash
./gradlew spotlessApply
```

### Check Code Quality
```bash
./gradlew check
```

## Git Commands

### Check Git Status
```bash
git status
```

### Stage Changes
```bash
git add .
git add app/src/main/java/
```

### Commit
```bash
git commit -m "Feature: Add scam detection utility"
```

### Push to Remote
```bash
git push origin main
```

### View Branches
```bash
git branch -a
```

### Create New Branch
```bash
git checkout -b feature/new-feature
```

## Gradle Wrapper Update

### Update Gradle Version
```bash
./gradlew wrapper --gradle-version=8.0
```

## ProGuard/R8 Optimization

### Build with Debugging Symbols
```bash
./gradlew assembleRelease --no-optimize
```

### View ProGuard Mappings
Located in: `app/build/outputs/mapping/release/mapping.txt`

## Troubleshooting Commands

### Clear Gradle Cache
```bash
rm -rf ~/.gradle/caches
```
or on Windows:
```powershell
Remove-Item -Path $env:USERPROFILE\.gradle\caches -Recurse -Force
```

### Clear Android Studio Cache
```bash
rm -rf .gradle/
rm -rf .idea/
rm -rf build/
./gradlew clean
```

### Rebuild From Scratch
```bash
./gradlew clean build
```

### Update all Gradle Plugins
```bash
./gradlew wrapper --gradle-version latest
```

## Performance Profiling

### CPU Profiler (Android Studio)
`Run > Profile 'app'` → CPU

### Memory Profiler
`Run > Profile 'app'` → Memory

### Network Profiler
`Run > Profile 'app'` → Network

### Battery Profiler
`Run > Profile 'app'` → Energy

## Useful Gradle Tasks

### List All Tasks
```bash
./gradlew tasks
```

### Show Gradle Version
```bash
./gradlew --version
```

### Show Java Version Used
```bash
./gradlew --info | grep "java.version"
```

## One-Liners

### Build and Install Debug
```bash
./gradlew installDebug && adb shell am start -n com.scambuster.app/.MainActivity
```

### Clean and Build Release
```bash
./gradlew clean assembleRelease
```

### Run Full Test Suite
```bash
./gradlew clean build test connectedAndroidTest
```

### Build with All Checks
```bash
./gradlew clean build test lint
```

## Fast Development Loop

### Development Iteration
```bash
# Make code changes
./gradlew installDebug
# Test manually
adb shell pm clear com.scambuster.app
# Repeat
```

### Using Android Studio Instant Run
- Build with `Run 'app'` for faster iterations
- Supports code and resource changes without reinstall

## Documentation Links

- [Gradle Official Docs](https://gradle.org/)
- [Android Gradle Plugin](https://developer.android.com/build/releases/gradle-plugin)
- [ADB Commands](https://developer.android.com/studio/command-line/adb)

## Quick Tips

1. **Fast Gradle Builds**: Add to `~/.gradle/gradle.properties`
   ```
   org.gradle.jvmargs=-Xmx2048m
   org.gradle.parallel=true
   org.gradle.caching=true
   ```

2. **Instant Run**: Use Android Studio run button for faster development

3. **Build Cache**: Enabled by default for faster rebuilds

4. **Incremental Compilation**: Always on for faster builds

5. **Lint Baseline**: Create baseline to focus on new issues:
   ```bash
   ./gradlew lintDebugReportBaseline
   ```

---

**Note**: All commands assume you're in the project root directory where `gradlew` exists.
For Windows, use `gradlew.bat` instead of `./gradlew`
