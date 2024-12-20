package com.example.foodcaloriemanagementapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

/**
 * Data Access Object (DAO) interface for accessing and manipulating user data in the database.
 */
@Dao
public interface UserDataDao {
    /**
     * Inserts a new user into the database.
     *
     * @param userData The UserData object to be inserted.
     */
    @Insert
    void insertUser(UserData userData);

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all UserData objects stored in the database.
     */
    @Query("SELECT * FROM users")
    List<UserData> getAllUser();

    /**
     * Updates an existing user in the database.
     *
     * @param userData The updated UserData object.
     */
    @Update
    void updateUser(UserData userData);

    /**
     * Deletes a user from the database.
     *
     * @param userData The UserData object to be deleted.
     */
    @Delete
    void deleteUser(UserData userData);

    /**
     * Finds a user in the database by their username.
     *
     * @param username The username to search for.
     * @return The UserData object matching the provided username, or null if not found.
     */
    @Query("SELECT * FROM users WHERE username LIKE :username")
    UserData findUserByUsername(String username);

    /**
     * Updates a user's information in the database based on their username.
     *
     * @param username        The username of the user to update.
     * @param newName         The new name for the user.
     * @param newAge          The new age for the user.
     * @param newGender       The new gender for the user.
     * @param newHeight       The new height for the user.
     * @param newWeight       The new weight for the user.
     * @param newCalorieGoal The new daily calorie goal for the user.
     */
    @Query("UPDATE users SET name = :newName, age = :newAge, gender = :newGender, height = :newHeight, weight = :newWeight, dailyCalorieGoal = :newCalorieGoal WHERE username = :username")
    void updateUserByUsername(String username, String newName, int newAge, String newGender, float newHeight, float newWeight, int newCalorieGoal);
}