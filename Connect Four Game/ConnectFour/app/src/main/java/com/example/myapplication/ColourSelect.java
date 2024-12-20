package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class ColourSelect extends Fragment
{
    //Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Rename and change the types of the parameters
    private String mParam1;
    private String mParam2;

    /**
     * Creates a new instance of ColourSelect fragment.
     * @param param1 First parameter
     * @param param2 Second parameter
     * @return A new instance of fragment ColourSelect
     */
    public static ColourSelect newInstance(String param1, String param2)
    {
        ColourSelect fragment = new ColourSelect();
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
     * Sets up color selection UI and handles user interactions.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.colour_selection, container, false);
        MainActivityData mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        Button confirmColour = view.findViewById(R.id.confirmSelection);
        ImageView redColour = view.findViewById(R.id.redColour);
        ImageView blueColour = view.findViewById(R.id.blueColour);
        ImageView greenColour = view.findViewById(R.id.greenColour);
        ImageView purpleColour = view.findViewById(R.id.purpleColour);
        ImageView pinkColour = view.findViewById(R.id.pinkColour);
        ImageView yellowColour = view.findViewById(R.id.yellowColour);

        // Set click listeners for each color
        redColour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainActivityData.getSelect())
                {
                    mainActivityData.pOneColour.setValue(R.drawable.token_red);
                }
                else
                {
                    mainActivityData.pTwoColour.setValue(R.drawable.token_red);
                }
            }
        });
        blueColour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainActivityData.getSelect())
                {
                    mainActivityData.pOneColour.setValue(R.drawable.token_blue);
                }
                else
                {
                    mainActivityData.pTwoColour.setValue(R.drawable.token_blue);
                }

            }
        });
        greenColour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainActivityData.getSelect())
                {
                    mainActivityData.pOneColour.setValue(R.drawable.token_green);
                }
                else
                {
                    mainActivityData.pTwoColour.setValue(R.drawable.token_green);
                }
            }
        });
        purpleColour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainActivityData.getSelect())
                {
                    mainActivityData.pOneColour.setValue(R.drawable.token_purple);
                }
                else
                {
                    mainActivityData.pTwoColour.setValue(R.drawable.token_purple);
                }
            }
        });
        pinkColour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainActivityData.getSelect())
                {
                    mainActivityData.pOneColour.setValue(R.drawable.token_pink);
                }
                else
                {
                    mainActivityData.pTwoColour.setValue(R.drawable.token_pink);
                }
            }
        });
        yellowColour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainActivityData.getSelect())
                {
                    mainActivityData.pOneColour.setValue(R.drawable.token_yellow);
                }
                else
                {
                    mainActivityData.pTwoColour.setValue(R.drawable.token_yellow);
                }
            }
        });

        // Confirm color selection
        confirmColour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(mainActivityData.getPreviousClickedValue() == 9)
                {
                    mainActivityData.setClickedValue(9);
                }
                else
                {
                    mainActivityData.setClickedValue(1);
                }
            }
        });

        return view;
    }
}
