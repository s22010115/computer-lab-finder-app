package com.s22010115.myproject522513094;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LabAdapter extends RecyclerView.Adapter<LabAdapter.LabViewHolder> {

    private ArrayList<Lab> labList;

    public LabAdapter(ArrayList<Lab> labList) {
        this.labList = labList;
    }

    //Called when RecyclerView needs a new ViewHolder of the given type to represent an item
    @NonNull
    @Override
    public LabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lab, parent, false);
        return new LabViewHolder(v);
    }

    //Called to display data at the specified position
    @Override
    public void onBindViewHolder(@NonNull LabViewHolder holder, int position) {
        // Get the current Lab object
        Lab lab = labList.get(position);

        // Set lab information to the corresponding TextViews
        holder.labNameTextView.setText(lab.getName());
        holder.labStatusTextView.setText("Status: " + lab.getStatus());
        holder.totalComputersTextView.setText("Total computers: " + lab.getTotalComputers());
        holder.availableComputersTextView.setText("Available: " + lab.getAvailableComputers());
        holder.openTimeTextView.setText("Open time: " + lab.getOpenTime());
        holder.closeDayTextView.setText("Close day: " + lab.getCloseDay());

        int available = lab.getAvailableComputers();
        int total = lab.getTotalComputers();

        int colorResId;
        if (available == 0) {
            // No computers available - Red circle
            colorResId = R.drawable.circle_red;
        } else if (available <= total * 0.3) {
            // Less than or equal to 30% available - Yellow circle
            colorResId = R.drawable.circle_yellow;
        } else {
            // More than 30% available - Green circle
            colorResId = R.drawable.circle_green;
        }

        // Set the background of the availability indicator
        holder.availabilityIndicator.setBackgroundResource(colorResId);
    }

    //Returns the total number of items in the data set
    @Override
    public int getItemCount() {
        return labList.size();
    }

    //ViewHolder class that holds the views for each Lab item
    public static class LabViewHolder extends RecyclerView.ViewHolder {

        public TextView labNameTextView;
        public TextView labStatusTextView;
        public TextView totalComputersTextView;
        public TextView availableComputersTextView;
        public TextView openTimeTextView;
        public TextView closeDayTextView;
        public View availabilityIndicator;

        //Constructor that initializes all the views in the item layout
        public LabViewHolder(@NonNull View itemView) {
            super(itemView);
            labNameTextView = itemView.findViewById(R.id.labNameTextView);
            labStatusTextView = itemView.findViewById(R.id.labStatusTextView);
            totalComputersTextView = itemView.findViewById(R.id.totalComputersTextView);
            availableComputersTextView = itemView.findViewById(R.id.availableComputersTextView);
            openTimeTextView = itemView.findViewById(R.id.openTimeTextView);
            closeDayTextView = itemView.findViewById(R.id.closeDayTextView);
            availabilityIndicator = itemView.findViewById(R.id.availabilityIndicator);
        }
    }
}
