package com.example.foodcaloriemanagementapplication;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Represents the user data entity stored in the "users" table.
 */
@Entity(tableName = "users")
public class UserData {
    /**
     * The username of the user, serving as the primary key.
     */
    @PrimaryKey
    @NonNull
    public String username;

    /**
     * The name of the user.
     */
    public String name;

    /**
     * The age of the user.
     */
    public int age;

    /**
     * The gender of the user.
     */
    public String gender;

    /**
     * The height of the user.
     */
    public float height;

    /**
     * The weight of the user.
     */
    public float weight;

    /**
     * The daily calorie goal of the user.
     */
    public int dailyCalorieGoal;

    /**
     * The timestamp of the user's last login.
     */
    public long lastLoginTime;

    /**
     * Constructs a new UserData object with the provided user information.
     *
     * @param username        The username of the user.
     * @param name            The name of the user.
     * @param age             The age of the user.
     * @param gender          The gender of the user.
     * @param height          The height of the user.
     * @param weight          The weight of the user.
     * @param dailyCalorieGoal The daily calorie goal of the user.
     */
    public UserData(@NonNull String username, String name, int age, String gender, float height, float weight, int dailyCalorieGoal) {
        this.username = username;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.dailyCalorieGoal = dailyCalorieGoal;
        this.lastLoginTime = new Date().getTime();
    }

    /**
     * Returns the username of the user.
     *
     * @return The username of the user.
     */
    @NonNull
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username to set.
     */
    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    /**
     * Returns the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the age of the user.
     *
     * @return The age of the user.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the user.
     *
     * @param age The age to set.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the gender of the user.
     *
     * @return The gender of the user.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the user.
     *
     * @param gender The gender to set.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the height of the user.
     *
     * @return The height of the user.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the height of the user.
     *
     * @param height The height to set.
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Returns the weight of the user.
     *
     * @return The weight of the user.
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the user.
     *
     * @param weight The weight to set.
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Returns the daily calorie goal of the user.
     *
     * @return The daily calorie goal of the user.
     */
    public int getDailyCalorieGoal() {
        return dailyCalorieGoal;
    }

    /**
     * Sets the daily calorie goal of the user.
     *
     * @param calorieGoal The daily calorie goal to set.
     */
    public void setDailyCalorieGoal(int calorieGoal) {
        this.dailyCalorieGoal = calorieGoal;
    }

    /**
     * Returns the timestamp of the user's last login.
     *
     * @return The timestamp of the user's last login.
     */
    public long getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * Sets the timestamp of the user's last login.
     *
     * @param lastLoginTime The timestamp of the user's last login to set.
     */
    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}