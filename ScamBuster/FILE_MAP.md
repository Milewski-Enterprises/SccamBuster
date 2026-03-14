# ScamBuster - Project File Map

## 📍 Quick Navigation Guide

### 🎬 Getting Started
1. **First Time Setup**: Read [README.md](README.md)
2. **Architecture Overview**: Read [ARCHITECTURE.md](ARCHITECTURE.md)
3. **Development Setup**: Read [DEVELOPMENT.md](DEVELOPMENT.md)
4. **Quick Commands**: Refer to [COMMANDS.md](COMMANDS.md)

---

## 🗂️ File Location Guide

### Main Application Code
| File | Purpose | Package |
|------|---------|---------|
| [MainActivity.java](app/src/main/java/com/scambuster/app/MainActivity.java) | App entry point & navigation hub | `com.scambuster.app` |
| [ReportScamActivity.java](app/src/main/java/com/scambuster/app/ui/activity/ReportScamActivity.java) | Scam reporting form | `com.scambuster.app.ui.activity` |
| [ReportsListActivity.java](app/src/main/java/com/scambuster/app/ui/activity/ReportsListActivity.java) | View reports | `com.scambuster.app.ui.activity` |

### Data & Business Logic
| File | Purpose | Package |
|------|---------|---------|
| [ScamReport.java](app/src/main/java/com/scambuster/app/model/ScamReport.java) | Scam report data model | `com.scambuster.app.model` |
| [ScamReportRepository.java](app/src/main/java/com/scambuster/app/repository/ScamReportRepository.java) | Data access layer | `com.scambuster.app.repository` |

### Utilities
| File | Purpose | Package |
|------|---------|---------|
| [ScamDetectionUtil.java](app/src/main/java/com/scambuster/app/util/ScamDetectionUtil.java) | Scam detection logic | `com.scambuster.app.util` |
| [DateUtil.java](app/src/main/java/com/scambuster/app/util/DateUtil.java) | Date/time utilities | `com.scambuster.app.util` |

### User Interface Layouts
| File | Purpose | Used By |
|------|---------|---------|
| [activity_main.xml](app/src/main/res/layout/activity_main.xml) | Main screen layout | MainActivity |
| [activity_report_scam.xml](app/src/main/res/layout/activity/activity_report_scam.xml) | Scam report form layout | ReportScamActivity |
| [activity_reports_list.xml](app/src/main/res/layout/activity/activity_reports_list.xml) | Reports list layout | ReportsListActivity |

### Resources
| File | Purpose |
|------|---------|
| [strings.xml](app/src/main/res/values/strings.xml) | Text strings |
| [colors.xml](app/src/main/res/values/colors.xml) | Color palette |
| [themes.xml](app/src/main/res/values/themes.xml) | Material Design theme |
| [AndroidManifest.xml](app/src/main/AndroidManifest.xml) | App configuration |

### Testing
| File | Purpose | Type |
|------|---------|------|
| [ExampleUnitTest.java](app/src/test/java/com/scambuster/app/ExampleUnitTest.java) | Business logic tests | Unit Tests |
| [ExampleInstrumentedTest.java](app/src/androidTest/java/com/scambuster/app/ExampleInstrumentedTest.java) | Device/emulator tests | Instrumented Tests |

### Build Configuration
| File | Purpose |
|------|---------|
| [build.gradle](build.gradle) | Project-level build config |
| [app/build.gradle](app/build.gradle) | App-level build config |
| [settings.gradle](settings.gradle) | Module settings |
| [gradle.properties](gradle.properties) | Gradle properties |
| [proguard-rules.pro](app/proguard-rules.pro) | ProGuard rules |

### Documentation
| File | Purpose | Read For |
|------|---------|----------|
| [README.md](README.md) | Quick start | First-time users |
| [SETUP_COMPLETE.md](SETUP_COMPLETE.md) | Setup summary | Understanding what was done |
| [ARCHITECTURE.md](ARCHITECTURE.md) | Technical architecture | Deep dive into design |
| [DEVELOPMENT.md](DEVELOPMENT.md) | Development guide | How to develop features |
| [COMMANDS.md](COMMANDS.md) | Command reference | Build/test commands |
| [PROJECT_COMPLETION_REPORT.md](PROJECT_COMPLETION_REPORT.md) | Final verification | Project status |

### Configuration & Automation
| File | Purpose |
|------|---------|
| [.gitignore](.gitignore) | Git ignore patterns |
| [.github/workflows/build.yml](.github/workflows/build.yml) | CI/CD pipeline |

---

## 🔍 Finding What You Need

### Want to...

#### 🏃 Get Started Quickly?
→ Read [README.md](README.md)

#### 📚 Understand the Architecture?
→ Read [ARCHITECTURE.md](ARCHITECTURE.md)

#### 💻 Start Developing?
→ Read [DEVELOPMENT.md](DEVELOPMENT.md)

#### 🧪 Run Tests?
→ Check [COMMANDS.md](COMMANDS.md) → "Testing Commands"

#### 🔨 Build an APK?
→ Check [COMMANDS.md](COMMANDS.md) → "Build Commands"

#### 📱 Create a New Activity?
→ See [DEVELOPMENT.md](DEVELOPMENT.md) → "Adding a New Activity"

#### ✏️ Add a New Data Model?
→ See [DEVELOPMENT.md](DEVELOPMENT.md) → "Adding a New Model"

#### 🧪 Write a Test?
→ See [DEVELOPMENT.md](DEVELOPMENT.md) → "Adding Tests"

#### 🐛 Debug an Issue?
→ See [DEVELOPMENT.md](DEVELOPMENT.md) → "Debugging"

#### 📊 Check Project Status?
→ Read [PROJECT_COMPLETION_REPORT.md](PROJECT_COMPLETION_REPORT.md)

#### ⚙️ Configure Something?
→ Check [COMMANDS.md](COMMANDS.md) or relevant gradle file

---

## 📦 Package Structure

```
com.scambuster.app
├── MainActivity                    # Entry point
├── model
│   └── ScamReport                 # Data model
├── repository
│   └── ScamReportRepository       # Data access
├── ui.activity
│   ├── ReportScamActivity         # Report form
│   └── ReportsListActivity        # Report list
└── util
    ├── ScamDetectionUtil          # Scam detection
    └── DateUtil                   # Date utilities
```

---

## 🚀 Development Workflow

```
1. Make Code Changes
   ↓
2. Run Tests (./gradlew test)
   ↓
3. Build Debug APK (./gradlew assembleDebug)
   ↓
4. Install & Test (./gradlew installDebug)
   ↓
5. Fix Issues
   ↓
6. Commit Changes (git commit)
   ↓
7. Push to Remote (git push)
   ↓
8. GitHub Actions runs automated tests
   ↓
9. Build Release APK when ready (./gradlew assembleRelease)
```

---

## 📞 Common File Locations

### Where to add...

**New Activity?**
```
app/src/main/java/com/scambuster/app/ui/activity/YourActivity.java
app/src/main/res/layout/activity/activity_your.xml
```

**New Data Model?**
```
app/src/main/java/com/scambuster/app/model/YourModel.java
```

**New Utility Class?**
```
app/src/main/java/com/scambuster/app/util/YourUtil.java
```

**New Unit Test?**
```
app/src/test/java/com/scambuster/app/YourTest.java
```

**New Instrumented Test?**
```
app/src/androidTest/java/com/scambuster/app/YourTest.java
```

**New String Resource?**
```
app/src/main/res/values/strings.xml
```

**New Layout?**
```
app/src/main/res/layout/your_layout.xml
```

---

## 🔑 Key Files by Purpose

### Core Logic
- [ScamReport.java](app/src/main/java/com/scambuster/app/model/ScamReport.java) - Data model
- [ScamDetectionUtil.java](app/src/main/java/com/scambuster/app/util/ScamDetectionUtil.java) - Detection logic
- [ScamReportRepository.java](app/src/main/java/com/scambuster/app/repository/ScamReportRepository.java) - Data access

### User Interface
- [MainActivity.java](app/src/main/java/com/scambuster/app/MainActivity.java) - Main screen
- [ReportScamActivity.java](app/src/main/java/com/scambuster/app/ui/activity/ReportScamActivity.java) - Report form
- [ReportsListActivity.java](app/src/main/java/com/scambuster/app/ui/activity/ReportsListActivity.java) - Report list

### Configuration
- [AndroidManifest.xml](app/src/main/AndroidManifest.xml) - App permissions & activities
- [app/build.gradle](app/build.gradle) - Dependencies & build settings
- [settings.gradle](settings.gradle) - Module configuration

### Testing
- [ExampleUnitTest.java](app/src/test/java/com/scambuster/app/ExampleUnitTest.java) - Unit tests
- [ExampleInstrumentedTest.java](app/src/androidTest/java/com/scambuster/app/ExampleInstrumentedTest.java) - Instrumented tests

### Automation
- [.github/workflows/build.yml](.github/workflows/build.yml) - CI/CD pipeline

---

## 📊 File Statistics

| Type | Count | Bytes |
|------|-------|-------|
| Java Files | 8 | ~8,000 |
| XML Files | 7 | ~3,500 |
| Gradle Files | 3 | ~2,000 |
| Documentation | 5 | ~30,000 |
| Configuration | 4 | ~1,500 |
| **Total** | **29** | **~45,000** |

---

## 🎯 Navigation Tips

1. **Use IDE Search**: `Ctrl+Shift+F` to find code across project
2. **Use IDE Go To**: `Ctrl+G` to jump to line numbers
3. **Use IDE Go To Class**: `Ctrl+N` to find classes
4. **Use IDE Find Usages**: `Ctrl+Alt+F7` to find where class/method is used

---

## ✅ Checklist for Common Tasks

### Before Starting Development
- [ ] Open project in Android Studio
- [ ] Sync Gradle (File > Sync Now)
- [ ] Build project (Build > Make Project)
- [ ] Run tests (./gradlew test)
- [ ] Launch on emulator/device

### When Adding Features
- [ ] Follow package structure
- [ ] Add tests for new code
- [ ] Update documentation
- [ ] Run full test suite
- [ ] Commit changes

### Before Deployment
- [ ] All tests pass
- [ ] Build succeeds
- [ ] No lint warnings
- [ ] Version updated
- [ ] Documentation complete

---

## 🚀 You're All Set!

This file map should help you navigate the project quickly. Each file is well-organized and serves a specific purpose.

**Happy coding!** 🎉

---

*Last Updated: January 4, 2026*
*Project Version: 1.0*
