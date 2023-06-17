package com.faith.todolist;

import com.faith.todolist.Model.ToDoModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDataManager {

    private FirebaseFirestore firestore;

    public FirebaseDataManager() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void fetchDataFromFirestore() {
        firestore.collection("your_collection_name")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<ToDoModel> todoList = new ArrayList<>();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String name = document.getString("name");
                            //String task = document.getString("task");
                            String due = document.getString("due");
                            String email = document.getString("email");
                            int status = document.getLong("status").intValue();

                            ToDoModel todo = new ToDoModel();
                            todo.setId(id);
                            todo.setName(name);
                            //todo.setTask(task);
                            todo.setDue(due);
                            todo.setEmail(email);
                            todo.setStatus(status);

                            todoList.add(todo);
                        }

                        updateUI(todoList);
                    } else {
                        // Handle the error
                    }
                });
    }

    private void updateUI(List<ToDoModel> todoList) {

    }
}

