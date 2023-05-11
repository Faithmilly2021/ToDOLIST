package com.faith.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast  extends BroadcastReceiver {
    private Context context;

    @Override
    public  void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Reminder")
                .setSmallIcon(R.drawable.notifications_24)
                .setContentTitle("Reminder")
                .setContentTitle("Your task is due today!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());
    }

public void  scheduleReminder(long dueDateTimeInMillis) {
   long currentTimeInMillis = System.currentTimeMillis();
   long timeDiffInMillis = dueDateTimeInMillis - currentTimeInMillis;

   Intent intent = new Intent(context, ReminderBroadcast.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeDiffInMillis, pendingIntent);
}

}
