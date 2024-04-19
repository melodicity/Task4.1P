package com.example.task41p;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemSelectedListener {

    // Declare UI elements
    Button btnCreateTask, btnReadTask, btnUpdateTask, btnDeleteTask;
    RecyclerView rvTaskList;

    TaskList taskList;
    String[] taskSelected;
    RecyclerViewAdapter adapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialise UI elements
        btnCreateTask = findViewById(R.id.btnCreateTask);
        btnReadTask = findViewById(R.id.btnReadTask);
        btnUpdateTask = findViewById(R.id.btnUpdateTask);
        btnDeleteTask = findViewById(R.id.btnDeleteTask);
        rvTaskList = findViewById(R.id.rvTaskList);

        // Get list of tasks to display in rvTaskList
        taskList = new TaskList(this);

        // Setup rvTaskList, initialise the adapter with the task list data, and set the adapter
        rvTaskList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(getApplicationContext(), taskList);
        rvTaskList.setAdapter(adapter);

        // Set the OnItemSelectedListener for the adapter
        adapter.setOnItemSelectedListener(new RecyclerViewAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                taskSelected = taskList.get(position);
            }
        });

        // Set onClickListener for each button
        // Create a new task
        btnCreateTask.setOnClickListener(v -> {
            // Create an intent for the TaskAddActivity and start the activity
            Intent intent = new Intent(MainActivity.this, TaskAddActivity.class);
            startActivity(intent);
        });

        // Update the selected task
        btnUpdateTask.setOnClickListener(v -> {
            // Check if a task is selected
            if (taskSelected == null) {
                Toast.makeText(MainActivity.this, "No task selected to update", Toast.LENGTH_SHORT).show();
                return; // Exit without creating an intent
            }

            // Create an intent for the TaskUpdateActivity, pass the task details, and start the activity
            Intent intent = new Intent(MainActivity.this, TaskUpdateActivity.class);
            intent.putExtra("title", taskSelected[0]);
            intent.putExtra("description", taskSelected[1]);
            intent.putExtra("date", taskSelected[2]);
            startActivity(intent);
        });

        // Delete the selected task
        btnDeleteTask.setOnClickListener(v -> {
            // Check if a task is selected
            if (taskSelected == null) {
                Toast.makeText(MainActivity.this, "No task selected to delete", Toast.LENGTH_SHORT).show();
                return; // Exit without deleting any task
            }

            // Remove the selected task from the data structure, and update the RecyclerViewAdapter
            taskList.deleteTask(taskSelected[0]);
            adapter.notifyDataSetChanged();
            taskSelected = null;
        });

        // View the selected task
        btnReadTask.setOnClickListener(v -> {
            // Check if a task is selected
            if (taskSelected == null) {
                Toast.makeText(MainActivity.this, "No task selected to read", Toast.LENGTH_SHORT).show();
                return; // Exit without creating an intent
            }

            // Get the task at the selected position
            String[] task = taskList.getTask(taskSelected[0]);

            // Create an intent for the TaskDetailsActivity, pass the task details, and start the activity
            Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
            intent.putExtra("title", task[0]);
            intent.putExtra("description", task[1]);
            intent.putExtra("date", task[2]);
            startActivity(intent);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the RecyclerView when returning to this activity
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskList.close();
    }

    @Override
    public void onItemSelected(int position) {
        taskSelected = taskList.get(position);
    }
}