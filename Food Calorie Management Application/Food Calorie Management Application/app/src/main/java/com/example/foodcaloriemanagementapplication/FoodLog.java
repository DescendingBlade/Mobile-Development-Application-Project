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
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * FoodLog is a Fragment responsible for managing the user's food log.
 * It provides functionality to search for foods, add meals to the log,
 * view logged meals, and remove meals from the log.
 */
public class FoodLog extends Fragment {
    // Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // ViewModels and data management
    private MainActivityData mainActivityData;
    private SearchResponseViewModel searchResponseViewModel;
    private DatabaseManager databaseManager;

    // UI components
    private EditText foodInputEditText;
    private Button searchButton;
    private Button addMealButton;
    private TextView resultTextView;
    private RecyclerView foodListRecyclerView;
    private Button backButton;
    private Button removeMealButton;
    private Button manualLogButton;
    private FoodAdapter foodAdapter;
    private FoodEntry selectedFoodEntry;

    /**
     * Factory method to create a new instance of FoodLog fragment.
     * @param param1 Parameter 1
     * @param param2 Parameter 2
     * @return A new instance of fragment FoodLog
     */
    public static FoodLog newInstance(String param1, String param2) {
        FoodLog fragment = new FoodLog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.foodlog_page, container, false);
        mainActivityData = new ViewModelProvider(requireActivity()).get(MainActivityData.class);
        searchResponseViewModel = new ViewModelProvider(this).get(SearchResponseViewModel.class);
        databaseManager = new DatabaseManager(getContext());
        RemoteUtilities.getInstance().setContext(getContext());

        initializeUIComponents(view);
        setupClickListeners();
        observeSearchResponse();

        // Load user's food entries when the fragment is created
        loadUserFoodEntries(getCurrentUsername());

        return view;
    }

    /**
     * Initializes all UI components by finding their views in the layout.
     * @param view The root view of the fragment
     */
    private void initializeUIComponents(View view) {
        foodInputEditText = view.findViewById(R.id.foodInputEditText);
        searchButton = view.findViewById(R.id.searchButton);
        addMealButton = view.findViewById(R.id.addMealButton);
        resultTextView = view.findViewById(R.id.resultTextView);
        foodListRecyclerView = view.findViewById(R.id.foodListRecyclerView);
        backButton = view.findViewById(R.id.backButton);
        removeMealButton = view.findViewById(R.id.removeMealButton);
        manualLogButton = view.findViewById(R.id.manualLogButton);

        foodAdapter = new FoodAdapter(this::onFoodItemClick);
        foodListRecyclerView.setAdapter(foodAdapter);
    }

    /**
     * Sets up click listeners for all interactive UI components.
     */
    private void setupClickListeners() {
        searchButton.setOnClickListener(view -> {
            String query = foodInputEditText.getText().toString();
            if (!query.isEmpty()) {
                searchFood(query);
            } else {
                Toast.makeText(getContext(), "Please enter a food name", Toast.LENGTH_SHORT).show();
            }
        });

        addMealButton.setOnClickListener(view -> openAddMealDialog());
        backButton.setOnClickListener(view -> mainActivityData.setClickedValue(2));
        removeMealButton.setOnClickListener(view -> removeMeal());
        manualLogButton.setOnClickListener(view -> mainActivityData.setClickedValue(5));
    }

    /**
     * Handles the click event on a food item in the list.
     * @param foodEntry The FoodEntry that was clicked
     */
    private void onFoodItemClick(FoodEntry foodEntry) {
        selectedFoodEntry = foodEntry;
        removeMealButton.setEnabled(true);
    }

    /**
     * Initiates a food search based on the user's input.
     * @param query The search query entered by the user
     */
    private void searchFood(String query) {
        APISearchThread searchThread = new APISearchThread(query, searchResponseViewModel);
        searchThread.start();
    }

    /**
     * Observes the search response from the ViewModel.
     */
    private void observeSearchResponse() {
        searchResponseViewModel.getSearchResponse().observe(getViewLifecycleOwner(), this::handleSearchResponse);
    }

    /**
     * Handles the search response by parsing JSON and updating the UI.
     * @param response The JSON response string from the API
     */
    private void handleSearchResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray items = jsonObject.getJSONArray("items");
            List<FoodEntry> foodEntries = new ArrayList<>();

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String foodName = item.getString("name");
                double servingSize = item.getDouble("serving_size_g");
                double calories = item.getDouble("calories");
                double fat = item.getDouble("fat_total_g");
                double protein = item.getDouble("protein_g");
                double carbs = item.getDouble("carbohydrates_total_g");
                String photoUrl = item.optString("photo", ""); // Get photo URL if available

                FoodEntry foodEntry = new FoodEntry("", foodName, servingSize, calories, fat, protein, carbs, photoUrl);
                foodEntries.add(foodEntry);
            }

            foodAdapter.setFoodItems(foodEntries);
            resultTextView.setText("Search results:");
        } catch (JSONException e) {
            e.printStackTrace();
            resultTextView.setText("Error parsing results");
        }
    }

    /**
     * Opens a dialog to add a selected meal to the food log.
     */
    private void openAddMealDialog() {
        List<FoodEntry> currentItems = foodAdapter.getFoodItems();
        if (!currentItems.isEmpty()) {
            FoodEntry selectedFood = currentItems.get(0);
            FoodEntry newEntry = new FoodEntry(
                    getCurrentUsername(),
                    selectedFood.getFoodName(),
                    selectedFood.getServingSize(),
                    selectedFood.getCalories(),
                    selectedFood.getFat(),
                    selectedFood.getProtein(),
                    selectedFood.getCarbs(),
                    selectedFood.getPhotoUrl() // Include the photo URL
            );
            addFoodToLog(newEntry);
        } else {
            Toast.makeText(getContext(), "Please search for a food item first", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Adds a food entry to the user's log in the database.
     * @param foodEntry The FoodEntry to be added to the log
     */
    private void addFoodToLog(FoodEntry foodEntry) {
        String currentUsername = getCurrentUsername();
        foodEntry.setUsername(currentUsername);

        databaseManager.addFoodEntry(foodEntry, result -> {
            Toast.makeText(getContext(), "Food added to log", Toast.LENGTH_SHORT).show();
            loadUserFoodEntries(currentUsername);
            updateTotalCalories(foodEntry.getCalories());
        });
    }

    /**
     * Loads the user's food entries from the database.
     * @param username The username of the current user
     */
    private void loadUserFoodEntries(String username) {
        databaseManager.getFoodEntriesForUser(username, foodEntries -> {
            foodAdapter.setFoodItems(foodEntries);
        });
    }

    /**
     * Retrieves the current username, using a default if not set.
     * @return The current username or "DefaultUser" if not set
     */
    private String getCurrentUsername() {
        String username = mainActivityData.getUsername();
        if (username == null || username.isEmpty()) {
            Toast.makeText(getContext(), "Username not set", Toast.LENGTH_SHORT).show();
            username = "DefaultUser";
        }
        return username;
    }

    /**
     * Removes a selected meal from the user's food log.
     */
    private void removeMeal() {
        boolean canRemoveMeal = true;

        if (selectedFoodEntry == null) {
            Toast.makeText(getContext(), "Please select a meal to remove", Toast.LENGTH_SHORT).show();
            canRemoveMeal = false;
        }

        if (canRemoveMeal) {
            String currentUsername = getCurrentUsername();

            databaseManager.removeFoodEntry(selectedFoodEntry, result -> {
                Toast.makeText(getContext(), "Food removed from log", Toast.LENGTH_SHORT).show();
                loadUserFoodEntries(currentUsername);
                updateTotalCalories(-selectedFoodEntry.getCalories());
                selectedFoodEntry = null;
                removeMealButton.setEnabled(false);
            });
        }
    }

    /**
     * Updates the total calorie count for the user.
     * @param calories The number of calories to add (or subtract if negative)
     */
    private void updateTotalCalories(double calories) {
        int currentCalories = mainActivityData.getCurrentCalorie();
        int newTotalCalories = currentCalories + (int) calories;
        mainActivityData.setCurrentCalorie(Math.max(0, newTotalCalories));
    }
}