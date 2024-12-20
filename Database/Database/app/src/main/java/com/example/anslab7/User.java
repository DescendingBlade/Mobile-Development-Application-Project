package com.example.anslab7;

import android.graphics.Bitmap;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class User
{
    @PrimaryKey
    public int id;

    public String firstName;
    public String lastName;

    @Ignore
    Bitmap picture;
}
