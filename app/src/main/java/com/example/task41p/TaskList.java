package com.example.task41p;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class TaskList {
    // Declare a List of Task objects
    private final List<Task> tasks;

    // Declare a database helper to manage SQL operations
    private final TaskDBHelper dbHelper;

    // Constructor
    public TaskList(Context context) {
        tasks = new ArrayList<>();
        dbHelper = new TaskDBHelper(context);
        //clearDatabase();
        readDatabase();
        sortTasks();
    }

    // Add a task to the list
    // Takes the title, description and date
    public void addTask(String title, String description, String date) {
        // Also add to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskDBHelper.COLUMN_TITLE, title);
        values.put(TaskDBHelper.COLUMN_DESCRIPTION, description);
        values.put(TaskDBHelper.COLUMN_DATE, date);
        db.insert(TaskDBHelper.TABLE_NAME, null, values);

        tasks.add(new Task(title, description, date));
        sortTasks();
        db.close();
    }

    // Remove a task from the list, by title
    public void deleteTask(String title) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TaskDBHelper.TABLE_NAME, TaskDBHelper.COLUMN_TITLE + " = ?", new String[]{String.valueOf(title)});
        db.close();

        // Remove the task from the tasks list
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getTitle().equals(title)) {
                iterator.remove();
                break;  // exits after removing the task (first occurrence of it)
            }
        }
    }

    // Update a task, by title
    public void updateTask(String oldTitle, String newTitle, String description, String date) {
        // Delete the old task
        deleteTask(oldTitle);

        // Add the updated task
        addTask(newTitle, description, date);
    }

    // Get a task, by title
    // Returns the task as an array of strings
    public String[] getTask(String title) {
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                String taskTitle = task.getTitle();
                String description = task.getDescription();
                String date = task.getDate();

                return new String[] { taskTitle, description, date };
            }
        }
        return null;    // if there was no match found
    }

    // Get all tasks
    // Returns a List of String arrays, sorted by due date
    // Each string array contains the title and description of a task
    public List<String[]> getTaskList() {
        // Sort tasks by due date
        sortTasks();

        // Create a new List to hold the string arrays
        List<String[]> taskStrings = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            taskStrings.add(new String[]{tasks.get(i).getTitle(), tasks.get(i).getDescription()});
        }

        return taskStrings;
    }

    // Read all data from the database to this TaskList
    private void readDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskDBHelper.TABLE_NAME, null, null, null, null, null, null);

        int titleIndex = cursor.getColumnIndex(TaskDBHelper.COLUMN_TITLE);
        int descriptionIndex = cursor.getColumnIndex(TaskDBHelper.COLUMN_DESCRIPTION);
        int dateIndex = cursor.getColumnIndex(TaskDBHelper.COLUMN_DATE);

        if (titleIndex != -1 && descriptionIndex != -1 && dateIndex != -1) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                String date = cursor.getString(dateIndex);
                tasks.add(new Task(title, description, date));
            }
        } else {
            // Handle the case where one or more columns are not found
            throw new IllegalStateException("One or more columns not found in database.");
        }
        cursor.close();
        db.close();
    }

    // Clear all data from the database
    public void clearDatabase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TaskDBHelper.TABLE_NAME, null, null); // Delete all rows
        db.close();
        tasks.clear(); // Clear the tasks list
    }

    // Get task count
    public int size() {
        return tasks.size();
    }

    // Sort all tasks by due date
    public void sortTasks() {
        // Define a custom comparator to compare date strings
        Comparator<String> dateComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                try {
                    // Split the dates into day / month
                    String[] part1 = o1.split("/");
                    String[] part2 = o2.split("/");

                    // Get the day and month integers separately
                    int day1 = Integer.parseInt(part1[0]);
                    int day2 = Integer.parseInt(part2[0]);
                    int month1 = Integer.parseInt(part1[1]);
                    int month2 = Integer.parseInt(part2[1]);

                    // Compare month parts first
                    if (month1 != month2) {
                        return Integer.compare(month1, month2);
                    }
                    // If months are the same, compare the day
                    return Integer.compare(day1, day2);
                } catch (Exception e) {
                    // If the date is not given in the correct format, simply compare them as strings
                    return o1.compareTo(o2);
                }
            }
        };

        // Sort the tasks list using the custom dateComparator
        tasks.sort(Comparator.comparing(Task::getDate, dateComparator));
    }

    // Close the database helper after finished
    public void close() {
        dbHelper.close();
    }

    // Get the task by index
    public String[] get(int position) {
        String taskTitle = tasks.get(position).getTitle();
        String description = tasks.get(position).getDescription();
        String date = tasks.get(position).getDate();

        return new String[] { taskTitle, description, date };
    }
}

// Private class used by TaskList to store data for an individual task
class Task {
    // Declare task fields
    private String title;
    private String description;
    private String date;

    // Constructor
    public Task(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    // Accessors & Mutators
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}

