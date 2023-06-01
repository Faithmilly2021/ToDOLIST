package com.faith.todolist.Model;

import android.widget.TextView;

public class Task {
    private String id;
    private String dueDate;
    private String description;
    private String dueTime;


    public Task(String id, String dueDate, String description, String dueTime) {
        this.id = id;
        this.dueDate = dueDate;
        this.description = description;
        this.dueTime = dueTime;
    }



        public String getDueDate() {
            return dueDate;
        }



    public String getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }

    public String getDueTime() {
        return dueTime;
    }
}
