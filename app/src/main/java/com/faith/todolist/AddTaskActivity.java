package com.faith.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.faith.todolist.Adapter.TaskAdapter;
import com.faith.todolist.Model.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskDescriptionEditText;
    private Button addButton;
    private Button dateButton;
    private Button timeButton;
    private TaskAdapter adapter;

    private Calendar calendar;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize views
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        addButton = findViewById(R.id.saveTaskButton);
        dateButton = findViewById(R.id.calendarButton);
        timeButton = findViewById(R.id.timeButton);

        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("hh:mm a", Locale.US);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = taskDescriptionEditText.getText().toString().trim();
                if (!description.isEmpty()) {
                    Task newTask = new Task(description);

                    Intent intent = new Intent(AddTaskActivity.this, DashboardActivity.class);
                    intent.putExtra("newTask", newTask);
                    startActivity(intent);
                    finish();
                }
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String selectedDate = dateFormatter.format(calendar.getTime());
                dateButton.setText(selectedDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                String selectedTime = timeFormatter.format(calendar.getTime());
                timeButton.setText(selectedTime);
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

        timePickerDialog.show();
    }
}
