package com.example.anslab3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NoteUpdate extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteupdate_detail);

        EditText noteToUpdate = findViewById(R.id.editText);
        Button updateButton = findViewById(R.id.updateButton);

        Intent intent = getIntent();
        String note = intent.getStringExtra("Note");
        noteToUpdate.setText(note);

        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                String updatedNote = noteToUpdate.getText().toString().trim();
                Intent intent1 = new Intent();
                intent1.putExtra("Note", updatedNote);
                setResult(RESULT_OK, intent1);
                finish();
            }
        });
    }
}
