package com.example.task41p;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    TextView tvDate;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvDate = itemView.findViewById(R.id.tvDate);
    }

    public void setSelected(boolean isSelected) {
        itemView.setActivated(isSelected); // Set item as activated when selected
    }
}
