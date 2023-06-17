package com.faith.todolist.Model;

import com.faith.todolist.Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String id;
    private String name;
    private List<Task> tasks;
    private List<String> preExistingNames;
    private List<Integer> colorIds;

    public Group(String name, String id, List<String> preExistingNames, List<Integer> colorIds) {
        this.name = name;
        this.id = id;
        this.tasks = new ArrayList<>();
        this.preExistingNames = preExistingNames;
        this.colorIds = colorIds;
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

    public List<String> getPreExistingNames() {
        return preExistingNames;
    }

    public List<Integer> getColorIds() {
        return colorIds;
    }

    @Override
    public String toString() {
        return name;
    }
}
