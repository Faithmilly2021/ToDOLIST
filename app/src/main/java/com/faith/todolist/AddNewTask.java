package com.faith.todolist;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import com.faith.todolist.Model.Group;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddNewTask extends DialogFragment {

    public static final String TAG = "AddNewTask";
    private static final String NOTIFICATION_CHANNEL_ID = "reminder_channel";
    private static final int NOTIFICATION_ID = 123;

    public interface OnDialogCloseListener {
        void onDialogClose(DialogInterface dialogInterface);
    }

    private Spinner groupSpinner;
    private EditText descriptionEditText;
    private TextView dueDateTextView;
    private TextView dueTimeTextView;
    private Button saveButton;

    private FirebaseFirestore firestore;
    private Context context;

    private List<Group> groupList;

    private Calendar calendar;

    public static AddNewTask newInstance() {
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        groupSpinner = view.findViewById(R.id.groupSpinner);
        dueDateTextView = view.findViewById(R.id.set_due_tv);
        dueTimeTextView = view.findViewById(R.id.set_time_tv);
        descriptionEditText = view.findViewById(R.id.description_edittext);
        saveButton = view.findViewById(R.id.save_btn);

        firestore = FirebaseFirestore.getInstance();

        groupList = getGroups();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group selectedGroup = (Group) groupSpinner.getSelectedItem();
                String description = descriptionEditText.getText().toString();
                String dueDate = dueDateTextView.getText().toString();
                String dueTime = dueTimeTextView.getText().toString();

                Task newTask = new Task(null, description, dueDate, dueTime, selectedGroup.getName(), 0);

                // Save the task
                saveTask(selectedGroup, newTask);

                // Schedule the reminder
                scheduleReminder(newTask);

                dismiss();
            }
        });

        ArrayAdapter<Group> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, groupList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(spinnerAdapter);

        calendar = Calendar.getInstance();

        dueDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        dueTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String selectedDate = dateFormat.format(calendar.getTime());
                        dueDateTextView.setText(selectedDate);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                        String selectedTime = timeFormat.format(calendar.getTime());
                        dueTimeTextView.setText(selectedTime);
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
        );
        timePickerDialog.show();
    }

    private void saveTask(Group group, Task task) {
        String groupId = group.getId();

        // Get a reference to the "tasks" collection in the Firestore database
        CollectionReference tasksCollection = firestore.collection("groups").document(groupId).collection("tasks");

        // Add a new document with an auto-generated ID
        tasksCollection.add(task)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Task saved successfully
                        dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                        Toast.makeText(context, "Failed to save task: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();

        // Group 1
        List<String> tasks1 = new ArrayList<>();
        tasks1.add("Task1");
        tasks1.add("Task2");
        List<Integer> colors1 = new ArrayList<>();
        colors1.add(R.color.groupColor1);
        groups.add(new Group("Personal", "groupId1", tasks1, colors1));

        // Group 2
        List<String> tasks2 = new ArrayList<>();
        tasks2.add("Task3");
        tasks2.add("Task4");
        List<Integer> colors2 = new ArrayList<>();
        colors2.add(R.color.groupColor2);
        groups.add(new Group("Work", "groupId2", tasks2, colors2));

        // Group 3
        List<String> tasks3 = new ArrayList<>();
        tasks3.add("Task5");
        tasks3.add("Task6");
        List<Integer> colors3 = new ArrayList<>();
        colors3.add(R.color.groupColor3);
        groups.add(new Group("Goals", "groupId3", tasks3, colors3));

        // Group 4
        List<String> tasks4 = new ArrayList<>();
        tasks4.add("Task7");
        tasks4.add("Task8");
        List<Integer> colors4 = new ArrayList<>();
        colors4.add(R.color.groupColor4);
        groups.add(new Group("Miscellaneous", "groupId4", tasks4, colors4));

        // Group 5
        List<String> tasks5 = new ArrayList<>();
        tasks5.add("Task9");
        tasks5.add("Task10");
        List<Integer> colors5 = new ArrayList<>();
        colors5.add(R.color.groupColor5);
        groups.add(new Group("Private", "groupId5", tasks5, colors5));

        return groups;
    }

    private void scheduleReminder(Task task) {
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        //intent.putExtra("task_description", task.getDescription());
        // Assuming you have a valid 'intent' object
        String taskDescription = task.getDescription();
        intent.putExtra("task_description", taskDescription);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            long reminderTime = calendar.getTimeInMillis();
            alarmManager.set(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent);
        }
    }

    public static class ReminderBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String taskDescription = intent.getStringExtra("task_description");
            showNotification(context, taskDescription);
        }

        private void showNotification(Context context, String taskDescription) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Reminder Channel";
                String description = "Channel for task reminders";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;

                NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
                channel.setDescription(description);

                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.notifications_24)
                    .setContentTitle("Task Reminder")
                    .setContentText(taskDescription)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            if (notificationManager != null) {
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListener) {
            ((OnDialogCloseListener) activity).onDialogClose(dialog);
        }
    }
}
