package com.faith.todolist.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.faith.todolist.Model.Task;
import com.faith.todolist.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

        private List<Task> tasks;
        TextView dueDateTextView, taskTextView;


        public TaskAdapter(List<Task> tasks) {
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.each_task, parent, false);
            return new TaskViewHolder(itemView);
        }

    @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = tasks.get(position);
            holder.taskTextView.setText(task.getDescription());
            holder.dueDateTextView.setText(task.getDueDate());
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }

        static class TaskViewHolder extends RecyclerView.ViewHolder {
            TextView taskTextView;
            TextView dueDateTextView;

            TaskViewHolder(@NonNull View itemView) {
                super(itemView);
                taskTextView = itemView.findViewById(R.id.descriptionTextView);
                dueDateTextView = itemView.findViewById(R.id.due_date_tv);
            }
        }
    }


