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
}



