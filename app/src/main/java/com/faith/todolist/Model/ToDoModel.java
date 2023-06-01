package com.faith.todolist.Model;

public class ToDoModel extends TaskId {

    private String task, id, due, email;
    private  int status;


    public String getId() {
        return id;
    }

    public String getTask() {
       return task;
    }

    public String getEmail() {
        return email;
    }

    public String getDue()
    {
        return due;
    }

    public int getStatus()
    {
        return status;
    }



}
