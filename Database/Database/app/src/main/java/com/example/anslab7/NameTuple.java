package com.example.lab77;

import androidx.room.ColumnInfo;

public class NameTuple
{
    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;
}
