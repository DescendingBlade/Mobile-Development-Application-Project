package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class Settings extends Fragment
{
    //Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Rename and change the types of the parameters
    private String mParam1;
    private String mParam2;

    /**
     * Creates a new instance of the Settings fragment.
     *
     * @param param1 First parameter (unused in this implementation)
     * @param param2 Second parameter (unused in this implementation)
     * @return A new instance of fragment Settings
     */
    public static Settings newInstance(String param1, String param2)
    {
        Settings fragment = new Settings();
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
     * This method sets up the settings UI, including buttons for various actions.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return The View for the fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        MainActivityData mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);

        // Initialize UI elements
        Button playAgain = view.findViewById(R.id.playAgain);
        Button continueButton = view.findViewById(R.id.continueButton);
        Button changeProfile = view.findViewById(R.id.changeProfile);
        Button statisticsButton = view.findViewById(R.id.statisticsButton);
        Button returnMenu = view.findViewById(R.id.returnMenu);

        // Set up click listener for Play Again button
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mainActivityData.setBoard(null); // Reset the game board
                mainActivityData.setClickedValue(4); // Navigate to game screen
            }
        });

        // Set up click listener for Continue button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mainActivityData.setClickedValue(4); // Navigate to game screen
            }
        });

        // Set up click listener for Change Profile button
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mainActivityData.setClickedValue(9); // Navigate to profile change screen
            }
        });

        // Set up click listener for Statistics button
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityData.setClickedValue(7); // Navigate to statistics screen
            }
        });

        // Set up click listener for Return to Menu button
        returnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mainActivityData.setGameOver(true); // Set game as over
                mainActivityData.setClickedValue(0); // Navigate to main menu
            }
        });

        return view;
    }
}
