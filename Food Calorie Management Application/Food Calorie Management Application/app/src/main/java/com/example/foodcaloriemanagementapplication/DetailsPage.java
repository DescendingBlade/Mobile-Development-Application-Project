package com.example.foodcaloriemanagementapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * DetailsPage is a Fragment responsible for collecting and saving user details.
 * It provides a form for users to enter their personal information and saves it
 * both to the ViewModel and the database.
 */
public class DetailsPage extends Fragment
{
    //Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Rename and change the types of the parameters
    private String mParam1;
    private String mParam2;

    private MainActivityData mainActivityData;
    private DatabaseManager databaseManager;

    // UI components for user input
    private EditText usernameEditText;
    private EditText nameEditText;
    private EditText ageEditText;
    private EditText weightEditText;
    private EditText heightEditText;
    private EditText calorieGoalEditText;
    private RadioGroup genderRadioGroup;
    private Button submitButton;
    private Button backButton;

    /**
     * Factory method to create a new instance of DetailsPage fragment.
     * @param param1 Parameter 1 (unused in current implementation)
     * @param param2 Parameter 2 (unused in current implementation)
     * @return A new instance of fragment DetailsPage
     */
    public static DetailsPage newInstance(String param1, String param2)
    {
        DetailsPage fragment = new DetailsPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is being created.
     * Initializes the DatabaseManager.
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
        databaseManager = new DatabaseManager(requireContext());
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     * Initializes UI components and sets up click listeners.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.details_page, container, false);
        mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        submitButton = view.findViewById(R.id.submitButton);
        backButton = view.findViewById(R.id.backButton);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        nameEditText = view.findViewById(R.id.nameEditText);
        ageEditText = view.findViewById(R.id.ageEditText);
        weightEditText = view.findViewById(R.id.weightEditText);
        heightEditText = view.findViewById(R.id.heightEditText);
        calorieGoalEditText = view.findViewById(R.id.calorieGoalEditText);
        genderRadioGroup = view.findViewById(R.id.genderRadioGroup);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    saveUserData();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityData.setClickedValue(0);
            }
        });
        return view;
    }

    /**
     * Saves user data to both MainActivityData and the database.
     * Displays a toast message upon successful save and navigates to the main page.
     */
    private void saveUserData() {
        String username = usernameEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        int age = Integer.parseInt(ageEditText.getText().toString().trim());
        float weight = Float.parseFloat(weightEditText.getText().toString().trim());
        float height = Float.parseFloat(heightEditText.getText().toString().trim());
        int calorieGoal = Integer.parseInt(calorieGoalEditText.getText().toString().trim());
        String gender = getSelectedGender();

        // Save to MainActivityData
        mainActivityData.setUsername(username);
        mainActivityData.setUserName(name);
        mainActivityData.setAge(age);
        mainActivityData.setWeight(weight);
        mainActivityData.setHeight(height);
        mainActivityData.setDailyCalorieGoal(calorieGoal);
        mainActivityData.setGender(gender);

        // Save to database
        databaseManager.addUser(username, name, age, gender, height, weight, calorieGoal, result -> {
            Toast.makeText(requireContext(), "User data saved successfully", Toast.LENGTH_SHORT).show();
            mainActivityData.setClickedValue(2);
        });
    }

    /**
     * Validates all input fields to ensure they are not empty.
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInputs() {
        boolean isValid = true;

        if (usernameEditText.getText().toString().trim().isEmpty() ||
                nameEditText.getText().toString().trim().isEmpty() ||
                ageEditText.getText().toString().trim().isEmpty() ||
                weightEditText.getText().toString().trim().isEmpty() ||
                heightEditText.getText().toString().trim().isEmpty() ||
                calorieGoalEditText.getText().toString().trim().isEmpty() ||
                genderRadioGroup.getCheckedRadioButtonId() == -1) {

            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    /**
     * Determines the selected gender from the radio group.
     * @return A string representing the selected gender ("Male", "Female", or "Other")
     */
    private String getSelectedGender() {
        String gender;
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.maleRadioButton) {
            gender = "Male";
        } else if (selectedId == R.id.femaleRadioButton) {
            gender = "Female";
        } else {
            gender = "Other";
        }
        return gender;
    }

    /**
     * Called when the fragment is being destroyed.
     * Ensures proper cleanup of the DatabaseManager.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseManager != null) {
            databaseManager.shutdown();
        }
    }
}