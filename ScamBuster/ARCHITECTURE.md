# ScamBuster Project Documentation

## Overview
ScamBuster is an Android application designed to help users identify, report, and track scams. The app provides tools for reporting suspicious activities and accessing a community database of known scams.

## Project Architecture

### MVVM + Repository Pattern
The project follows Android best practices with:
- **Models**: Data classes representing domain entities
- **Repository**: Single source of truth for data access
- **Activities**: UI layer presenting data to users
- **Utilities**: Helper classes for common operations

### Directory Structure
```
app/src/main/
├── java/com/scambuster/app/
│   ├── MainActivity.java                 # Entry point, navigation hub
│   ├── model/
│   │   └── ScamReport.java              # Scam report data model
│   ├── repository/
│   │   └── ScamReportRepository.java    # Data access and management
│   ├── util/
│   │   ├── ScamDetectionUtil.java       # Scam detection logic
│   │   └── DateUtil.java                # Date/time utilities
│   └── ui/activity/
│       ├── ReportScamActivity.java      # Form to report scams
│       └── ReportsListActivity.java     # View existing reports
└── res/
    ├── layout/
    │   ├── activity_main.xml            # Main screen
    │   └── activity/
    │       ├── activity_report_scam.xml
    │       └── activity_reports_list.xml
    └── values/
        ├── strings.xml
        ├── colors.xml
        └── themes.xml
```

## Key Features

### 1. Scam Detection Utility
- Identifies suspicious keywords commonly used in scams
- Validates email and phone number formats
- Calculates risk levels based on content analysis

### 2. Scam Reporting
- Users can submit detailed scam reports
- Include contact information (phone, email)
- Categorize scams (Phishing, Lottery, Tech Support, etc.)
- Rate risk level on a 1-5 scale

### 3. Reports Management
- View all submitted scam reports
- Filter by category or risk level
- In-memory data storage (ready for database integration)

### 4. Data Models

#### ScamReport
```java
- id: String                    // Unique identifier
- title: String                 // Scam title
- description: String           // Detailed description
- category: String              // Scam category
- phoneNumber: String           // Associated phone number
- email: String                 // Associated email
- reportDate: Date              // When report was created
- riskLevel: int (1-5)         // Risk severity
```

## Testing

### Unit Tests
Located in `app/src/test/java/com/scambuster/app/ExampleUnitTest.java`

Tests cover:
- Suspicious keyword detection
- Email validation
- Phone number validation
- Scam report creation
- Risk level validation

Run with:
```bash
./gradlew test
```

### Instrumented Tests
Located in `app/src/androidTest/java/com/scambuster/app/ExampleInstrumentedTest.java`

Tests cover:
- Package name verification
- Repository singleton pattern
- Report addition functionality
- High-risk report filtering

Run with:
```bash
./gradlew connectedAndroidTest
```

## Build Configuration

### Gradle Configuration
- **compileSdk**: 33
- **minSdk**: 21
- **targetSdk**: 33
- **Java Version**: 11

### Dependencies
- **AndroidX AppCompat**: UI compatibility
- **Material Design**: Modern UI components
- **ConstraintLayout**: Flexible layouts
- **JUnit 4**: Unit testing
- **Espresso**: UI testing

## Getting Started

### Prerequisites
- Android Studio 2022.1+
- Android SDK 33
- Java 11+

### Installation
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on emulator or device

### Building
```bash
./gradlew build
```

### Running Tests
```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

### Installing Debug APK
```bash
./gradlew installDebug
```

## Future Enhancements

### Planned Features
1. **Database Integration**
   - SQLite for local storage
   - Room persistence library

2. **Cloud Sync**
   - Firebase Realtime Database
   - Synchronize reports across devices

3. **Machine Learning**
   - Advanced scam detection
   - Pattern recognition

4. **User Accounts**
   - Authentication system
   - User profiles
   - Report history

5. **Advanced Filtering**
   - Date range filtering
   - Multiple category filtering
   - Search functionality

6. **Notifications**
   - Alert for high-risk reports
   - Community updates

## Code Style & Standards

### Naming Conventions
- Classes: PascalCase (e.g., `MainActivity`)
- Methods: camelCase (e.g., `loadReports()`)
- Constants: UPPER_SNAKE_CASE
- Variables: camelCase

### Documentation
- All public methods have JavaDoc comments
- Complex logic explained with inline comments
- README files in key directories

## Troubleshooting

### Common Issues

**Gradle Sync Fails**
- Check Java version (11+)
- Update Android SDK
- Clear .gradle directory

**Build Errors**
- Ensure compileSdk 33 is installed
- Check namespace in build.gradle
- Verify package names match

**Tests Failing**
- Check emulator/device Android version
- Ensure testing dependencies installed
- Review test logcat output

## License
Proprietary - ScamBuster

## Contributing
This is a proprietary project. For contributions, contact the maintainers.

## Support
For issues or questions, please contact the development team.
