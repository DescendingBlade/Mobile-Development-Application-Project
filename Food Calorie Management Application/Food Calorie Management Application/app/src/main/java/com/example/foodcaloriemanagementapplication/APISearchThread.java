package com.example.foodcaloriemanagementapplication;

import android.net.Uri;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * This class represents a thread for making API calls to search for nutritional information.
 * It uses the CalorieNinjas API to fetch nutrition data based on a user's query.
 */
public class APISearchThread extends Thread
{
    // Base URL for the CalorieNinjas API
    private static final String BASE_URL = "https://api.calorieninjas.com/v1/nutrition?";

    private final String query;  // The search query provided by the user
    private final SearchResponseViewModel viewModel;  // ViewModel to update with the search response
    private final RemoteUtilities remoteUtilities;  // Utility class for making network requests

    /**
     * Constructor for APISearchThread.
     * @param query The search query for nutritional information
     * @param viewModel The ViewModel to update with the search response
     */
    public APISearchThread(String query, SearchResponseViewModel viewModel)
    {
        this.query = query;
        this.viewModel = viewModel;
        this.remoteUtilities = RemoteUtilities.getInstance();  // Get singleton instance of RemoteUtilities
    }

    /**
     * The main execution method of the thread.
     * It constructs the URL, makes the API call, and updates the ViewModel with the response.
     */
    @Override
    public void run()
    {
        // Construct the URL with the query parameter
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
        builder.appendQueryParameter("query", query);
        String urlString = builder.build().toString();

        try
        {
            // Open a connection to the API
            HttpURLConnection connection = remoteUtilities.openConnection(urlString);

            // Check if the connection is successful
            if (remoteUtilities.isConnectionOkay(connection.getResponseCode()))
            {
                // Get the response string from the connection
                String response = remoteUtilities.getResponseString(connection);
                // Update the ViewModel with the response
                viewModel.setSearchResponse(response);
            }
            else
            {
                // Show an error toast if the connection is not successful
                remoteUtilities.showToast("Error: " + connection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            // Show an error toast if an IOException occurs
            remoteUtilities.showToast("Error: " + e.getMessage());
        }
    }
}