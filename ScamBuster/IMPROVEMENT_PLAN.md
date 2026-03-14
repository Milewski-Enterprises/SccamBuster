# Focus Chain List for Task 1767569795138

<!-- Edit this markdown file to update your focus chain list -->
<!-- Use the format: - [ ] for incomplete items and - [x] for completed items -->

- [x] **Refactor `ScamReportRepository`**
  - [x] Implement Singleton pattern to ensure a single source of truth for reports.
  - [x] Replace `ArrayList` with a thread-safe collection or use `Collections.synchronizedList`.
  - [x] Use `UUID` for ID generation instead of `System.currentTimeMillis()`.

- [x] **Refactor `ScamDetectionUtil`**
  - [x] Improve Email Regex (use `android.util.Patterns` or a more robust regex).
  - [x] Improve Phone Regex to be more strict.
  - [x] Optimize `containsSuspiciousKeywords` (avoid creating new strings in loop).

- [x] **Refactor `ReportScamActivity`**
  - [x] Fix `ScamReportRepository` access (use `getInstance()`).
  - [x] Implement input validation using `ScamDetectionUtil` before submitting.
  - [ ] Extract hardcoded strings (categories, toast messages) to `strings.xml`.
  - [ ] (Optional) Migrate to ViewBinding.

- [ ] **General Code Quality**
  - [ ] Add Javadoc to missing methods.
  - [ ] Run linter/formatter.
