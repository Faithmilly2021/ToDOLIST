package com.faith.todolist;

public class User {

    private String id;
    private String name;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}



