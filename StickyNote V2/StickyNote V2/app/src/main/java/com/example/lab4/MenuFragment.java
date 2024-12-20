package com.example.lab4;

import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Rename and change the types of the parameters
    private String mParam1;
    private String mParam2;

    public MenuFragment()
    {
    }

    public static MenuFragment newInstance(String param1, String param2)
    {
        MenuFragment fragment1 = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment1.setArguments(args);
        return fragment1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 =getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ModelView model = new ViewModelProvider(getActivity()).get(ModelView.class);

        Button addNote = view.findViewById(R.id.addButton);

        if (model.getIsEdited()) {
            addNote.setText("Update Note");
        }

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setClickedValue(1);
            }
        });
        return view;
    }
}

