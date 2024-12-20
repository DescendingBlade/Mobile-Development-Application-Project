package com.example.foodcaloriemanagementapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

/**
 * MainActivity is the main entry point of the application.
 * It manages the navigation between different fragments based on user interactions.
 */
public class MainActivity extends AppCompatActivity {

    // Fragments for different pages of the application
    private LoginPage loginPageFragment = new LoginPage();
    private DetailsPage detailsPageFragment = new DetailsPage();
    private MainPage mainPageFragment = new MainPage();
    private WeightPage weightPageFragment = new WeightPage();
    private FoodLog foodLogFragment = new FoodLog();
    private FoodLogManual foodLogManualFragment = new FoodLogManual();
    private MealPhotoCapture mealPhotoCaptureFragment = new MealPhotoCapture();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize MainActivityData ViewModel
        MainActivityData mainActivityData = new ViewModelProvider(this).get(MainActivityData.class);

        // Load the initial login page
        loadLoginPage();

        // Observe changes in clickedValue and load appropriate fragment
        mainActivityData.clickedValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                // Switch to appropriate fragment based on clickedValue
                switch (mainActivityData.getClickedValue()) {
                    case 0:
                        loadLoginPage();
                        break;
                    case 1:
                        loadDetailsPage();
                        break;
                    case 2:
                        loadMainPage();
                        break;
                    case 3:
                        loadWeightPage();
                        break;
                    case 4:
                        loadFoodLog();
                        break;
                    case 5:
                        loadFoodLogManual();
                        break;
                    case 6:
                        loadMealPhotoCapture();
                        break;
                }
            }
        });
    }

    /**
     * Loads the login page fragment.
     */
    public void loadLoginPage() {
        loadFragment(loginPageFragment);
    }

    /**
     * Loads the details page fragment.
     */
    public void loadDetailsPage() {
        loadFragment(detailsPageFragment);
    }

    /**
     * Loads the main page fragment.
     */
    public void loadMainPage() {
        loadFragment(mainPageFragment);
    }

    /**
     * Loads the weight page fragment.
     */
    public void loadWeightPage() {
        loadFragment(weightPageFragment);
    }

    /**
     * Loads the food log fragment.
     */
    public void loadFoodLog() {
        loadFragment(foodLogFragment);
    }

    /**
     * Loads the manual food log fragment.
     */
    public void loadFoodLogManual() {
        loadFragment(foodLogManualFragment);
    }

    /**
     * Loads the meal photo capture fragment.
     */
    public void loadMealPhotoCapture() {
        loadFragment(mealPhotoCaptureFragment);
    }

    /**
     * Helper method to load a fragment into the main frame.
     * This method reduces code duplication in the individual load methods.
     * @param fragment The fragment to be loaded
     */
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFrag = fm.findFragmentById(R.id.mainFrame);

        if (currentFrag == null) {
            fm.beginTransaction().add(R.id.mainFrame, fragment).commit();
        } else {
            fm.beginTransaction().replace(R.id.mainFrame, fragment).commit();
        }
    }
}