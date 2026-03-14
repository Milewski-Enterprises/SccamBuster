package com.scambuster.app.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.scambuster.app.database.entity.ScammerRecord;
import java.util.List;

/**
 * Data Access Object for ScammerRecord entities.
 * Provides database operations for known scammer records.
 */
@Dao
public interface ScammerDao {

    /**
     * Insert a new scammer record.
     */
    @Insert
    long insert(ScammerRecord scammer);

    /**
     * Update an existing scammer record.
     */
    @Update
    int update(ScammerRecord scammer);

    /**
     * Delete a scammer record.
     */
    @Delete
    int delete(ScammerRecord scammer);

    /**
     * Find scammer by phone number.
     * @param phone Phone number to search for
     * @return ScammerRecord if found, null otherwise
     */
    @Query("SELECT * FROM scammer_records WHERE phone = :phone LIMIT 1")
    ScammerRecord findByPhone(String phone);

    /**
     * Find scammer by email address.
     * @param email Email address to search for
     * @return ScammerRecord if found, null otherwise
     */
    @Query("SELECT * FROM scammer_records WHERE email = :email LIMIT 1")
    ScammerRecord findByEmail(String email);

    /**
     * Get all scammer records.
     * @return List of all ScammerRecords
     */
    @Query("SELECT * FROM scammer_records ORDER BY reportCount DESC")
    List<ScammerRecord> getAll();

    /**
     * Get high-risk scammers (risk score >= 0.7).
     * @return List of high-risk ScammerRecords
     */
    @Query("SELECT * FROM scammer_records WHERE riskScore >= 0.7 ORDER BY riskScore DESC")
    List<ScammerRecord> getHighRiskScammers();

    /**
     * Get scammers by report count threshold.
     * @param minReports Minimum number of reports
     * @return List of ScammerRecords with at least minReports reports
     */
    @Query("SELECT * FROM scammer_records WHERE reportCount >= :minReports ORDER BY reportCount DESC")
    List<ScammerRecord> getByReportCount(int minReports);

    /**
     * Count total scammer records.
     * @return Total count
     */
    @Query("SELECT COUNT(*) FROM scammer_records")
    int getTotalCount();

    /**
     * Delete all records (for testing/reset).
     */
    @Query("DELETE FROM scammer_records")
    void deleteAll();
}
