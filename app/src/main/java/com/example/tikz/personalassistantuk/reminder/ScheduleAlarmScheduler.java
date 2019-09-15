package com.example.tikz.personalassistantuk.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;

import com.example.tikz.personalassistantuk.reminder.service.ScheduleAlarmService;

public class ScheduleAlarmScheduler {

    /**
     * ScheduleActivity a reminder alarm at the specified time for the given task.
     *
     * @param context Local application or activity context

     * @param reminderTask Uri referencing the task in the content provider
     */

    /**ScheduleActivity*/
    //set repeat alarm for ScheduleActivity
    public void setRepeatAlarm(Context context, long alarmTime, Uri reminderTask, long repeatTime) {
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation =
                ScheduleAlarmService.getReminderPendingIntent(context, reminderTask);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, repeatTime, operation);
    }

    //cancel alarm for ScheduleActivity
    public void cancelAlarm(Context context, Uri reminderTask) {
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation =
                ScheduleAlarmService.getReminderPendingIntent(context, reminderTask);

        manager.cancel(operation);
    }
}