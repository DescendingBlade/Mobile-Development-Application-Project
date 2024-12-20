package com.example.lab4;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    MenuFragment menu = new MenuFragment();
    NoteTaking note = new NoteTaking();

    String noteTitle;
    String noteDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null)
        {
            noteTitle = savedInstanceState.getString("noteTitle");
            noteDetail = savedInstanceState.getString("noteDetail");
        }

        int screenOrientation = getResources().getConfiguration().orientation;
        if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            setContentView(R.layout.fragment_linear);
            loadMenuLandscape();
            ModelView model = new ViewModelProvider(this).get(ModelView.class);
            model.clickedValue.observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer)
                {
                    if(model.getClickedValue() == 1)
                    {
                        loadNoteTakingLandscape();
                    }
                    if(model.getClickedValue() == 2)
                    {
                        Button addNote = findViewById(R.id.addButton);
                        addNote.setText("Update Note");
                        Button saveButton = findViewById(R.id.saveButton);
                        saveButton.setText("Update");
                        loadMenuLandscape();
                        removeFragment();
                }
            }
        });
    }
        else if(screenOrientation == Configuration.ORIENTATION_PORTRAIT)
        {
            setContentView(R.layout.activity_main);
            loadMenuPortrait();
            ModelView model = new ViewModelProvider(this).get(ModelView.class);
            model.clickedValue.observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    noteTitle = model.getNoteTitle();
                    noteDetail = model.getNotes();
                    if(model.getClickedValue() == 1)
                    {
                        loadNoteTakingPortrait();
                    }
                    if(model.getClickedValue() == 2)
                    {
                        loadMenuPortrait();
                    }
                }
            });
        }
    }

    private void loadMenuPortrait()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.frame);

        if(frag==null)
        {
            fm.beginTransaction().add(R.id.frame, menu).commit();
        }
        else
        {
            fm.beginTransaction().replace(R.id.frame, menu).commit();
        }
    }

    private void loadNoteTakingPortrait()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.frame);

        if(frag==null)
        {
            fm.beginTransaction().add(R.id.frame, note).commit();
        }
        else
        {
            fm.beginTransaction().replace(R.id.frame, note).commit();
        }
    }

    private void loadMenuLandscape()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.frame);

        if(frag==null)
        {
            fm.beginTransaction().add(R.id.frame_One, menu).commit();
        }
        else
        {
            fm.beginTransaction().replace(R.id.frame_One, menu).commit();
        }
    }

    private void loadNoteTakingLandscape()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.frame_Two);
        FrameLayout layout = (FrameLayout) findViewById(R.id.frame_Two);
        layout.setVisibility(View.VISIBLE);
        if(frag==null)
        {
            fm.beginTransaction().add(R.id.frame_Two, note).commit();
        }
        else
        {
            fm.beginTransaction().replace(R.id.frame_Two, note).commit();
        }
    }

    private void removeFragment()
    {
        FrameLayout layout = (FrameLayout) findViewById(R.id.frame_Two);
        layout.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putString("noteTitle", noteTitle);
        outState.putString("noteDetail", noteDetail);

        super.onSaveInstanceState(outState);
    }
}