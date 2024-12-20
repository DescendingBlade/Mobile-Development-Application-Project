package com.example.anslab7;

import android.content.Context;

import androidx.room.Room;

public class StudentDBInstance
{
    private static StudentDatabase database;

    public static StudentDatabase getDatabase(Context context)
    {
        if(database == null)
        {
            database = Room.databaseBuilder(context,
                            StudentDatabase.class, "app_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}
