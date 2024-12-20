package com.example.myapplication;

import android.graphics.ImageDecoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class GridSizeSettings extends Fragment
{
    //Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Rename and change the types of the parameters
    private String mParam1;
    private String mParam2;

    /**
     * Creates a new instance of the GridSizeSettings fragment.
     * @param param1 First parameter (unused in this implementation)
     * @param param2 Second parameter (unused in this implementation)
     * @return A new instance of fragment GridSizeSettings
     */
    public static GridSizeSettings newInstance(String param1, String param2)
    {
        GridSizeSettings fragment = new GridSizeSettings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is being created.
     * Retrieves arguments if any (unused in this implementation).
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
     * Initializes buttons for different grid sizes and sets up their click listeners.
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return The View for the fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MainActivityData mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        View view = inflater.inflate(R.layout.gridsize_settings, container, false);
        Button button_6x5 = view.findViewById(R.id.button_6x5);
        Button button_7x6 = view.findViewById(R.id.button_7x6);
        Button button_8x7 = view.findViewById(R.id.button_8x7);

        // Set click listener for 6x5 grid button
        button_6x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mainActivityData.setRow(6);
                mainActivityData.setCol(5);
                mainActivityData.setClickedValue(1);
            }
        });

        // Set click listener for 7x6 grid button
        button_7x6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mainActivityData.setRow(7);
                mainActivityData.setCol(6);
                mainActivityData.setClickedValue(1);
            }
        });

        // Set click listener for 8x7 grid button
        button_8x7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mainActivityData.setRow(8);
                mainActivityData.setCol(7);
                mainActivityData.setClickedValue(1);
            }
        });

        return view;
    }
}
