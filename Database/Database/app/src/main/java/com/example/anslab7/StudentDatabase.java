package com.example.anslab7;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1)
public abstract class StudentDatabase extends RoomDatabase
{
    public abstract StudentDAO studentDAO();
}