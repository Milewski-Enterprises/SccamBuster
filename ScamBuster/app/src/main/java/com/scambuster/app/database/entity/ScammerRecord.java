package com.scambuster.app.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

/**
 * Room entity representing a known scammer record.
 * Stored in local SQLite database for offline verification.
 */
@Entity(tableName = "scammer_records")
public class ScammerRecord {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String phone;        // Unique phone number (nullable)
    private String email;        // Unique email address (nullable)
    private String name;         // Scammer name/alias
    private int reportCount;     // Number of reports linking to this scammer
    private double riskScore;    // Risk score (0.0 - 1.0)
    private Date lastSeen;       // Last report date for this scammer

    // Constructors
    public ScammerRecord() {
        this.reportCount = 1;
        this.riskScore = 0.8;
        this.lastSeen = new Date();
    }

    public ScammerRecord(String phone, String email, String name, 
                        int reportCount, double riskScore, Date lastSeen) {
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.reportCount = reportCount;
        this.riskScore = riskScore;
        this.lastSeen = lastSeen;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = Math.min(1.0, Math.max(0.0, riskScore)); // Clamp 0.0-1.0
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    /**
     * Increment report count and update last seen timestamp.
     */
    public void incrementReportCount() {
        this.reportCount++;
        this.lastSeen = new Date();
    }
}
