package com.example.task41p;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context context;
    private TaskList tasks;
    private int selectedItem = RecyclerView.NO_POSITION;
    private OnItemSelectedListener listener;

    public RecyclerViewAdapter(Context context, TaskList tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                selectedItem = position;
                notifyDataSetChanged(); // Update the UI to reflect the selected item
                if (listener != null) {
                    listener.onItemSelected(position); // Notify the listener about the selected item
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.itemView.setBackgroundColor(selectedItem == position ? Color.LTGRAY : Color.WHITE); // Change background color if item is selected
        holder.tvTitle.setText(tasks.get(position)[0]);
        holder.tvDate.setText(tasks.get(position)[2]);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public int getSelectedItem() {
        return selectedItem;
    }
}
