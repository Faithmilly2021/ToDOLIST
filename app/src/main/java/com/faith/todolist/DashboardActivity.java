package com.faith.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.faith.todolist.Adapter.TaskAdapter;
import com.faith.todolist.Model.Task;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        taskRecyclerView = findViewById(R.id.tasksRecyclerView);
        taskList = new ArrayList<>();

        // Set up RecyclerView with TaskAdapter
        taskAdapter = new TaskAdapter(taskList);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskRecyclerView.setAdapter(taskAdapter);

        // Check for incoming task data from AddTaskActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("newTask")) {
            Task newTask = intent.getParcelableExtra("newTask");
            if (newTask != null) {
                // Add the new task to the taskList
                taskList.add(newTask);
                // Notify the adapter about the data change
                taskAdapter.notifyDataSetChanged();
            }
        }
    }
}
