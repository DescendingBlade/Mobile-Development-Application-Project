package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class NoteTaking extends Fragment
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText noteTitle;
    private EditText noteDetail;

    public NoteTaking()
    {

    }

    public static NoteTaking newInstance(String param1, String param2)
    {
        NoteTaking fragment = new NoteTaking();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceSate)
    {
        super.onCreate(savedInstanceSate);
        if(getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_note_taking, container, false);
        Button save = view.findViewById(R.id.saveButton);
        noteTitle = view.findViewById(R.id.editTextTitle);
        noteDetail = view.findViewById(R.id.editTextDetails);

        ModelView model = new ViewModelProvider(getActivity()).get(ModelView.class);

        if(model.getIsEdited())
        {
            save.setText("Update");
            noteTitle.setText(model.getNoteTitle());
            noteDetail.setText(model.getNotes());
        }

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String editNoteTitle = noteTitle.getText().toString().trim();
                String editNoteText = noteDetail.getText().toString().trim();

                if(!editNoteText.isEmpty() && !editNoteTitle.isEmpty())
                {
                    model.setNoteTitle(editNoteTitle);
                    model.setNotes(editNoteText);
                    model.setClickedValue(2);
                    model.setIsEdited();
                }
            }
        });
        return view;
    }
}
