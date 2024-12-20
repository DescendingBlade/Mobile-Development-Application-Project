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

public class EndGame extends Fragment
{
    //Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Rename and change the types of the parameters
    private String mParam1;
    private String mParam2;

    /**
     * Creates a new instance of EndGame fragment.
     * @param param1 First parameter
     * @param param2 Second parameter
     * @return A new instance of fragment EndGame
     */
    public static EndGame newInstance(String param1, String param2)
    {
        EndGame fragment = new EndGame();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is being created.
     * Retrieves arguments if any.
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
     * Creates and returns the view hierarchy associated with the fragment.
     * Sets up end game UI, displays winner, and handles user interactions.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.endgame_fragment, container, false);
        MainActivityData mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);

        Button playAgainButton = view.findViewById(R.id.playAgainButton);
        Button mainMenuButton = view.findViewById(R.id.mainMenuButton);
        TextView winnerTextView = view.findViewById(R.id.winnerTextView);
        ImageView winnerAvatar = view.findViewById(R.id.winnerAvatar);

        // Set winner text
        if(mainActivityData.getWinner()==null)
        {
            winnerTextView.setText("DRAW!!!");
        }
        else
        {
            winnerTextView.setText(mainActivityData.winner + " wins!!!");
        }

        // Set winner avatar
        if(mainActivityData.getWinner() == null)
        {
            winnerAvatar.setVisibility(View.INVISIBLE);
        }
        else if(mainActivityData.winner.equals(mainActivityData.getPlayerOneName()))
        {
            winnerAvatar.setImageResource(mainActivityData.getPOneAvatar());
        }
        else if(mainActivityData.winner.equals(mainActivityData.getPlayerTwoName()))
        {
            winnerAvatar.setImageResource(mainActivityData.getPTwoAvatar());
        }
        else if(mainActivityData.winner.equals(mainActivityData.getComp()))
        {
            winnerAvatar.setImageResource(mainActivityData.getCompAvatar());
        }

        // Set click listeners for buttons
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityData.setClickedValue(4);
            }
        });
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityData.setClickedValue(0);
            }
        });

        return view;
    }
}
