package com.example.tikz.personalassistantuk.reminder.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.tikz.personalassistantuk.activity.AlarmActivity;
import com.example.tikz.personalassistantuk.activity.Popup_Event;
import com.example.tikz.personalassistantuk.R;
import com.example.tikz.personalassistantuk.data.AlarmReminderContract;
import com.example.tikz.personalassistantuk.data.MahasiswaDBOpenHelper;

import java.util.Random;


public class EventAlarmService extends IntentService {
    private static final String TAG = EventAlarmService.class.getSimpleName();

    private static final int NOTIFICATION_ID = new Random().nextInt(50) * new Random().nextInt(10);
    //This is a deep link intent, and needs the task stack
    public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {
        Intent action = new Intent(context, EventAlarmService.class);
        action.setData(uri);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public EventAlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri uri = intent.getData();

        //Display a notification to view the task details
        Intent action = new Intent(this, Popup_Event.class);
        action.setData(uri);
        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //Grab the task description
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String description = "", title = "", time = "", info = "";
        try {
            if (cursor != null && cursor.moveToFirst()) {
                description = AlarmReminderContract.getColumnString(cursor, MahasiswaDBOpenHelper.COL_EVENT_NAME) +" "+
                        AlarmReminderContract.getColumnString(cursor, MahasiswaDBOpenHelper.COL_EVENT_TIME);

                title = "Don't late to go to "
                        + AlarmReminderContract.getColumnString(cursor, MahasiswaDBOpenHelper.COL_EVENT_NAME);
                info = AlarmReminderContract.getColumnString(cursor, MahasiswaDBOpenHelper.COL_EVENT_INFO);
                time = AlarmReminderContract.getColumnString(cursor, MahasiswaDBOpenHelper.COL_EVENT_DATE) + " "
                        + AlarmReminderContract.getColumnString(cursor, MahasiswaDBOpenHelper.COL_EVENT_TIME);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Notification note = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.reminder_title_event))
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentIntent(operation)
                //.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSound(Settings.System.DEFAULT_ALARM_ALERT_URI)
                .setAutoCancel(true)
                .build();

        manager.notify(NOTIFICATION_ID, note);

        Intent secIntent = new Intent(getBaseContext(), AlarmActivity.class);
        secIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        secIntent.putExtra("title", title);
        secIntent.putExtra("info", info);
        secIntent.putExtra("time", time);
        getBaseContext().startActivity(secIntent);
    }
}