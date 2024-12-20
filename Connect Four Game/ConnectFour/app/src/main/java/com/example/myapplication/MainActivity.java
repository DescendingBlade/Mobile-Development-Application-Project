package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    // Fragment instances for different screens
    Menu menuFragment = new Menu();
    ProfileCreate profileFragment = new ProfileCreate();
    AvatarSelect avatarSelectFragment = new AvatarSelect();
    Game gameFragment = new Game();
    ColourSelect colourSelectFragment = new ColourSelect();
    Settings settingFragment = new Settings();
    GridSizeSettings gridSizeSettingsFragment = new GridSizeSettings();
    Statistics statisticsFragment = new Statistics();
    EndGame endGameFragment = new EndGame();

    /**
     * Called when the activity is starting.
     * This is where most initialization should go: calling setContentView(int) to inflate the
     * activity's UI, using findViewById(int) to programmatically interact with widgets in the UI, etc.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityData mainActivityData = new ViewModelProvider(this).get(MainActivityData.class);
        loadMenuFragment();

        // Observe changes in clickedValue and load appropriate fragment
        mainActivityData.clickedValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer)
            {
                // Switch to appropriate fragment based on clickedValue
                switch (mainActivityData.getClickedValue()) {
                    case 0:
                        loadMenuFragment();
                        break;
                    case 1:
                    case 9:
                        loadProfileFragment();
                        break;
                    case 2:
                        loadAvatarSelect();
                        break;
                    case 3:
                        loadColourSelect();
                        break;
                    case 4:
                        loadGame();
                        break;
                    case 5:
                        loadSettings();
                        break;
                    case 6:
                        loadGridSettings();
                        break;
                    case 7:
                        loadStatistics();
                        break;
                    case 8:
                        loadEndgame();
                        break;
                }
            }
        });
    }

    /**
     * Loads the Menu fragment into the main frame.
     */
    private void loadMenuFragment()
    {
        loadFragment(menuFragment);
    }

    /**
     * Loads the Profile Creation fragment into the main frame.
     */
    private void loadProfileFragment()
    {
        loadFragment(profileFragment);
    }

    /**
     * Loads the Avatar Selection fragment into the main frame.
     */
    private void loadAvatarSelect()
    {
        loadFragment(avatarSelectFragment);
    }

    /**
     * Loads the Colour Selection fragment into the main frame.
     */
    private void loadColourSelect()
    {
        loadFragment(colourSelectFragment);
    }

    /**
     * Loads the Game fragment into the main frame.
     */
    private void loadGame()
    {
        loadFragment(gameFragment);
    }

    /**
     * Loads the Settings fragment into the main frame.
     */
    private void loadSettings()
    {
        loadFragment(settingFragment);
    }

    /**
     * Loads the Grid Size Settings fragment into the main frame.
     */
    private void loadGridSettings()
    {
        loadFragment(gridSizeSettingsFragment);
    }

    /**
     * Loads the Statistics fragment into the main frame.
     */
    private void loadStatistics()
    {
        loadFragment(statisticsFragment);
    }

    /**
     * Loads the End Game fragment into the main frame.
     */
    private void loadEndgame()
    {
        loadFragment(endGameFragment);
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