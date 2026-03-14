package com.scambuster.app.repository;

import android.content.Context;
import com.scambuster.app.model.ScamReport;
import com.scambuster.app.database.AppDatabase;
import com.scambuster.app.database.dao.ScammerDao;
import com.scambuster.app.database.entity.ScammerRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Repository class for managing scam reports data.
 * Uses Room database for persistent storage with offline-first approach.
 * Maintains singleton pattern for centralized data access.
 */
public class ScamReportRepository {
    private static ScamReportRepository instance;
    private final List<ScamReport> reports;
    private AppDatabase database;
    private ScammerDao scammerDao;
    private Context context;

    private ScamReportRepository(Context context) {
        this.context = context.getApplicationContext();
        this.reports = Collections.synchronizedList(new ArrayList<>());
        this.database = AppDatabase.getInstance(this.context);
        this.scammerDao = database.scammerDao();
        initializeDatabaseIfEmpty();
        initializeSampleReports();
    }

    public static synchronized ScamReportRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ScamReportRepository(context);
        }
        return instance;
    }

    /**
     * Legacy getInstance() for backward compatibility.
     * Note: Should use getInstance(context) instead.
     */
    public static synchronized ScamReportRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException(
                "ScamReportRepository not initialized. Call getInstance(context) first.");
        }
        return instance;
    }

    /**
     * Add a new scam report.
     */
    public void addReport(ScamReport report) {
        if (report != null) {
            report.setId(UUID.randomUUID().toString());
            reports.add(report);
        }
    }

    /**
     * Get all scam reports.
     */
    public List<ScamReport> getAllReports() {
        return new ArrayList<>(reports);
    }

    /**
     * Get reports by category.
     */
    public List<ScamReport> getReportsByCategory(String category) {
        List<ScamReport> filtered = new ArrayList<>();
        for (ScamReport report : reports) {
            if (category.equalsIgnoreCase(report.getCategory())) {
                filtered.add(report);
            }
        }
        return filtered;
    }

    /**
     * Get high-risk reports (risk level >= 4).
     */
    public List<ScamReport> getHighRiskReports() {
        List<ScamReport> filtered = new ArrayList<>();
        for (ScamReport report : reports) {
            if (report.getRiskLevel() >= 4) {
                filtered.add(report);
            }
        }
        return filtered;
    }

    /**
     * Delete a report by ID.
     */
    public boolean deleteReport(String reportId) {
        return reports.removeIf(report -> report.getId().equals(reportId));
    }

    /**
     * Get total report count.
     */
    public int getTotalReportCount() {
        return reports.size();
    }

    /**
     * Database operations for scammer records
     */

    /**
     * Add known scammer to database.
     */
    public void addKnownScammer(ScammerRecord scammer) {
        new Thread(() -> scammerDao.insert(scammer)).start();
    }

    /**
     * Get known scammer by phone (blocking call - use in background thread).
     */
    public ScammerRecord getScammerByPhone(String phone) {
        return scammerDao.findByPhone(phone);
    }

    /**
     * Get known scammer by email (blocking call - use in background thread).
     */
    public ScammerRecord getScammerByEmail(String email) {
        return scammerDao.findByEmail(email);
    }

    /**
     * Get all known scammers (blocking call - use in background thread).
     */
    public List<ScammerRecord> getAllKnownScammers() {
        return scammerDao.getAll();
    }

    /**
     * Get high-risk known scammers (blocking call - use in background thread).
     */
    public List<ScammerRecord> getHighRiskScammers() {
        return scammerDao.getHighRiskScammers();
    }

    /**
     * Get known scammers with minimum report count (blocking call).
     */
    public List<ScammerRecord> getScammersByReportCount(int minReports) {
        return scammerDao.getByReportCount(minReports);
    }

    /**
     * Initialize scammer database with sample known scammers if empty.
     */
    private void initializeDatabaseIfEmpty() {
        new Thread(() -> {
            if (scammerDao.getTotalCount() == 0) {
                // Pre-populate with known scammer patterns
                ScammerRecord scammer1 = new ScammerRecord(
                        "+1-555-0100",
                        "fake.lottery@suspicious.com",
                        "Lottery Scammer Network",
                        47,
                        0.95,
                        new Date()
                );
                scammerDao.insert(scammer1);

                ScammerRecord scammer2 = new ScammerRecord(
                        "+1-555-0101",
                        "tech.support.fake@malicious.net",
                        "Tech Support Fraud",
                        62,
                        0.95,
                        new Date()
                );
                scammerDao.insert(scammer2);

                ScammerRecord scammer3 = new ScammerRecord(
                        "+44-20-7946-0958",
                        "nigerian.prince@scam.org",
                        "Nigerian Prince Scams",
                        153,
                        0.90,
                        new Date()
                );
                scammerDao.insert(scammer3);

                ScammerRecord scammer4 = new ScammerRecord(
                        null,
                        "phishing.attack@fake-bank.com",
                        "Phishing Campaign 2024",
                        89,
                        0.92,
                        new Date()
                );
                scammerDao.insert(scammer4);

                ScammerRecord scammer5 = new ScammerRecord(
                        "+1-555-0102",
                        null,
                        "Romance Scam Ring",
                        34,
                        0.85,
                        new Date()
                );
                scammerDao.insert(scammer5);

                ScammerRecord scammer6 = new ScammerRecord(
                        "(480)640-7690",
                        "jordan@azmobileautorepair.com",
                        "Jordan Brunner - AZ Mobile Auto Repair",
                        26,
                        0.99,
                        new Date()
                );
                scammerDao.insert(scammer6);

                ScammerRecord scammer7 = new ScammerRecord(
                        "(480)410-2982",
                        null,
                        "Justin Malott - Rakkasan Motorsports (Closed)",
                        18,
                        0.95,
                        new Date()
                );
                scammerDao.insert(scammer7);
            }
        }).start();
    }

    /**
     * Initialize in-memory sample reports for display.
     */
    private void initializeSampleReports() {
        ScamReport report1 = new ScamReport(
                "Phishing Email",
                "Received email claiming to be from bank asking to verify account",
                "Phishing",
                4
        );
        report1.setEmail("fake.bank@suspicious.com");

        ScamReport report2 = new ScamReport(
                "Fake Lottery Win",
                "Text message claiming I won a prize I never entered",
                "Lottery Scam",
                5
        );

        reports.add(report1);
        reports.add(report2);
    }

    /**
     * Legacy sample data initialization (deprecated).
     */
    @Deprecated
    private void initializeSampleData() {
        // This is now handled by initializeSampleReports()
    }
}
