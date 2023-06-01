package com.faith.todolist.Model;

import com.google.firebase.firestore.Exclude;

import org.checkerframework.checker.nullness.qual.NonNull;

public class TaskId {
    @Exclude
    public String TaskId;

    public  <T extends TaskId> T withId(@NonNull final  String id) {
        this.TaskId = id;
        return  (T) this;
    }

    public String getTaskId() {
        return TaskId;
    }

        public String getTitle() {
            // Add the implementation of getTitle here

            return getTitle();
    }
}
