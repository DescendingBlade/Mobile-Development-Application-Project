package com.example.anslab7;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student
{
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "student_name")
    private String name;

    @ColumnInfo(name = "student_age")
    private int age;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAge(int age)
    {
        this.age= age;
    }
}
