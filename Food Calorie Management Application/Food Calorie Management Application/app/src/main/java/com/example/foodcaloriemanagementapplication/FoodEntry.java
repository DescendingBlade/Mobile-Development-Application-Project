package com.example.foodcaloriemanagementapplication;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * FoodEntry represents a single food item entry in the Room database.
 * It stores nutritional information for foods consumed by users.
 * This class is annotated as a Room Entity, which means it will be stored as a table in the database.
 */
@Entity(tableName = "food_entries",
        foreignKeys = @ForeignKey(entity = UserData.class,
                parentColumns = "username",
                childColumns = "username",
                onDelete = ForeignKey.CASCADE))
public class FoodEntry {
    /**
     * Unique identifier for each food entry.
     * The @PrimaryKey annotation with autoGenerate = true means Room will automatically
     * generate a unique ID for each new entry.
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * Username of the user who added this food entry.
     * This field creates a relationship with the UserData table.
     */
    public String username;

    /**
     * Name of the food item.
     */
    public String foodName;

    /**
     * Size of the serving in grams or milliliters.
     */
    public double servingSize;

    /**
     * Calorie content of the food item.
     */
    public double calories;

    /**
     * Fat content of the food item in grams.
     */
    public double fat;

    /**
     * Protein content of the food item in grams.
     */
    public double protein;

    /**
     * Carbohydrate content of the food item in grams.
     */
    public double carbs;

    /**
     * URL of a photo of the food item, if available.
     */
    public String photoUrl;

    /**
     * Constructor for creating a new FoodEntry object.
     *
     * @param username The username of the user adding this food entry
     * @param foodName The name of the food item
     * @param servingSize The size of the serving
     * @param calories The calorie content
     * @param fat The fat content
     * @param protein The protein content
     * @param carbs The carbohydrate content
     * @param photoUrl The URL of the food's photo (if available)
     */
    public FoodEntry(String username, String foodName, double servingSize, double calories, double fat, double protein, double carbs, String photoUrl) {
        this.username = username;
        this.foodName = foodName;
        this.servingSize = servingSize;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.carbs = carbs;
        this.photoUrl = photoUrl;
    }

    /**
     * Sets the ID of the food entry.
     * @param id The ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the food entry.
     * @return The ID of the food entry
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the username associated with this food entry.
     * @param username The username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the username associated with this food entry.
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the name of the food item.
     * @param foodName The food name to set
     */
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    /**
     * Gets the name of the food item.
     * @return The food name
     */
    public String getFoodName() {
        return foodName;
    }

    /**
     * Sets the serving size of the food item.
     * @param servingSize The serving size to set
     */
    public void setServingSize(double servingSize) {
        this.servingSize = servingSize;
    }

    /**
     * Gets the serving size of the food item.
     * @return The serving size
     */
    public double getServingSize() {
        return servingSize;
    }

    /**
     * Sets the calorie content of the food item.
     * @param calories The calorie content to set
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }

    /**
     * Gets the calorie content of the food item.
     * @return The calorie content
     */
    public double getCalories() {
        return calories;
    }

    /**
     * Sets the fat content of the food item.
     * @param fat The fat content to set
     */
    public void setFat(double fat) {
        this.fat = fat;
    }

    /**
     * Gets the fat content of the food item.
     * @return The fat content
     */
    public double getFat() {
        return fat;
    }

    /**
     * Sets the protein content of the food item.
     * @param protein The protein content to set
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }

    /**
     * Gets the protein content of the food item.
     * @return The protein content
     */
    public double getProtein() {
        return protein;
    }

    /**
     * Sets the carbohydrate content of the food item.
     * @param carbs The carbohydrate content to set
     */
    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    /**
     * Gets the carbohydrate content of the food item.
     * @return The carbohydrate content
     */
    public double getCarbs() {
        return carbs;
    }

    /**
     * Sets the URL of the photo associated with this food item.
     * @param photoUrl The photo URL to set
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * Gets the URL of the photo associated with this food item.
     * @return The photo URL
     */
    public String getPhotoUrl() {
        return photoUrl;
    }
}