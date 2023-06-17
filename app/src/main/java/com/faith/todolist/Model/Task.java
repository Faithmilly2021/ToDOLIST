package com.faith.todolist.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    private String id;
    private String taskId;
    private String dueDate;
    private String description;
    private String dueTime;
    private int status;

    public Task(String id, String taskId, String dueDate, String description, String dueTime, int status) {
        this.id = id;
        this.taskId = taskId;
        this.dueDate = dueDate;
        this.description = description;
        this.dueTime = dueTime;
        this.status = status;
    }

    public Task(Parcel in) {
        id = in.readString();
        taskId = in.readString();
        dueDate = in.readString();
        description = in.readString();
        dueTime = in.readString();
        status = in.readInt();
    }

    public Task(String description) {

    }

    public String getId() {
        return id;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public String getDueTime() {
        return dueTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(taskId);
        dest.writeString(dueDate);
        dest.writeString(description);
        dest.writeString(dueTime);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
