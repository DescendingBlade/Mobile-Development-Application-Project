package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Random;

public class ProfileCreate extends Fragment
{
    //Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Rename and change the types of the parameters
    private String mParam1;
    private String mParam2;

    private MainActivityData mainActivityData;

    /**
     * Creates a new instance of the ProfileCreate fragment.
     *
     * @param param1 First parameter (unused in this implementation)
     * @param param2 Second parameter (unused in this implementation)
     * @return A new instance of fragment ProfileCreate
     */
    public static ProfileCreate newInstance(String param1, String param2)
    {
        ProfileCreate fragment = new ProfileCreate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called to do initial creation of the fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
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

    /**
     * Called to have the fragment instantiate its user interface view.
     * This method sets up the profile creation UI, including player names, avatars, colors,
     * and grid size selection.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return The View for the fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.profile_creation, container, false);
        mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);

        // Initialize UI elements
        Button gridSize = view.findViewById(R.id.GridSizeButton);
        Button confirmButton = view.findViewById(R.id.confirm_button);
        TextView gridSizeText = view.findViewById(R.id.gridSizeText);

        // Player One UI elements
        ImageView playerOneAvatar = view.findViewById(R.id.profilePlayerOne);
        ImageView playerOneColour = view.findViewById(R.id.colourPlayerOne);
        EditText playerOneName = view.findViewById(R.id.playerOneName);

        // Player Two UI elements
        ImageView playerTwoAvatar = view.findViewById(R.id.profilePlayerTwo);
        ImageView playerTwoColour = view.findViewById(R.id.colourPlayerTwo);
        EditText playerTwoName = view.findViewById(R.id.playerTwoName);
        ConstraintLayout playerTwoConstraint = view.findViewById(R.id.playerTwoConstraint);

        // Set initial values from MainActivityData
        playerOneName.setText(mainActivityData.getPlayerOneName());
        playerOneAvatar.setImageResource(mainActivityData.getPOneAvatar());
        playerOneColour.setImageResource(mainActivityData.getPOneColour());

        playerTwoName.setText(mainActivityData.getPlayerTwoName());
        playerTwoAvatar.setImageResource(mainActivityData.getPTwoAvatar());
        playerTwoColour.setImageResource(mainActivityData.getPTwoColour());

        // Set grid size text
        String newText = mainActivityData.getRow() + "x" + mainActivityData.getCol();
        gridSize.setText(newText);

        // Hide Player Two UI if not in PvP mode
        if(!mainActivityData.getGameMode())
        {
            playerTwoConstraint.setVisibility(View.INVISIBLE);
        }

        // Hide grid size options if in edit mode
        if(mainActivityData.getClickedValue() == 9)
        {
            gridSize.setVisibility(View.INVISIBLE);
            gridSizeText.setVisibility(View.INVISIBLE);
        }

        // Set up text change listener for Player One name
        playerOneName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //empty
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //empty
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty())
                {
                    mainActivityData.setPlayerOne(editable.toString());
                }
            }
        });

        // Set up text change listener for Player Two name
        playerTwoName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //empty
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //empty
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty())
                {
                    mainActivityData.setPlayerTwo(editable.toString());
                }
            }
        });

        // Set up click listeners for Player One avatar and color selection
        playerOneAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mainActivityData.setPOneSelect();
                mainActivityData.setClickedValue(2);
            }
        });
        playerOneColour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mainActivityData.setPOneSelect();
                mainActivityData.setClickedValue(3);
            }
        });

        // Set up click listeners for Player Two avatar and color selection
        playerTwoAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mainActivityData.setPTwoSelect();
                mainActivityData.setClickedValue(2);
            }
        });
        playerTwoColour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mainActivityData.setPTwoSelect();
                mainActivityData.setClickedValue(3);
            }
        });

        // Set up click listener for confirm button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityData.setClickedValue(4);
            }
        });

        // Set up click listener for grid size button
        gridSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mainActivityData.setClickedValue(6);
            }
        });

        return view;
    }
}
