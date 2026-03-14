package com.scambuster.app.util;

import com.scambuster.app.database.entity.ScammerRecord;
import com.scambuster.app.repository.ScamReportRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Utility class for scam detection and validation.
 * Includes pattern matching and database-backed scammer verification.
 */
public class ScamDetectionUtil {

    private static final Set<String> SUSPICIOUS_KEYWORDS = new HashSet<>();
    // More robust email regex (OWASP Validation Regex)
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    // Stricter phone regex (allows +, spaces, dashes, parentheses, 7-25 digits)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[0-9. ()-]{7,25}$"
    );

    static {
        SUSPICIOUS_KEYWORDS.add("verify account");
        SUSPICIOUS_KEYWORDS.add("confirm identity");
        SUSPICIOUS_KEYWORDS.add("urgent action");
        SUSPICIOUS_KEYWORDS.add("claim reward");
        SUSPICIOUS_KEYWORDS.add("click here immediately");
        SUSPICIOUS_KEYWORDS.add("suspicious activity");
        SUSPICIOUS_KEYWORDS.add("update payment");
        SUSPICIOUS_KEYWORDS.add("confirm credentials");
    }

    /**
     * Checks if a text contains suspicious keywords commonly used in scams.
     */
    public static boolean containsSuspiciousKeywords(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        String lowerText = text.toLowerCase();
        // Stream is fine, but for performance in tight loops, a simple loop is better in Android
        for (String keyword : SUSPICIOUS_KEYWORDS) {
            if (lowerText.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates email format.
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates phone number format.
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (phone == null) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Checks if phone or email matches a known scammer in the database.
     * Returns ScammerRecord if match found, null otherwise.
     * NOTE: This is a blocking call - execute in background thread.
     */
    public static ScammerRecord isKnownScammer(String phoneNumber, String email) {
        ScamReportRepository repo = ScamReportRepository.getInstance();
        
        if (repo == null) {
            return null;
        }

        // Check phone number first
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            ScammerRecord phoneMatch = repo.getScammerByPhone(phoneNumber);
            if (phoneMatch != null) {
                return phoneMatch;
            }
        }

        // Check email second
        if (email != null && !email.isEmpty()) {
            ScammerRecord emailMatch = repo.getScammerByEmail(email);
            if (emailMatch != null) {
                return emailMatch;
            }
        }

        return null;
    }

    /**
     * Calculates risk level based on various factors (1-5).
     * Enhanced to include known scammer detection.
     */
    public static int calculateRiskLevel(String title, String description, 
                                         String phoneNumber, String email,
                                         ScammerRecord knownScammer) {
        int risk = 0;

        // Known scammer match = maximum risk
        if (knownScammer != null) {
            return 5;
        }

        // Check suspicious keywords
        if (containsSuspiciousKeywords(title) || containsSuspiciousKeywords(description)) {
            risk += 2;
        }

        // Check phone number validity
        if (phoneNumber != null && !isValidPhoneNumber(phoneNumber)) {
            risk += 1;
        }

        // Check email validity
        if (email != null && !isValidEmail(email)) {
            risk += 1;
        }

        // Cap risk at 5
        return Math.min(risk, 5);
    }

    /**
     * Legacy calculateRiskLevel without scammer checking.
     * Maintained for backward compatibility.
     */
    @Deprecated
    public static int calculateRiskLevel(String title, String description, 
                                         String phoneNumber, String email) {
        return calculateRiskLevel(title, description, phoneNumber, email, null);
    }
}
