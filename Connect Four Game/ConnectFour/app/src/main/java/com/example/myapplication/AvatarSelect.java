package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class AvatarSelect extends Fragment
{
    //Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Rename and change the types of the parameters
    private String mParam1;
    private String mParam2;

    private final int selectedAvatarResourceId = -1;

    /**
     * Creates a new instance of AvatarSelect fragment.
     * @param param1 First parameter
     * @param param2 Second parameter
     * @return A new instance of fragment AvatarSelect
     */
    public static AvatarSelect newInstance(String param1, String param2)
    {
        AvatarSelect fragment = new AvatarSelect();
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
     * Sets up avatar selection UI and handles user interactions.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.avatar_selection, container, false);
        MainActivityData mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        Button confirmAvatar = view.findViewById(R.id.confirmAvatar);

        ImageView asunaAvatar = view.findViewById(R.id.asunaAvatar);
        ImageView moonLightAvatar = view.findViewById(R.id.moonLightAvatar);
        ImageView mikasaAvatar = view.findViewById(R.id.mikasaAvatar);
        ImageView kiritoAvatar = view.findViewById(R.id.kiritoAvatar);
        ImageView daylightAvatar = view.findViewById(R.id.daylightAvatar);
        ImageView erenAvatar = view.findViewById(R.id.erenAvatar);

        // Set click listeners for each avatar
        asunaAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainActivityData.getSelect()) {
                    mainActivityData.pOneAvatar.setValue(R.drawable.avatar_asuna);
                }
                else
                {
                    mainActivityData.pTwoAvatar.setValue(R.drawable.avatar_asuna);
                }
            }
        });
        moonLightAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainActivityData.getSelect()) {
                    mainActivityData.pOneAvatar.setValue(R.drawable.avatar_moonlight);
                }
                else
                {
                    mainActivityData.pTwoAvatar.setValue(R.drawable.avatar_moonlight);
                }
            }
        });
        mikasaAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainActivityData.getSelect()) {
                    mainActivityData.pOneAvatar.setValue(R.drawable.avatar_mikasa);
                }
                else
                {
                    mainActivityData.pTwoAvatar.setValue(R.drawable.avatar_mikasa);
                }
            }
        });
        kiritoAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainActivityData.getSelect()) {
                    mainActivityData.pOneAvatar.setValue(R.drawable.avatar_kirito);
                }
                else
                {
                    mainActivityData.pTwoAvatar.setValue(R.drawable.avatar_kirito);
                }
            }
        });
        daylightAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainActivityData.getSelect()) {
                    mainActivityData.pOneAvatar.setValue(R.drawable.avatar_daylight);
                }
                else
                {
                    mainActivityData.pTwoAvatar.setValue(R.drawable.avatar_daylight);
                }
            }
        });
        erenAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainActivityData.getSelect()) {
                    mainActivityData.pOneAvatar.setValue(R.drawable.avatar_eren);
                }
                else
                {
                    mainActivityData.pTwoAvatar.setValue(R.drawable.avatar_eren);
                }
            }
        });

        // Confirm avatar selection
        confirmAvatar.setOnClickListener(new View.OnClickListener() {
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
