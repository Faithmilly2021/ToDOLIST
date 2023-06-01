package com.faith.todolist.Model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String id;
    private String name;
    private List<Task> tasks;

    public Group(String name, String id) {
        this.name = name;
        this.id = id;
        this.tasks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public String toString() {
        return name;
    }
}
