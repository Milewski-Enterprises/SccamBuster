package com.scambuster.app;

import org.junit.Test;

import static org.junit.Assert.*;

import com.scambuster.app.util.ScamDetectionUtil;
import com.scambuster.app.model.ScamReport;
import com.scambuster.app.database.entity.ScammerRecord;
import java.util.Date;

/**
 * Unit tests for ScamBuster application.
 * Tests validation logic, pattern matching, and model behavior.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testSuspiciousKeywordDetection() {
        assertTrue(ScamDetectionUtil.containsSuspiciousKeywords("Please verify account immediately"));
        assertTrue(ScamDetectionUtil.containsSuspiciousKeywords("Claim your reward now"));
        assertFalse(ScamDetectionUtil.containsSuspiciousKeywords("This is a normal message"));
    }

    @Test
    public void testEmailValidation() {
        assertTrue(ScamDetectionUtil.isValidEmail("user@example.com"));
        assertTrue(ScamDetectionUtil.isValidEmail("test.email@domain.co.uk"));
        assertFalse(ScamDetectionUtil.isValidEmail("invalid.email"));
        assertFalse(ScamDetectionUtil.isValidEmail("@example.com"));
    }

    @Test
    public void testPhoneValidation() {
        assertTrue(ScamDetectionUtil.isValidPhoneNumber("123-456-7890"));
        assertTrue(ScamDetectionUtil.isValidPhoneNumber("(123) 456-7890"));
        assertTrue(ScamDetectionUtil.isValidPhoneNumber("+1 123 456 7890"));
        assertFalse(ScamDetectionUtil.isValidPhoneNumber("abc-def-ghij"));
    }

    @Test
    public void testScamReportCreation() {
        ScamReport report = new ScamReport("Phishing Email", "Suspicious email", "Phishing", 4);
        assertEquals("Phishing Email", report.getTitle());
        assertEquals("Phishing", report.getCategory());
        assertEquals(4, report.getRiskLevel());
        assertNotNull(report.getReportDate());
    }

    @Test
    public void testRiskLevelValidation() {
        ScamReport report = new ScamReport("Test", "Test", "Test", 3);
        report.setRiskLevel(5);
        assertEquals(5, report.getRiskLevel());
        
        report.setRiskLevel(10); // Should cap at 5
        assertEquals(5, report.getRiskLevel());
        
        report.setRiskLevel(1);
        assertEquals(1, report.getRiskLevel());
    }

    @Test
    public void testScammerRecordCreation() {
        ScammerRecord scammer = new ScammerRecord(
                "+1-555-0100",
                "scammer@fake.com",
                "Test Scammer",
                42,
                0.95,
                new Date()
        );
        assertEquals("+1-555-0100", scammer.getPhone());
        assertEquals("scammer@fake.com", scammer.getEmail());
        assertEquals(42, scammer.getReportCount());
        assertEquals(0.95, scammer.getRiskScore(), 0.01);
    }

    @Test
    public void testScammerRiskScoreClamping() {
        ScammerRecord scammer = new ScammerRecord();
        scammer.setRiskScore(1.5); // Should clamp to 1.0
        assertEquals(1.0, scammer.getRiskScore(), 0.01);
        
        scammer.setRiskScore(-0.5); // Should clamp to 0.0
        assertEquals(0.0, scammer.getRiskScore(), 0.01);
    }

    @Test
    public void testRiskLevelWithKnownScammer() {
        ScammerRecord knownScammer = new ScammerRecord(
                "+1-555-0100",
                null,
                "Lottery Scammer",
                47,
                0.95,
                new Date()
        );
        
        // Risk level should be 5 when known scammer is matched
        int riskLevel = ScamDetectionUtil.calculateRiskLevel(
                "Random Title",
                "Random Description",
                "+1-555-0100",
                "random@example.com",
                knownScammer
        );
        assertEquals(5, riskLevel);
    }

    @Test
    public void testRiskLevelWithoutKnownScammer() {
        int riskLevel = ScamDetectionUtil.calculateRiskLevel(
                "Verify account immediately",
                "Click here for details",
                "not-a-phone",
                "invalid.email",
                null
        );
        assertTrue(riskLevel > 0);
        assertEquals(5, riskLevel); // Should be at max due to keywords + invalid inputs
    }
}
