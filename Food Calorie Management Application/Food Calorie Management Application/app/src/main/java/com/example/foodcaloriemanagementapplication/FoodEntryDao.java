package com.example.foodcaloriemanagementapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

/**
 * FoodEntryDao (Data Access Object) interface for the FoodEntry entity.
 * This interface defines the database operations that can be performed on FoodEntry objects.
 * Room will generate an implementation of this interface.
 */
@Dao
public interface FoodEntryDao {
    /**
     * Inserts a new FoodEntry into the database.
     * If a FoodEntry with the same primary key already exists, the behavior depends on the conflict strategy.
     *
     * @param foodEntry The FoodEntry object to be inserted
     */
    @Insert
    void insert(FoodEntry foodEntry);

    /**
     * Retrieves all FoodEntry objects associated with a specific user.
     * This query selects all columns from the food_entries table where the username matches the provided parameter.
     *
     * @param username The username of the user whose food entries are to be retrieved
     * @return A List of FoodEntry objects belonging to the specified user
     */
    @Query("SELECT * FROM food_entries WHERE username = :username")
    List<FoodEntry> getFoodEntriesForUser(String username);

    /**
     * Deletes a specific FoodEntry from the database.
     * The FoodEntry to be deleted is identified by its primary key.
     *
     * @param foodEntry The FoodEntry object to be deleted
     */
    @Delete
    void delete(FoodEntry foodEntry);
}