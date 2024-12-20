package com.example.foodcaloriemanagementapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteUtilities {
    private static RemoteUtilities instance;
    private Context context;

    private RemoteUtilities() {}

    /**
     * Returns the singleton instance of RemoteUtilities.
     * If the instance doesn't exist, it will be created.
     *
     * @return The singleton instance of RemoteUtilities.
     */
    public static synchronized RemoteUtilities getInstance() {
        if (instance == null) {
            instance = new RemoteUtilities();
        }
        return instance;
    }

    /**
     * Sets the context for the RemoteUtilities instance.
     *
     * @param context The context to be set.
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Opens an HTTP connection to the specified URL.
     *
     * @param urlString The URL to connect to.
     * @return The opened HttpURLConnection.
     * @throws IOException If an I/O error occurs while opening the connection.
     */
    public HttpURLConnection openConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Api-Key", "JAx+n3WJ1tdfCBy2C1wRcg==vjFKqXtjG900XIXp");
        return connection;
    }

    /**
     * Checks if the HTTP response code indicates a successful connection.
     *
     * @param responseCode The HTTP response code.
     * @return true if the response code is HTTP_OK, false otherwise.
     */
    public boolean isConnectionOkay(int responseCode) {
        return responseCode == HttpURLConnection.HTTP_OK;
    }

    /**
     * Retrieves the response string from the HTTP connection.
     *
     * @param connection The HttpURLConnection to get the response from.
     * @return The response string.
     * @throws IOException If an I/O error occurs while reading the response.
     */
    public String getResponseString(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    /**
     * Shows a toast message on the main thread.
     *
     * @param message The message to be displayed in the toast.
     */
    public void showToast(final String message) {
        if (context != null) {
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            );
        }
    }
}