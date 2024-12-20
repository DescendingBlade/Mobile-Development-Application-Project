package com.example.foodcaloriemanagementapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DatabaseManager handles all database operations for the application.
 * It provides methods to interact with the Room database on a background thread
 * and return results on the main thread using callbacks.
 * This class follows the singleton pattern and uses an ExecutorService for background operations.
 */
public class DatabaseManager {
    private AppDatabase database;
    private UserDataDao userDataDao;
    private FoodEntryDao foodEntryDao;
    private ExecutorService executor;
    private Handler mainHandler;

    /**
     * Constructor for DatabaseManager.
     * Initializes the database, DAOs, executor for background tasks, and handler for main thread callbacks.
     * @param context The application context used to get the database instance
     */
    public DatabaseManager(Context context) {
        database = AppDatabase.getInstance(context);
        userDataDao = database.userDataDao();
        foodEntryDao = database.foodEntryDao();
        executor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Adds a new user to the database.
     * @param username The user's unique identifier
     * @param name The user's full name
     * @param age The user's age
     * @param gender The user's gender
     * @param height The user's height
     * @param weight The user's weight
     * @param dailyCalorieGoal The user's daily calorie goal
     * @param callback Callback to be executed when the operation is complete
     */
    public void addUser(String username, String name, int age, String gender, float height, float weight, int dailyCalorieGoal, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            UserData newUser = new UserData(username, name, age, gender, height, weight, dailyCalorieGoal);
            userDataDao.insertUser(newUser);
            mainHandler.post(() -> callback.onComplete(null));
        });
    }

    /**
     * Updates an existing user's information in the database.
     * @param username The username of the user to update
     * @param newName The new name for the user
     * @param newAge The new age for the user
     * @param newGender The new gender for the user
     * @param newHeight The new height for the user
     * @param newWeight The new weight for the user
     * @param newCalorieGoal The new daily calorie goal for the user
     * @param callback Callback to be executed when the operation is complete
     */
    public void updateUserByUsername(String username, String newName, int newAge, String newGender, float newHeight, float newWeight, int newCalorieGoal, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            userDataDao.updateUserByUsername(username, newName, newAge, newGender, newHeight, newWeight, newCalorieGoal);
            mainHandler.post(() -> callback.onComplete(null));
        });
    }

    /**
     * Retrieves a user from the database by their username.
     * @param username The username of the user to retrieve
     * @param callback Callback to be executed with the retrieved UserData
     */
    public void getUserByUsername(String username, DatabaseCallback<UserData> callback) {
        executor.execute(() -> {
            UserData userData = userDataDao.findUserByUsername(username);
            mainHandler.post(() -> callback.onComplete(userData));
        });
    }

    /**
     * Retrieves all users from the database.
     * @param callback Callback to be executed with the list of all UserData
     */
    public void getAllUsers(DatabaseCallback<List<UserData>> callback) {
        executor.execute(() -> {
            List<UserData> userList = userDataDao.getAllUser();
            mainHandler.post(() -> callback.onComplete(userList));
        });
    }

    /**
     * Deletes a user from the database.
     * @param userData The UserData object to be deleted
     * @param callback Callback to be executed when the operation is complete
     */
    public void deleteUser(UserData userData, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            userDataDao.deleteUser(userData);
            mainHandler.post(() -> callback.onComplete(null));
        });
    }

    /**
     * Adds a new food entry to the database.
     * @param foodEntry The FoodEntry object to be added
     * @param callback Callback to be executed when the operation is complete
     */
    public void addFoodEntry(FoodEntry foodEntry, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            foodEntryDao.insert(foodEntry);
            mainHandler.post(() -> callback.onComplete(null));
        });
    }

    /**
     * Retrieves all food entries for a specific user.
     * @param username The username of the user whose food entries to retrieve
     * @param callback Callback to be executed with the list of FoodEntry objects
     */
    public void getFoodEntriesForUser(String username, DatabaseCallback<List<FoodEntry>> callback) {
        executor.execute(() -> {
            List<FoodEntry> foodEntries = foodEntryDao.getFoodEntriesForUser(username);
            mainHandler.post(() -> callback.onComplete(foodEntries));
        });
    }

    /**
     * Updates an existing user's information in the database.
     * @param userData The updated UserData object
     * @param callback Callback to be executed when the operation is complete
     */
    public void updateUser(UserData userData, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            userDataDao.updateUser(userData);
            mainHandler.post(() -> callback.onComplete(null));
        });
    }

    /**
     * Calculates the total calories consumed by a user from their food entries.
     * @param username The username of the user
     * @param callback Callback to be executed with the total calories
     */
    public void getTotalCaloriesForUser(String username, DatabaseCallback<Integer> callback) {
        executor.execute(() -> {
            List<FoodEntry> foodEntries = foodEntryDao.getFoodEntriesForUser(username);
            int totalCalories = foodEntries.stream().mapToInt(entry -> (int) entry.getCalories()).sum();
            mainHandler.post(() -> callback.onComplete(totalCalories));
        });
    }

    /**
     * Removes a food entry from the database.
     * @param foodEntry The FoodEntry object to be removed
     * @param callback Callback to be executed when the operation is complete
     */
    public void removeFoodEntry(FoodEntry foodEntry, DatabaseCallback<Void> callback) {
        executor.execute(() -> {
            foodEntryDao.delete(foodEntry);
            mainHandler.post(() -> callback.onComplete(null));
        });
    }

    /**
     * Interface for database operation callbacks.
     * @param <T> The type of result expected from the database operation
     */
    public interface DatabaseCallback<T> {
        /**
         * Called when a database operation is complete.
         * @param result The result of the database operation
         */
        void onComplete(T result);
    }

    /**
     * Shuts down the executor service.
     * This method should be called when the DatabaseManager is no longer needed
     * to ensure proper cleanup of resources.
     */
    public void shutdown() {
        executor.shutdown();
    }
}