package com.scambuster.app.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.scambuster.app.database.dao.ScammerDao;
import com.scambuster.app.database.entity.ScammerRecord;

/**
 * Room database abstraction for ScamBuster.
 * Manages local SQLite database with scammer records.
 * Singleton pattern ensures single database instance across app.
 */
@Database(entities = {ScammerRecord.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract ScammerDao scammerDao();

    /**
     * Get singleton database instance.
     * Creates database on first call, subsequent calls return cached instance.
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "scambuster_database")
                    .build();
        }
        return instance;
    }

    /**
     * Close database connection (for testing/cleanup).
     */
    public static void closeDatabase() {
        if (instance != null && instance.isOpen()) {
            instance.close();
            instance = null;
        }
    }
}
