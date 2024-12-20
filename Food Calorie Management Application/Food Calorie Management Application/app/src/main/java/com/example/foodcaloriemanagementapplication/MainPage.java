package com.example.foodcaloriemanagementapplication;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * MainPage is a Fragment that displays the main dashboard of the application.
 * It shows user information, BMI, calorie progress, and navigation buttons.
 */
public class MainPage extends Fragment
{
    //Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Rename and change the types of the parameters
    private String mParam1;
    private String mParam2;

    private MainActivityData mainActivityData;
    private Button logoutButton;
    private ImageButton weightButton;
    private ImageButton foodLogButton;
    private ImageButton cameraButton;
    private TextView nameTextView;
    private TextView ageTextView;
    private TextView genderTextView;
    private TextView weightTextView;
    private TextView calorieProgressText;
    private ProgressBar calorieProgressBar;
    private TextView bmiTextView;
    private ImageView bmiImageView;

    /**
     * Factory method to create a new instance of MainPage fragment.
     */
    public static MainPage newInstance(String param1, String param2)
    {
        MainPage fragment = new MainPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is being created.
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
     * Called to create the view hierarchy associated with the fragment.
     * Initializes UI components and sets up click listeners.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.main_page, container, false);
        mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        // Initialize UI components
        logoutButton = view.findViewById(R.id.logoutButton);
        weightButton = view.findViewById(R.id.weightButton);
        foodLogButton = view.findViewById(R.id.foodLogButton);
        cameraButton = view.findViewById(R.id.cameraButton);
        nameTextView = view.findViewById(R.id.nameTextView);
        ageTextView = view.findViewById(R.id.ageTextView);
        genderTextView = view.findViewById(R.id.genderTextView);
        weightTextView = view.findViewById(R.id.weightTextView);
        calorieProgressText = view.findViewById(R.id.calorieProgressText);
        calorieProgressBar = view.findViewById(R.id.calorieProgressBar);
        bmiTextView = view.findViewById(R.id.bmiTextView);
        bmiImageView = view.findViewById(R.id.bmiImageView);

        // Set user data to UI components
        nameTextView.setText("Name: " + mainActivityData.getUserName());
        ageTextView.setText("Age: " + mainActivityData.getAge());
        genderTextView.setText("Gender: " + mainActivityData.getGender());
        weightTextView.setText("Current Weight: "+ mainActivityData.getWeight()+" kg");
        float bmi = calculateBMI();
        bmiTextView.setText(String.format("BMI: %.1f - %s", bmi, getBMICategory(bmi)));
        updateBMIImage(bmi);

        updateCalorieProgress();

        // Set up click listeners
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityData.setClickedValue(0);
            }
        });

        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityData.setClickedValue(3);
            }
        });

        foodLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityData.setClickedValue(4);
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityData.setClickedValue(6);
            }
        });
        return view;
    }

    /**
     * Updates the calorie progress bar and text.
     */
    private void updateCalorieProgress() {
        int currentCalorie = mainActivityData.getCurrentCalorie();
        int dailyGoal = mainActivityData.getDailyCalorieGoal();

        calorieProgressText.setText(currentCalorie + " / " + dailyGoal + " kcal");

        if (dailyGoal > 0) {
            int progress = (int) ((currentCalorie / (float) dailyGoal) * 100);
            calorieProgressBar.setProgress(Math.min(progress, 100));
        } else {
            calorieProgressBar.setProgress(0);
        }
    }

    /**
     * Calculates the user's BMI based on their weight, height, and gender.
     */
    private float calculateBMI() {
        float weightKg = mainActivityData.getWeight();
        float heightM = mainActivityData.getHeight() / 100f; // Convert cm to m
        String gender = mainActivityData.getGender();

        float bmi = weightKg / (heightM * heightM);

        // Adjust for gender (simplified approach)
        if (gender.equalsIgnoreCase("female")) {
            bmi *= 0.95f; // Approximate adjustment for females
        }

        return bmi;
    }

    /**
     * Determines the BMI category based on the calculated BMI value.
     */
    private String getBMICategory(float bmi) {
        String category;

        if (bmi < 18.5) {
            category = "Underweight";
        } else if (bmi < 25) {
            category = "Normal weight";
        } else if (bmi < 30) {
            category = "Overweight";
        } else {
            category = "Obese";
        }

        return category;
    }

    /**
     * Updates the BMI image based on the calculated BMI value.
     */
    private void updateBMIImage(float bmi) {
        if (bmi < 18.5) {
            bmiImageView.setImageResource(R.drawable.underweight);
        } else if (bmi < 25) {
            bmiImageView.setImageResource(R.drawable.normalweight);
        } else if (bmi < 30) {
            bmiImageView.setImageResource(R.drawable.overweight);
        } else {
            bmiImageView.setImageResource(R.drawable.obese);
        }
    }

    /**
     * Called when the fragment is resumed.
     * Updates the calorie progress.
     */
    @Override
    public void onResume() {
        super.onResume();
        updateCalorieProgress();
    }
}