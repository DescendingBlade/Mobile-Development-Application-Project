package com.example.anslab3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button addButton;

    private ArrayList<String> notes = new ArrayList<>();
    private int index = 0;
    private int checking =0;

    ActivityResultLauncher<Intent> detailActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent intent = result.getData();
                try {
                    if (intent != null) {
                        String note = intent.getStringExtra("Note");
                        if (note != null) {
                            if (index < 4) {
                                notes.add(note);
                                updateNoteButtons();
                                index++;
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("MainActivity", "Error handling result", e);
                }
            }
    );

    ActivityResultLauncher<Intent> updateActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String note = data.getStringExtra("Note");
                        if (note != null && index >= 0 && index < notes.size()) {
                            notes.set(index, note);
                            updateNoteButtons();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            notes = savedInstanceState.getStringArrayList("notes");
            index = savedInstanceState.getInt("index", -1); // Use -1 to indicate no note is selected
        }

        button1 = findViewById(R.id.note1);
        button2 = findViewById(R.id.note2);
        button3 = findViewById(R.id.note3);
        button4 = findViewById(R.id.note4);
        addButton = findViewById(R.id.addButton);

        updateNoteButtons();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checking > 3) {
                    Toast.makeText(MainActivity.this, "Can't add anymore", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, NoteTaking.class);
                    detailActivityLauncher.launch(intent);
                    checking++;
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notes.size() > 0) {
                    index = 1;
                    Intent intent = new Intent(MainActivity.this, NoteUpdate.class);
                    intent.putExtra("Note", notes.get(0));
                    updateActivityLauncher.launch(intent);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notes.size() > 1) {
                    index = 2;
                    Intent intent = new Intent(MainActivity.this, NoteUpdate.class);
                    intent.putExtra("Note", notes.get(1));
                    updateActivityLauncher.launch(intent);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notes.size() > 2) {
                    index = 3;
                    Intent intent = new Intent(MainActivity.this, NoteUpdate.class);
                    intent.putExtra("Note", notes.get(2));
                    updateActivityLauncher.launch(intent);
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notes.size() > 3) {
                    index = 4;
                    Intent intent = new Intent(MainActivity.this, NoteUpdate.class);
                    intent.putExtra("Note", notes.get(3));
                    updateActivityLauncher.launch(intent);
                }
            }
        });
    }

    private void updateNoteButtons() {
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);

        if (notes.size() > 0) {
            button1.setVisibility(View.VISIBLE);
        }
        if (notes.size() > 1) {
            button2.setVisibility(View.VISIBLE);
        }
        if (notes.size() > 2) {
            button3.setVisibility(View.VISIBLE);
        }
        if (notes.size() > 3) {
            button4.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("notes", notes);
        outState.putInt("index", index);
    }
}
