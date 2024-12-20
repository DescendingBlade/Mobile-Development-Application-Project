package com.example.foodcaloriemanagementapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * FoodAdapter is a RecyclerView.Adapter implementation for displaying a list of food items.
 * It manages the data set of FoodEntry objects and creates ViewHolders for each item.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {
    private List<FoodEntry> foodItems = new ArrayList<>();
    private OnFoodItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    /**
     * Interface definition for a callback to be invoked when a food item is clicked.
     */
    public interface OnFoodItemClickListener {
        void onFoodItemClick(FoodEntry foodEntry);
    }

    /**
     * Constructor for FoodAdapter.
     * @param listener The listener that will handle food item click events
     */
    public FoodAdapter(OnFoodItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View (not used in this implementation)
     * @return A new FoodViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view, this);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodEntry foodEntry = foodItems.get(position);
        holder.bind(foodEntry, position == selectedPosition);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter
     */
    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    /**
     * Sets the list of food items and notifies the adapter to refresh the view.
     * @param foodItems The new list of food items to be displayed
     */
    public void setFoodItems(List<FoodEntry> foodItems) {
        this.foodItems = foodItems;
        notifyDataSetChanged();
    }

    /**
     * Returns the current list of food items.
     * @return The list of food items currently held by the adapter
     */
    public List<FoodEntry> getFoodItems() {
        return foodItems;
    }

    /**
     * Selects an item at the specified position and notifies the listener.
     * @param position The position of the item to be selected
     */
    public void selectItem(int position) {
        int previousSelectedPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(previousSelectedPosition);
        notifyItemChanged(selectedPosition);
        if (listener != null) {
            listener.onFoodItemClick(foodItems.get(position));
        }
    }
}