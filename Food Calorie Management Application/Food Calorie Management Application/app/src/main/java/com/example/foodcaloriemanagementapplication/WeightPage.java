package com.example.foodcaloriemanagementapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * A fragment representing the weight update page.
 */
public class WeightPage extends Fragment {
    /**
     * The MainActivityData view model used for data sharing between fragments.
     */
    private MainActivityData mainActivityData;

    /**
     * The DatabaseManager instance used for database operations.
     */
    private DatabaseManager databaseManager;

    /**
     * The TextView displaying the user name.
     */
    private TextView userNameTextView;

    /**
     * The TextView displaying the current weight.
     */
    private TextView currentWeightLabel;

    /**
     * The EditText for entering the new weight.
     */
    private EditText newWeightEditText;

    /**
     * The Button for updating the weight.
     */
    private Button updateButton;

    /**
     * The Button for navigating back.
     */
    private Button backButton;

    /**
     * Called when the fragment is created.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = new DatabaseManager(requireContext());
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_weight_page, container, false);
        mainActivityData = new ViewModelProvider(requireActivity()).get(MainActivityData.class);

        userNameTextView = view.findViewById(R.id.userNameTextView);
        currentWeightLabel = view.findViewById(R.id.currentWeightLabel);
        newWeightEditText = view.findViewById(R.id.newWeightEditText);
        updateButton = view.findViewById(R.id.updateButton);
        backButton = view.findViewById(R.id.backButton);

        // Set user name and current weight
        userNameTextView.setText("User: " + mainActivityData.getUserName());
        currentWeightLabel.setText("Current Weight: " + mainActivityData.getWeight() + " kg");

        updateButton.setOnClickListener(v -> updateWeight());
        backButton.setOnClickListener(v -> mainActivityData.setClickedValue(2));

        return view;
    }

    /**
     * Updates the user's weight based on the input from the EditText.
     */
    private void updateWeight() {
        String newWeightStr = newWeightEditText.getText().toString().trim();
        boolean isValidWeight = !newWeightStr.isEmpty();
        Float parsedWeight = null;

        if (isValidWeight) {
            parsedWeight = parseWeight(newWeightStr);
            isValidWeight = parsedWeight != null;
        }

        if (isValidWeight) {
            updateWeightInDatabaseAndUI(parsedWeight);
        } else {
            handleInvalidWeight(newWeightStr);
        }
    }

    /**
     * Parses the weight string into a Float value.
     *
     * @param weightStr The weight string to parse.
     * @return The parsed Float value, or null if parsing fails.
     */
    private Float parseWeight(String weightStr) {
        Float result = null;
        try {
            result = Float.parseFloat(weightStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid weight format", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    /**
     * Updates the user's weight in the database and UI.
     *
     * @param newWeight The new weight to update.
     */
    private void updateWeightInDatabaseAndUI(float newWeight) {
        mainActivityData.setWeight(newWeight);

        String username = mainActivityData.getUsername();
        databaseManager.updateUserByUsername(
                username,
                mainActivityData.getUserName(),
                mainActivityData.getAge(),
                mainActivityData.getGender(),
                mainActivityData.getHeight(),
                newWeight,
                mainActivityData.getDailyCalorieGoal(),
                result -> {
                    Toast.makeText(getContext(), "Weight updated successfully", Toast.LENGTH_SHORT).show();
                    currentWeightLabel.setText("Current Weight: " + newWeight + " kg");
                    newWeightEditText.setText("");
                }
        );
    }

    /**
     * Handles the case when the entered weight is invalid.
     *
     * @param weightStr The invalid weight string.
     */
    private void handleInvalidWeight(String weightStr) {
        if (weightStr.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a weight", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called when the fragment is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseManager != null) {
            databaseManager.shutdown();
        }
    }
}