package com.faith.todolist.Model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public class ToDoModel extends TaskId {

    private String task;
    private String id;
    private String due;
    private String email;
    private int status;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTask() {
        return task;
    }

    public String getEmail() {
        return email;
    }

    public String getDue() {
        return due;
    }

    public int getStatus() {
        return status;
    }

    public void setId(String id) {
    }

    public void setTask(String task) {
    }

    public void setDue(String due) {
    }

    public void setEmail(String email) {
    }

    public void setStatus(int status) {
    }
}

