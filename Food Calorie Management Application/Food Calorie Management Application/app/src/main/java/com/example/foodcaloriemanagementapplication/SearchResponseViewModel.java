package com.example.foodcaloriemanagementapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchResponseViewModel extends ViewModel {
    /**
     * The MutableLiveData object that holds the search response string.
     * It is used to update the search response value and notify observers.
     */
    private final MutableLiveData<String> searchResponse = new MutableLiveData<>();

    /**
     * Sets the search response value in the MutableLiveData object.
     * This method posts the value to the main thread using postValue().
     *
     * @param response The search response string to be set.
     */
    public void setSearchResponse(String response) {
        searchResponse.postValue(response);
    }

    /**
     * Returns the LiveData object that holds the search response string.
     * Observers can subscribe to this LiveData to receive updates when the search response changes.
     *
     * @return The LiveData object representing the search response.
     */
    public LiveData<String> getSearchResponse() {
        return searchResponse;
    }
}