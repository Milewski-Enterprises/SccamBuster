package com.scambuster.app.model;

import java.util.Date;

/**
 * Model class representing a scam report.
 */
public class ScamReport {
    private String id;
    private String title;
    private String description;
    private String category;
    private String phoneNumber;
    private String email;
    private Date reportDate;
    private int riskLevel;// 1-5, where 5 is highest risk

    public ScamReport() {
    }

    public ScamReport(String title, String description, String category, int riskLevel) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.riskLevel = riskLevel;
        this.reportDate = new Date();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public int getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(int riskLevel) {
        if (riskLevel >= 1 && riskLevel <= 5) {
            this.riskLevel = riskLevel;
        }
    }

    @Override
    public String toString() {
        return "ScamReport{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", riskLevel=" + riskLevel +
                ", reportDate=" + reportDate +
                '}';
    }
}
