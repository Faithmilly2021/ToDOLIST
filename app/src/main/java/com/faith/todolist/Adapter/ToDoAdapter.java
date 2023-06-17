package com.faith.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.checkerframework.checker.nullness.qual.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faith.todolist.AddNewTask;
import com.faith.todolist.MainActivity;
import com.faith.todolist.Model.Task;
import com.faith.todolist.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.BreakIterator;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<Task> taskList;
    private MainActivity activity;
    private FirebaseFirestore firestore;

    public ToDoAdapter(MainActivity activity, List<Task> taskList) {
        this.activity = activity;
        this.taskList = taskList;
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.each_task, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.mCheckBox.setText(task.getDescription());
        holder.mDueDateTv.setText("Due On " + task.getDueDate());
        holder.mCheckBox.setChecked(task.getStatus() != 0);

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    firestore.collection("task").document(task.getTaskId()).update("status", 1);
                } else {
                    firestore.collection("task").document(task.getTaskId()).update("status", 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public BreakIterator textView;
        TextView mDueDateTv;
        CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mDueDateTv = itemView.findViewById(R.id.due_date_tv);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Task task = taskList.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putString("task", task.getDescription());
                        bundle.putString("due", task.getDueDate());
                        bundle.putString("id", task.getTaskId());

                        AddNewTask addNewTask = new AddNewTask();
                        addNewTask.setArguments(bundle);
                        addNewTask.show(activity.getSupportFragmentManager(), addNewTask.getTag());
                    }
                }
            });
        }
    }
}
