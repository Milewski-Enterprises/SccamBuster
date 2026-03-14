# ScamBuster - Copilot Instructions

ScamBuster is an Android anti-scam reporting app using MVVM + Repository pattern for a mobile-first architecture.

## Architecture Overview

**MVVM + Repository Pattern**: Data flows from Activities → Repository → In-memory models. The `ScamReportRepository` is a singleton managing all scam reports with synchronized access.

- **Models** ([model/ScamReport.java](app/src/main/java/com/scambuster/app/model/ScamReport.java)): Domain entities with getters/setters
- **Repository** ([ScamReportRepository.java](app/src/main/java/com/scambuster/app/repository/ScamReportRepository.java)): Singleton pattern, thread-safe with Collections.synchronizedList()
- **Activities** (UI layer): Extend AppCompatActivity, handle view initialization and user interaction
- **Utilities**: Validation and detection logic separated in dedicated util classes

**Key Design Decision**: Repository uses in-memory storage (ready for Room/SQLite migration). Always use `ScamReportRepository.getInstance()` for data access - never bypass the repository.

## Project Structure & Conventions

```
app/src/main/java/com/scambuster/app/
├── MainActivity.java                    # Navigation hub - Intent-based routing
├── model/ScamReport.java               # 8 fields: id, title, description, category, phone, email, reportDate, riskLevel(1-5)
├── repository/ScamReportRepository.java # Singleton - getInstance()
├── ui/activity/                         # All Activities extend AppCompatActivity
│   ├── ReportScamActivity.java          # Form submission with validation
│   └── ReportsListActivity.java         # Display & filter reports
└── util/
    ├── ScamDetectionUtil.java           # Static validation & keyword detection
    └── DateUtil.java                    # Date formatting utilities

app/src/main/res/
├── layout/activity_main.xml             # Navigation buttons
└── layout/activity/*.xml                # Activity layouts (1 per activity)
```

**Naming Conventions**:
- Activities: `{Feature}Activity.java` (e.g., ReportScamActivity)
- Models: `{Entity}.java` (e.g., ScamReport)
- Utilities: `{Function}Util.java` (e.g., ScamDetectionUtil)
- Layouts: `activity_{snake_case}.xml`

## Core Workflows

### Adding a New Feature Activity

1. Create Java class in `ui/activity/` extending `AppCompatActivity`
2. Create XML layout in `res/layout/activity/` directory
3. Register in [AndroidManifest.xml](app/src/main/AndroidManifest.xml)
4. Add navigation Intent from [MainActivity.java](app/src/main/java/com/scambuster/app/MainActivity.java)
5. Use `ScamReportRepository.getInstance()` for data access

### Validation Pattern

Always validate user input before repository operations using [ScamDetectionUtil.java](app/src/main/java/com/scambuster/app/util/ScamDetectionUtil.java):
- `isValidEmail()` - OWASP regex pattern
- `isValidPhoneNumber()` - Allows +, spaces, dashes, 7-25 chars
- `containsSuspiciousKeywords()` - Case-insensitive keyword search
- `calculateRiskLevel()` - Returns 1-5 based on multiple factors

Input sanitization example from [ReportScamActivity.java](app/src/main/java/com/scambuster/app/ui/activity/ReportScamActivity.java#L88):
```java
String sanitized = input.trim();  // Remove whitespace
if (sanitized.isEmpty()) return false;  // Check empty
```

## Build & Test Commands

**Build:**
```bash
./gradlew build                    # Full build
./gradlew assembleDebug            # Debug APK
./gradlew build -x test            # Skip tests
```

**Test:**
```bash
./gradlew test                     # Unit tests (JUnit 4)
./gradlew connectedAndroidTest     # Instrumented tests (Espresso)
```

**Key Gradle Configuration**:
- compileSdk: 33 | minSdk: 21 | targetSdk: 33
- Java 11, ProGuard minification enabled for release
- Dependencies: AndroidX, Material Design, ConstraintLayout, JUnit, Espresso

## Critical Integration Points

1. **Data Flow**: Activities → Validation Util → Repository (singleton) → Models
2. **Navigation**: Intent-based (MainActivity acts as router)
3. **Concurrency**: Repository uses `Collections.synchronizedList()` for thread-safe access
4. **Scam Categories**: Fixed enum in [ReportScamActivity.java](app/src/main/java/com/scambuster/app/ui/activity/ReportScamActivity.java#L47) - update if adding categories

## Common Pitfalls

- **Don't create Repository instances** - Always use `getInstance()`
- **Don't bypass validation** - Use ScamDetectionUtil before repository operations
- **Don't forget AndroidManifest.xml** registration for new Activities
- **Don't use deprecated View binding** - Use findViewById (current approach in codebase)

## Testing Strategy

Unit tests validate:
- Keyword detection (case-insensitive)
- Email/phone regex patterns
- Risk level calculation (1-5 bounds)
- Report CRUD operations

Example test location: [ExampleUnitTest.java](app/src/test/java/com/scambuster/app/ExampleUnitTest.java)

## Database Implementation (Room ORM) - ✅ Phase 1 Complete

**Current State**: Room-backed SQLite database with known scammer verification. Local persistent storage with pre-populated sample data.

### Database Structure
- **ScammerRecord.java** ([database/entity/](app/src/main/java/com/scambuster/app/database/entity/)) - Entity with phone, email, name, reportCount, riskScore (0.0-1.0), lastSeen
- **ScammerDao.java** ([database/dao/](app/src/main/java/com/scambuster/app/database/dao/)) - Query methods: findByPhone(), findByEmail(), getAll(), getHighRiskScammers(), getByReportCount()
- **AppDatabase.java** ([database/](app/src/main/java/com/scambuster/app/database/)) - Singleton Room database with DateConverter
- **DateConverter.java** - Type converter for Java Date ↔ SQLite LONG timestamps

### Pre-Population (First Run)
On app initialization, database auto-populates with 5 sample known scammers in background thread:
```java
ScammerRecord scammer1 = new ScammerRecord(
    "+1-555-0100",          // phone
    "fake.lottery@...",     // email  
    "Lottery Scammer",      // name
    47,                     // reportCount
    0.95,                   // riskScore (0.0-1.0)
    new Date()              // lastSeen
);
```

### Verification Integration
**ScamDetectionUtil** enhancements:
- `isKnownScammer(phone, email)` - Returns ScammerRecord if match found
- `calculateRiskLevel(title, desc, phone, email, knownScammer)` - Returns 5 if scammer matched
- ⚠️ **Blocking calls** - Always run in background thread, never on main thread

**ReportScamActivity** real-time checks:
- onFocusChange listeners on phone/email fields
- Background thread triggers database lookup
- Displays warning banner with scammer name, report count, risk score
- Auto-sets risk rating to 5 stars on match

### Repository Migration
**ScamReportRepository** constructor now requires Context:
```java
// OLD: ScamReportRepository.getInstance()
// NEW: 
repository = ScamReportRepository.getInstance(context);
```

Database methods (blocking - use in background threads):
- `getScammerByPhone(phone)` / `getScammerByEmail(email)`
- `getAllKnownScammers()` / `getHighRiskScammers()`
- `getScammersByReportCount(minReports)`
- `addKnownScammer(scammer)` - Async insertion

### Gradle Dependencies
```gradle
implementation 'androidx.room:room-runtime:2.5.2'
annotationProcessor 'androidx.room:room-compiler:2.5.2'
testImplementation 'org.mockito:mockito-core:5.2.0'
androidTestImplementation 'androidx.room:room-testing:2.5.2'
```

### Database Schema
```sql
CREATE TABLE scammer_records (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    phone TEXT UNIQUE,
    email TEXT UNIQUE,
    name TEXT,
    reportCount INTEGER DEFAULT 0,
    riskScore REAL,
    lastSeen TIMESTAMP
);
CREATE INDEX idx_phone ON scammer_records(phone);
CREATE INDEX idx_email ON scammer_records(email);
```

### Testing
- Unit tests cover ScammerRecord creation, risk score clamping, scammer-based risk calculation
- Instrumented tests verify database initialization (5+ sample scammers), phone/email lookups, report count filtering
- Use `@Before` / `@After` with `Thread.sleep(500ms)` for async operation synchronization

### Phase 2: Admin API (Future)
- REST endpoints for CRUD operations on scammer records
- Background sync via WorkManager for periodic updates
- Admin UI Activity for managing known scammers
- See [PHASE_1_DATABASE_IMPLEMENTATION.md](PHASE_1_DATABASE_IMPLEMENTATION.md) for roadmap
