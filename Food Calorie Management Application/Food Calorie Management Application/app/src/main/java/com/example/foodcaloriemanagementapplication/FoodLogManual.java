package com.example.foodcaloriemanagementapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * FoodLogManual is a Fragment responsible for manually adding food entries to the user's log.
 * It provides a form for users to input nutritional information for a food item and saves it to the database.
 */
public class FoodLogManual extends Fragment {

    private MainActivityData mainActivityData;
    private DatabaseManager databaseManager;

    // UI components for user input
    private EditText foodNameEditText;
    private EditText servingSizeEditText;
    private EditText caloriesEditText;
    private EditText fatEditText;
    private EditText proteinEditText;
    private EditText carbsEditText;
    private Button addMealButton;

    /**
     * Called when the fragment is being created.
     * Initializes the DatabaseManager.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = new DatabaseManager(requireContext());
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     * Initializes UI components and sets up click listeners.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.foodlog_manual_page, container, false);
        mainActivityData = new ViewModelProvider(requireActivity()).get(MainActivityData.class);
        foodNameEditText = view.findViewById(R.id.foodNameEditText);
        servingSizeEditText = view.findViewById(R.id.servingSizeEditText);
        caloriesEditText = view.findViewById(R.id.caloriesEditText);
        fatEditText = view.findViewById(R.id.fatEditText);
        proteinEditText = view.findViewById(R.id.proteinEditText);
        carbsEditText = view.findViewById(R.id.carbsEditText);
        addMealButton = view.findViewById(R.id.addMealButton);

        addMealButton.setOnClickListener(v -> addMeal());

        return view;
    }

    /**
     * Handles the process of adding a new meal to the log.
     * Validates input, creates a FoodEntry object, and adds it to the database.
     */
    private void addMeal() {
        boolean canAddMeal = validateInput();

        if (canAddMeal) {
            String foodName = foodNameEditText.getText().toString().trim();
            double servingSize = Double.parseDouble(servingSizeEditText.getText().toString());
            double calories = Double.parseDouble(caloriesEditText.getText().toString());
            double fat = Double.parseDouble(fatEditText.getText().toString());
            double protein = Double.parseDouble(proteinEditText.getText().toString());
            double carbs = Double.parseDouble(carbsEditText.getText().toString());

            String username = mainActivityData.getUsername();

            FoodEntry newMeal = new FoodEntry(username, foodName, servingSize, calories, fat, protein, carbs, "");
            addMealToDatabase(newMeal, calories);
        }

        mainActivityData.setClickedValue(4);
    }

    /**
     * Adds a new meal to the database and updates the UI accordingly.
     * @param newMeal The FoodEntry object to be added to the database
     * @param calories The calorie content of the meal, used to update the total calorie intake
     */
    private void addMealToDatabase(FoodEntry newMeal, double calories) {
        databaseManager.addFoodEntry(newMeal, result -> {
            Toast.makeText(getContext(), "Meal added successfully", Toast.LENGTH_SHORT).show();
            updateCalorieIntake(calories);
            clearInputFields();
        });
    }

    /**
     * Validates user input to ensure all fields are filled.
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInput() {
        boolean isValid = true;

        if (foodNameEditText.getText().toString().trim().isEmpty() ||
                servingSizeEditText.getText().toString().trim().isEmpty() ||
                caloriesEditText.getText().toString().trim().isEmpty() ||
                fatEditText.getText().toString().trim().isEmpty() ||
                proteinEditText.getText().toString().trim().isEmpty() ||
                carbsEditText.getText().toString().trim().isEmpty()) {

            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    /**
     * Updates the total calorie intake for the user.
     * @param calories The number of calories to add to the total
     */
    private void updateCalorieIntake(double calories) {
        int currentCalories = mainActivityData.getCurrentCalorie();
        int newTotalCalories = currentCalories + (int) calories;
        mainActivityData.setCurrentCalorie(newTotalCalories);
    }

    /**
     * Clears all input fields after a meal has been added.
     */
    private void clearInputFields() {
        foodNameEditText.setText("");
        servingSizeEditText.setText("");
        caloriesEditText.setText("");
        fatEditText.setText("");
        proteinEditText.setText("");
        carbsEditText.setText("");
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