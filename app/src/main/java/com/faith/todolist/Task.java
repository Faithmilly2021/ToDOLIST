package com.faith.todolist;

public class Task {

    private String id;
    private String taskName;
    private String email;
    private String name;
    private String groupName;

    public Task() {
        // Default constructor required for Firebase
    }

    public Task(String id, String taskName, String email) {
        this.id = id;
        this.taskName = taskName;
        this.email = email;
    }

    public Task(String description) {
    }

    public Task(Object o, String description, String dueDate, String dueTime, String name, int i) {
    }

    public String getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }

    public  String getGroupName(){
        return groupName;
    }

    public String getDescription() {
        return null;
    }

    public void setTime(String format) {
    }

    public void setDate(String format) {
    }
}



