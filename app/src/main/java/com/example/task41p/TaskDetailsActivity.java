package com.example.task41p;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TaskDetailsActivity extends AppCompatActivity {

    // Declare UI elements
    TextView tvViewTaskTitle, tvTaskDescription, tvTaskDate;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialise UI elements
        tvViewTaskTitle = findViewById(R.id.tvViewTaskTitle);
        tvTaskDescription = findViewById(R.id.tvTaskDescription);
        tvTaskDate = findViewById(R.id.tvTaskDate);
        btnBack = findViewById(R.id.btnBack);

        // Get task details from the intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String date = intent.getStringExtra("date");

        // Set the TaskView objects to display this task's details
        tvViewTaskTitle.setText(title);
        tvTaskDescription.setText(description);
        tvTaskDate.setText(date);

        // Set onClickListener for the Back button
        btnBack.setOnClickListener(v -> {
            // Finish the activity and return to the previous activity (MainActivity)
            finish();
        });
    }
}