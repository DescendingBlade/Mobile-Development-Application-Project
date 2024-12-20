package com.example.foodcaloriemanagementapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * MainActivityData is a ViewModel class that holds and manages UI-related data
 * for the main activity. It survives configuration changes and can be shared
 * across fragments.
 */
public class MainActivityData extends ViewModel {
    // MutableLiveData fields for various user data
    public MutableLiveData<Integer> clickedValue;  // Tracks navigation state
    public MutableLiveData<String> username;       // User's username
    public MutableLiveData<String> name;           // User's full name
    public MutableLiveData<Integer> age;           // User's age
    public MutableLiveData<String> gender;         // User's gender
    public MutableLiveData<Float> height;          // User's height
    public MutableLiveData<Float> weight;          // User's weight
    public MutableLiveData<Integer> dailyCalorieGoal; // User's daily calorie goal
    public int currentCalorie;  // Current calorie count (not LiveData as it changes frequently)

    /**
     * Constructor initializes all MutableLiveData fields.
     */
    public MainActivityData() {
        clickedValue = new MutableLiveData<>();
        username = new MutableLiveData<>();
        name = new MutableLiveData<>();
        age = new MutableLiveData<>();
        gender = new MutableLiveData<>();
        height = new MutableLiveData<>();
        weight = new MutableLiveData<>();
        dailyCalorieGoal = new MutableLiveData<>();
        currentCalorie = 0;
    }

    /**
     * Sets the clicked value for navigation.
     * @param value The integer value representing the navigation state
     */
    public void setClickedValue(int value) {
        clickedValue.setValue(value);
    }

    /**
     * Gets the current clicked value.
     * @return The current navigation state value
     */
    public int getClickedValue() {
        return clickedValue.getValue();
    }

    /**
     * Sets the username.
     * @param inUsername The username to set
     */
    public void setUsername(String inUsername) {
        username.setValue(inUsername);
    }

    /**
     * Gets the current username.
     * @return The current username
     */
    public String getUsername() {
        return username.getValue();
    }

    /**
     * Sets the user's name.
     * @param inName The name to set
     */
    public void setUserName(String inName) {
        name.setValue(inName);
    }

    /**
     * Gets the user's name.
     * @return The current user's name
     */
    public String getUserName() {
        return name.getValue();
    }

    /**
     * Sets the user's age.
     * @param inAge The age to set
     */
    public void setAge(int inAge) {
        age.setValue(inAge);
    }

    /**
     * Gets the user's age.
     * @return The current user's age
     */
    public int getAge() {
        return age.getValue();
    }

    /**
     * Sets the user's gender.
     * @param inGender The gender to set
     */
    public void setGender(String inGender) {
        gender.setValue(inGender);
    }

    /**
     * Gets the user's gender.
     * @return The current user's gender
     */
    public String getGender() {
        return gender.getValue();
    }

    /**
     * Sets the user's height.
     * @param inHeight The height to set
     */
    public void setHeight(float inHeight) {
        height.setValue(inHeight);
    }

    /**
     * Gets the user's height.
     * @return The current user's height
     */
    public float getHeight() {
        return height.getValue();
    }

    /**
     * Sets the user's weight.
     * @param inWeight The weight to set
     */
    public void setWeight(float inWeight) {
        weight.setValue(inWeight);
    }

    /**
     * Gets the user's weight.
     * @return The current user's weight
     */
    public float getWeight() {
        return weight.getValue();
    }

    /**
     * Sets the user's daily calorie goal.
     * @param inDailyCalorieGoal The daily calorie goal to set
     */
    public void setDailyCalorieGoal(int inDailyCalorieGoal) {
        dailyCalorieGoal.setValue(inDailyCalorieGoal);
    }

    /**
     * Gets the user's daily calorie goal.
     * @return The current user's daily calorie goal
     */
    public int getDailyCalorieGoal() {
        return dailyCalorieGoal.getValue();
    }

    /**
     * Sets the current calorie count.
     * @param calorie The current calorie count to set
     */
    public void setCurrentCalorie(int calorie) {
        this.currentCalorie = calorie;
    }

    /**
     * Gets the current calorie count.
     * @return The current calorie count
     */
    public int getCurrentCalorie() {
        return currentCalorie;
    }
}