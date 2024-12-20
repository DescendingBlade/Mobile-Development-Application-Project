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

import java.util.Date;

/**
 * LoginPage is a Fragment responsible for handling user login and account creation.
 * It provides a simple interface for users to enter their username and either log in
 * or navigate to account creation.
 */
public class LoginPage extends Fragment {
    // Fragment initialization parameters (not used in current implementation)
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // ViewModels and data management
    private MainActivityData mainActivityData;
    private DatabaseManager databaseManager;

    // UI components
    private EditText usernameInput;
    private Button loginButton;
    private Button createAccountButton;

    /**
     * Factory method to create a new instance of LoginPage fragment.
     * @param param1 Parameter 1 (unused in current implementation)
     * @param param2 Parameter 2 (unused in current implementation)
     * @return A new instance of fragment LoginPage
     */
    public static LoginPage newInstance(String param1, String param2) {
        LoginPage fragment = new LoginPage();
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
        databaseManager = new DatabaseManager(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_page, container, false);
        mainActivityData = new ViewModelProvider(requireActivity()).get(MainActivityData.class);
        databaseManager = new DatabaseManager(requireContext());
        usernameInput = view.findViewById(R.id.usernameInput);
        loginButton = view.findViewById(R.id.loginButton);
        createAccountButton = view.findViewById(R.id.createAccountButton);

        loginButton.setOnClickListener(v -> attemptLogin());
        createAccountButton.setOnClickListener(v -> mainActivityData.setClickedValue(1));

        return view;
    }

    /**
     * Attempts to log in the user with the provided username.
     * Validates the input and proceeds with login if valid.
     */
    private void attemptLogin() {
        String username = usernameInput.getText().toString().trim();
        boolean isValidUsername = !username.isEmpty();

        if (isValidUsername) {
            proceedWithLogin(username);
        } else {
            Toast.makeText(getContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Proceeds with the login process by checking if the user exists in the database.
     * @param username The username to check
     */
    private void proceedWithLogin(String username) {
        databaseManager.getUserByUsername(username, result -> {
            if (result != null) {
                // User exists, proceed with login
                loginUser(result);
            } else {
                // User doesn't exist
                Toast.makeText(getContext(), "User doesn't exist. Please create an account.", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Logs in the user by updating their last login time and retrieving their data.
     * @param userData The UserData object of the logging in user
     */
    private void loginUser(UserData userData) {
        // Update last login time
        userData.setLastLoginTime(new Date().getTime());

        databaseManager.updateUser(userData, result -> {
            // Login successful
            getData(userData);

            // Get total calories for the user
            databaseManager.getTotalCaloriesForUser(userData.getUsername(), totalCalories -> {
                mainActivityData.setCurrentCalorie(totalCalories);

                // Navigate to main page or dashboard
                mainActivityData.setClickedValue(2);
                Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
            });
        });
    }

    /**
     * Retrieves user data and sets it in the MainActivityData ViewModel.
     * @param userData The UserData object containing the user's information
     */
    public void getData(UserData userData) {
        mainActivityData.setUsername(userData.getUsername());
        mainActivityData.setUserName(userData.getName());
        mainActivityData.setAge(userData.getAge());
        mainActivityData.setGender(userData.getGender());
        mainActivityData.setHeight(userData.getHeight());
        mainActivityData.setWeight(userData.getWeight());
        mainActivityData.setDailyCalorieGoal(userData.getDailyCalorieGoal());
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