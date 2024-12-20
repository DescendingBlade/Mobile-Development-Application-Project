package com.example.foodcaloriemanagementapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * FoodViewHolder represents a single item view in the RecyclerView that displays food entries.
 * It is responsible for binding data to the view and handling user interactions.
 */
class FoodViewHolder extends RecyclerView.ViewHolder {
    private TextView foodNameTextView;
    private TextView caloriesTextView;
    private ImageView foodImageView;
    private FoodAdapter adapter;
    private FirebaseStorage storage;

    /**
     * Constructor for FoodViewHolder.
     * Initializes the view holder and sets up click listeners.
     *
     * @param itemView The view representing a single item in the RecyclerView
     * @param adapter The FoodAdapter that this ViewHolder is associated with
     */
    public FoodViewHolder(@NonNull View itemView, FoodAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
        foodNameTextView = itemView.findViewById(R.id.foodNameTextView);
        caloriesTextView = itemView.findViewById(R.id.caloriesTextView);
        foodImageView = itemView.findViewById(R.id.foodImageView);
        storage = FirebaseStorage.getInstance();

        // Set up click listener for the entire item view
        itemView.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                adapter.selectItem(position);
            }
        });
    }

    /**
     * Binds a FoodEntry object to the view holder.
     * Updates the UI with food entry data and loads the image if available.
     *
     * @param foodEntry The FoodEntry object containing the data to be displayed
     * @param isSelected Boolean indicating whether this item is currently selected
     */
    public void bind(FoodEntry foodEntry, boolean isSelected) {
        foodNameTextView.setText(foodEntry.getFoodName());
        caloriesTextView.setText(String.format("%.2f calories", foodEntry.getCalories()));
        itemView.setBackgroundColor(isSelected ? Color.LTGRAY : Color.TRANSPARENT);

        if (foodEntry.getPhotoUrl() != null && !foodEntry.getPhotoUrl().isEmpty()) {
            loadImage(foodEntry.getPhotoUrl());
        } else {
            foodImageView.setVisibility(View.GONE);
        }
    }

    /**
     * Loads an image from Firebase Storage and displays it in the ImageView.
     * Handles different types of URLs (Google Storage, HTTPS, relative path).
     *
     * @param imageUrl The URL or path of the image to be loaded
     */
    private void loadImage(String imageUrl) {
        StorageReference imageRef;
        if (imageUrl.startsWith("gs://")) {
            // If it's a Google Storage URL
            imageRef = storage.getReferenceFromUrl(imageUrl);
        } else if (imageUrl.startsWith("https://firebasestorage.googleapis.com")) {
            // If it's an HTTPS URL from Firebase Storage
            imageRef = storage.getReferenceFromUrl(imageUrl);
        } else {
            // If it's a relative path
            imageRef = storage.getReference().child(imageUrl);
        }

        final long ONE_MEGABYTE = 1024 * 1024;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            foodImageView.setImageBitmap(bitmap);
            foodImageView.setVisibility(View.VISIBLE);
        }).addOnFailureListener(exception -> {
            // Handle any errors
            foodImageView.setVisibility(View.VISIBLE);
        });
    }
}