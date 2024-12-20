package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.nio.charset.StandardCharsets;

public class Statistics extends Fragment
{
    //Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Rename and change the types of the parameters
    private String mParam1;
    private String mParam2;

    /**
     * Creates a new instance of the Statistics fragment.
     *
     * @param param1 First parameter (unused in this implementation)
     * @param param2 Second parameter (unused in this implementation)
     * @return A new instance of fragment Statistics
     */
    public static Statistics newInstance(String param1, String param2)
    {
        Statistics fragment = new Statistics();
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
     * This method sets up the statistics UI, including displaying player information and game statistics.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return The View for the fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.statistics_fragment, container, false);
        MainActivityData mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);

        // Initialize UI elements
        Button backButton = view.findViewById(R.id.backButton);
        TextView winText = view.findViewById(R.id.winText);
        TextView loseText = view.findViewById(R.id.loseText);
        TextView drawText = view.findViewById(R.id.drawText);
        TextView gamesText = view.findViewById(R.id.gamesText);
        TextView winPercText = view.findViewById(R.id.winPercText);
        TextView playerOneName = view.findViewById(R.id.playerOneName);
        ImageView avatarOne = view.findViewById(R.id.avatarOne);

        // Prepare statistics strings
        String win = "Wins: " + mainActivityData.getWins();
        String lose = "Lose: " + mainActivityData.getLose();
        String draw = "Draw: " + mainActivityData.getDraw();
        String totalGP = "Total Games Played: " + mainActivityData.getTGP();

        // Calculate win percentage
        double winP=0;
        if(mainActivityData.getTGP() != 0)
        {
            winP = ((double) mainActivityData.getWins() / (double)mainActivityData.getTGP())*100;
        }
        String winPerct = "Win Percentage: " +winP +"%";

        // Set player information and statistics in UI
        playerOneName.setText(mainActivityData.getPlayerOneName());
        avatarOne.setImageResource(mainActivityData.getPOneAvatar());
        winText.setText(win);
        loseText.setText(lose);
        drawText.setText(draw);
        gamesText.setText(totalGP);
        winPercText.setText(winPerct);

        // Set up click listener for back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainActivityData.getPreviousClickedValue() == 0)
                {
                    mainActivityData.setClickedValue(0); // Navigate to main menu
                }
                else if (mainActivityData.getPreviousClickedValue() == 5)
                {
                    mainActivityData.setClickedValue(5); // Navigate to settings
                }
            }
        });
        return view;
    }
}
