package com.example.anslab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText firstNumber = findViewById(R.id.firstNumber);
        EditText secondNumber = findViewById(R.id.secondNumber);

        Button addButton = findViewById(R.id.addButton);
        Button substractButton = findViewById(R.id.subButton);
        Button multiplyButton = findViewById(R.id.mulButton);
        Button divideButton = findViewById(R.id.divButton);

        TextView result = findViewById(R.id.resultView);

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                double i = Double.parseDouble(firstNumber.getText().toString());
                double j = Double.parseDouble(secondNumber.getText().toString());
                double sum = i + j;
                result.setText(String.valueOf(sum));
            }
        });

        substractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double i = Double.parseDouble(firstNumber.getText().toString());
                double j = Double.parseDouble(secondNumber.getText().toString());
                double subtract = i - j;
                result.setText(String.valueOf(subtract));
            }
        });

        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double i = Double.parseDouble(firstNumber.getText().toString());
                double j = Double.parseDouble(secondNumber.getText().toString());
                double multiply = i * j;
                result.setText(String.valueOf(multiply));
            }
        });

        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                double i = Double.parseDouble(firstNumber.getText().toString());
                double j = Double.parseDouble(secondNumber.getText().toString());
                double division = i / j;
                if (j == 0)
                {
                    result.setText("Cannot divide by 0");
                }
                else
                {
                    result.setText(String.valueOf(division));
                }

            }
        });
    }
}