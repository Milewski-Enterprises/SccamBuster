@echo off
echo Building ScamBuster for preview...
echo.

echo [1/3] Building debug APK...
call gradlew assembleDebug

echo.
echo [2/3] Installing to connected device...
call gradlew installDebug

echo.
echo [3/3] Starting app...
adb shell am start -n com.scambuster.app/.ui.activity.MainActivity

echo.
echo ✅ ScamBuster is now running on your device!
echo APK location: app\build\outputs\apk\debug\app-debug.apk
pause