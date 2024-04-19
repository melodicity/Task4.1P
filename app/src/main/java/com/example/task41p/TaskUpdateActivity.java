package com.example.task41p;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class TaskUpdateActivity extends AppCompatActivity {

    // Declare UI elements
    EditText etEditTitle, etEditDescription, etEditDate;
    Button btnEdit;

    TaskList taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        taskList = new TaskList(this);

        // Initialise UI elements
        etEditTitle = findViewById(R.id.etEditTitle);
        etEditDescription = findViewById(R.id.etEditDescription);
        etEditDate = findViewById(R.id.etEditDate);
        btnEdit = findViewById(R.id.btnEdit);

        // Get task details from the intent
        Intent intent = getIntent();
        String oldTitle = intent.getStringExtra("title");
        final String[] newTitle = {oldTitle};
        final String[] description = {intent.getStringExtra("description")};
        final String[] date = {intent.getStringExtra("date")};

        // Set the TaskView objects to display this task's details
        etEditTitle.setText(newTitle[0]);
        etEditDescription.setText(description[0]);
        etEditDate.setText(date[0]);

        // Set onClickListener for the Edit button
        // Edit the selected task
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the inputs
                newTitle[0] = etEditTitle.getText().toString().trim();
                description[0] = etEditDescription.getText().toString().trim();
                date[0] = etEditDate.getText().toString().trim(); // Ensure not null

                // Check all fields are filled
                if (newTitle[0].isEmpty() || description[0].isEmpty() || date[0].isEmpty()) {
                    // Send a toast if not
                    Toast.makeText(TaskUpdateActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return; // Return to avoid further execution
                }

                // Add the new task to the data structure
                taskList.updateTask(oldTitle, newTitle[0], description[0], date[0]);

                // Return to the MainActivity
                Intent intent = new Intent(TaskUpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}