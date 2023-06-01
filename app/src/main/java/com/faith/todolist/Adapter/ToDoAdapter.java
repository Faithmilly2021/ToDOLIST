package com.faith.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.faith.todolist.AddNewTask;
import com.faith.todolist.MainActivity;
import com.faith.todolist.Model.ToDoModel;
import com.faith.todolist.R;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private final List<ToDoModel> todoList;
    private final MainActivity activity;
    private FirebaseFirestore firestore;

    public ToDoAdapter(MainActivity mainActivity , List<ToDoModel> todoList) {
        this.todoList = todoList;
        activity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.each_task , parent , false);
        firestore = FirebaseFirestore.getInstance();

        return new MyViewHolder(view);
    }

    public void deleteTask(int position) {
        ToDoModel toDoModel = todoList.get(position);
        firestore.collection("task").document(toDoModel.TaskId).delete();
        todoList.remove(position);
        notifyItemRemoved(position);
    }
    public Context getContext(){
        return activity;
    }

    public void editTask(int position) {
        ToDoModel toDoModel = todoList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("task" , toDoModel.getTask());
        bundle.putString("due" , toDoModel.getDue());
        bundle.putString("id" , toDoModel.TaskId);

        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager() , addNewTask.getTag());
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
     ToDoModel toDoModel = todoList.get(position);
        try {     holder.mCheckBox.setText(toDoModel.getTask());
        } catch (Exception e) {
            e.printStackTrace();
        }
     holder.mDueDateTv.setText("Due On " + toDoModel.getDue());


        try {   holder.mCheckBox.setChecked(toBoolean(toDoModel.getStatus()));
        } catch (Exception e) {
            e.printStackTrace();
        }

     holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if (isChecked){
                 firestore.collection("task").document(toDoModel.TaskId).update("status" , 1);
             } else{
                 firestore.collection("task").document(toDoModel.TaskId).update("status" , 0);
             }
         }
     });
    }
 private boolean toBoolean(int status) {
        return status != 0;
 }
    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void filterList(List<ToDoModel> filteredList) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mDueDateTv;
        CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mDueDateTv = itemView.findViewById(R.id.due_date_tv);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);
        }
    }
}
