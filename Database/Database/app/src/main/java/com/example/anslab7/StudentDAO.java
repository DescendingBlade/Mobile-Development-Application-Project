package com.example.anslab7;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDAO {
    @Insert
    void insert(Student... student);

    @Update
    void update(Student... student);

    @Query("DELETE FROM students WHERE student_name = :student")
    void delete(String student);

    @Query("SELECT * FROM students")
    List<Student> getAllStudents();

    @Query("SELECT * FROM students WHERE student_name = :studentName")
    List<Student> getStudentsByName(String studentName);

    @Query("SELECT * FROM students WHERE id = :studentId")
    Student getStudentByID(int studentId);
}



