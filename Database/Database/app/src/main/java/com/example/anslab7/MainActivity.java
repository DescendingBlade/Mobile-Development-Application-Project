package com.example.anslab7;

import static android.widget.Toast.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StudentDAO studentDAO = StudentDBInstance.
                getDatabase(getApplicationContext()).studentDAO();
        EditText name = findViewById(R.id.studentName);
        Button add = findViewById(R.id.addButton);
        Button next = findViewById(R.id.nextActivity);
        Button delete = findViewById(R.id.deleteButton);
        Button query = findViewById(R.id.queryButton);
        Button update = findViewById(R.id.updateButton);

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String studentName = name.getText().toString();
                if(studentName.trim().isEmpty())
                {
                    Toast toast = makeText(MainActivity.this,
                            "Cannot be blank", LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Student student = new Student();
                    student.setName(studentName);
                    studentDAO.insert(student);
                    Toast toast = makeText(MainActivity.this,
                            "A student is added", LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,
                        NextActivity.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String studentName = name.getText().toString();
                if (studentName.trim().isEmpty()) {
                    Toast toast = makeText(MainActivity.this,
                            "Cannot be blank", LENGTH_SHORT);
                    toast.show();
                } else {
                    studentDAO.delete(studentName);
                    Toast toast = makeText(MainActivity.this,
                            "Student is deleted", LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        query.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String studentName = name.getText().toString();
                if (studentName.trim().isEmpty()) {
                    Toast toast = makeText(MainActivity.this,
                            "Cannot be blank", LENGTH_SHORT);
                    toast.show();
                }
                else if(!studentName.trim().isEmpty())
                {
                    List<Student> temp = studentDAO.getStudentsByName(studentName);
                    Toast toast = makeText(MainActivity.this,
                            "Student found", LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Toast toast = makeText(MainActivity.this,
                            "No student found", LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
