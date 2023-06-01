package com.faith.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        // Extract the task details from the intent extras
        String taskDescription = intent.getStringExtra("taskDescription");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Reminder")
                .setSmallIcon(R.drawable.notifications_24)
                .setContentTitle("Reminder")
                .setContentText("Your task \"" + taskDescription + "\" is due today!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());
    }

    public void scheduleReminder(Context context, long dueDateTimeInMillis, String taskDescription) {
        this.context = context;

        long currentTimeInMillis = System.currentTimeMillis();
        long timeDiffInMillis = dueDateTimeInMillis - currentTimeInMillis;

        Intent intent = new Intent(context, ReminderBroadcast.class);
        intent.putExtra("taskDescription", taskDescription); // Pass the task description as an extra
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeDiffInMillis, pendingIntent);
    }
}

