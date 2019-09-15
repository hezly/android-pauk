package com.example.tikz.personalassistantuk.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;

import com.example.tikz.personalassistantuk.reminder.service.AssExAlarmService;

public class AssExAlarmScheduler {

    /**
     * ScheduleActivity a reminder alarm at the specified time for the given task.
     *
     * @param context Local application or activity context

     * @param reminderTask Uri referencing the task in the content provider
     */

    /**AssExam*/
    //Set alarm for Assignment and Exam
    public void setAlarmAssExam(Context context, long alarmTime, Uri reminderTask) {
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation =
                AssExAlarmService.getReminderPendingIntent(context, reminderTask);


        if (Build.VERSION.SDK_INT >= 23) {

            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, operation);

        } else if (Build.VERSION.SDK_INT >= 19) {

            manager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, operation);

        } else {

            manager.set(AlarmManager.RTC_WAKEUP, alarmTime, operation);

        }
    }
    //set repeat alarm for Assignment and Exam
    public void setRepeatAssExam(Context context, long alarmTime, Uri reminderTask, long repeatTime) {
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation =
                AssExAlarmService.getReminderPendingIntent(context, reminderTask);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, repeatTime, operation);
    }

    //cancel alarm for Assignment and Exam
    public void cancelAlarmAssEx(Context context, Uri reminderTask) {
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation =
                AssExAlarmService.getReminderPendingIntent(context, reminderTask);

        manager.cancel(operation);
    }

}