package com.example.task41p;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskAddActivity extends AppCompatActivity {

    // Declare UI elements
    EditText etAddTitle, etAddDescription, etAddDate;
    Button btnAdd;

    TaskList taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        taskList = new TaskList(this);

        // Initialise UI elements
        etAddTitle = findViewById(R.id.etAddTitle);
        etAddDescription = findViewById(R.id.etAddDescription);
        etAddDate = findViewById(R.id.etAddDate);
        btnAdd = findViewById(R.id.btnAdd);

        // Set onClickListener for the Add button
        btnAdd.setOnClickListener(v -> {
            // Get the inputs
            String title = etAddTitle.getText().toString().trim();
            String description = etAddDescription.getText().toString().trim();
            String date = etAddDate.getText().toString().trim(); // Ensure not null

            // Check all fields are filled
            if (title.isEmpty() || description.isEmpty() || date.isEmpty()) {
                // Send a toast if not
                Toast.makeText(TaskAddActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return; // Return to avoid further execution
            }

            // Add the new task to the data structure
            taskList.addTask(title, description, date);

            // Return to the MainActivity
            Intent intent = new Intent(TaskAddActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}