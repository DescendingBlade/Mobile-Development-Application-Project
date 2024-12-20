package com.example.foodcaloriemanagementapplication;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * This class represents the Room database for the application.
 * It uses the Singleton pattern to ensure only one instance of the database is created.
 */
@Database(entities = {UserData.class, FoodEntry.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    // Volatile ensures that INSTANCE is always up-to-date and the same for all threads
    private static volatile AppDatabase INSTANCE;

    /**
     * Abstract method to get the UserDataDao.
     * Room will generate an implementation of this method.
     */
    public abstract UserDataDao userDataDao();

    /**
     * Abstract method to get the FoodEntryDao.
     * Room will generate an implementation of this method.
     */
    public abstract FoodEntryDao foodEntryDao();

    /**
     * Gets the singleton instance of the AppDatabase.
     * If the instance doesn't exist, it creates one.
     * This method is thread-safe due to the use of synchronized and volatile.
     *
     * @param context The application context
     * @return The singleton instance of AppDatabase
     */
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null)
        {
            synchronized (AppDatabase.class)
            {
                if (INSTANCE == null)
                {
                    // Create the database instance
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()  // Allows Room to recreate database if no Migration object specified
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}