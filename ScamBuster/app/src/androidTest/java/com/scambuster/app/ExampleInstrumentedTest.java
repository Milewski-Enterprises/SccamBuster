package com.scambuster.app;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.scambuster.app.repository.ScamReportRepository;
import com.scambuster.app.model.ScamReport;
import com.scambuster.app.database.AppDatabase;
import com.scambuster.app.database.entity.ScammerRecord;
import java.util.Date;
import java.util.List;

/**
 * Instrumented test for ScamBuster application.
 * Tests database operations and repository behavior.
 * This will execute on an Android device.
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private Context context;
    private AppDatabase database;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        database = AppDatabase.getInstance(context);
    }

    @After
    public void tearDown() {
        // Clean up database after each test
        database.scammerDao().deleteAll();
        AppDatabase.closeDatabase();
    }

    @Test
    public void useAppContext() {
        assertEquals("com.scambuster.app", context.getPackageName());
    }

    @Test
    public void testRepositorySingleton() {
        // Create new instances in separate threads to test singleton behavior
        ScamReportRepository repo1 = ScamReportRepository.getInstance(context);
        ScamReportRepository repo2 = ScamReportRepository.getInstance(context);
        assertSame(repo1, repo2);
    }

    @Test
    public void testAddReport() {
        ScamReportRepository repo = ScamReportRepository.getInstance(context);
        int initialCount = repo.getTotalReportCount();
        
        ScamReport report = new ScamReport("Test Scam", "Test Description", "Test", 3);
        repo.addReport(report);
        
        assertEquals(initialCount + 1, repo.getTotalReportCount());
    }

    @Test
    public void testGetHighRiskReports() {
        ScamReportRepository repo = ScamReportRepository.getInstance(context);
        List<ScamReport> highRiskReports = repo.getHighRiskReports();
        assertTrue(highRiskReports.size() > 0);
        
        // Verify all reports are high-risk (level >= 4)
        for (ScamReport report : highRiskReports) {
            assertTrue(report.getRiskLevel() >= 4);
        }
    }

    @Test
    public void testScammerDatabaseOperations() throws InterruptedException {
        ScamReportRepository repo = ScamReportRepository.getInstance(context);
        
        // Create test scammer
        ScammerRecord testScammer = new ScammerRecord(
                "+1-555-9999",
                "test.scammer@fake.com",
                "Test Scammer",
                5,
                0.90,
                new Date()
        );
        
        // Add scammer to database
        repo.addKnownScammer(testScammer);
        
        // Wait for async operation
        Thread.sleep(500);
        
        // Verify scammer was added
        ScammerRecord foundByPhone = repo.getScammerByPhone("+1-555-9999");
        assertNotNull(foundByPhone);
        assertEquals("Test Scammer", foundByPhone.getName());
        
        ScammerRecord foundByEmail = repo.getScammerByEmail("test.scammer@fake.com");
        assertNotNull(foundByEmail);
        assertEquals(5, foundByEmail.getReportCount());
    }

    @Test
    public void testScammerDatabaseInitialization() throws InterruptedException {
        ScamReportRepository repo = ScamReportRepository.getInstance(context);
        
        // Wait for database initialization
        Thread.sleep(1000);
        
        // Verify sample scammers were added
        List<ScammerRecord> allScammers = repo.getAllKnownScammers();
        assertTrue(allScammers.size() >= 5); // At least 5 sample scammers
        
        // Verify high-risk scammers
        List<ScammerRecord> highRiskScammers = repo.getHighRiskScammers();
        assertTrue(highRiskScammers.size() > 0);
        
        // Verify all have high risk scores
        for (ScammerRecord scammer : highRiskScammers) {
            assertTrue(scammer.getRiskScore() >= 0.7);
        }
    }

    @Test
    public void testScammerReportCountQuery() throws InterruptedException {
        ScamReportRepository repo = ScamReportRepository.getInstance(context);
        
        // Wait for initialization
        Thread.sleep(1000);
        
        // Get scammers with at least 50 reports
        List<ScammerRecord> frequentScammers = repo.getScammersByReportCount(50);
        assertTrue(frequentScammers.size() > 0);
        
        // Verify all meet criteria
        for (ScammerRecord scammer : frequentScammers) {
            assertTrue(scammer.getReportCount() >= 50);
        }
    }
}
