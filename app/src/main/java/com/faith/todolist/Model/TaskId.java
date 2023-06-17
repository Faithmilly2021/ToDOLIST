package com.faith.todolist.Model;

import com.google.firebase.firestore.Exclude;

import org.checkerframework.checker.nullness.qual.NonNull;

public class TaskId {
    @Exclude
    public String taskId;

    public <T extends TaskId> T withId(@NonNull final String id) {
        this.taskId = id;
        return (T) this;
    }

    public String getTaskId() {
        return taskId;
    }
}

