package com.example.tikz.personalassistantuk.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.BaseColumns;

public class AlarmReminderContract {

    private AlarmReminderContract() {}

    public static final Uri uriAlarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    public static final Uri uriNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    public static final Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);


    /**>>EventActivity>>*/
    public static final String CONTENT_AUTHORITY = "com.example.tikz.personalassistantuk.providerAlarm";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_VEHICLE = "event";

    public static final class AlarmReminderEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VEHICLE);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEHICLE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VEHICLE;

        public final static String TABLE_NAME = "event";

        public final static String _ID = BaseColumns._ID;
    }
    /**<<EventActivity<<*/

    /**RegistrationActivity>>*/
    public static final String CONTENT_AUTHORITY_REG = "com.example.tikz.personalassistantuk.providerReg";

    public static final Uri BASE_CONTENT_URI_REG = Uri.parse("content://" + CONTENT_AUTHORITY_REG);

    public static final String PATH_VEHICLE_REG = "mahasiswa";

    public static class RegistrationEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI_REG, PATH_VEHICLE_REG);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY_REG + "/" + PATH_VEHICLE_REG;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY_REG + "/" + PATH_VEHICLE_REG;

        public final static String TABLE_NAME = "mahasiswa";

    }
    /**RegistrationActivity<<*/

    /**ScheduleActivity>>*/
    public static final String CONTENT_AUTHORITY_SCH = "com.example.tikz.personalassistantuk.providerSchedule";

    public static final Uri BASE_CONTENT_URI_SCH = Uri.parse("content://" + CONTENT_AUTHORITY_SCH);

    public static final String PATH_VEHICLE_SCH = MahasiswaDBOpenHelper.TABLE_SCHEDULE;


    public static final class ScheduleEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI_SCH, PATH_VEHICLE_SCH);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY_SCH + "/" + PATH_VEHICLE_SCH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY_SCH + "/" + PATH_VEHICLE_SCH;

        public final static String TABLE_NAME = MahasiswaDBOpenHelper.TABLE_SCHEDULE;
    }
    /**ScheduleActivity<<*/

    /**Assigntment and Exam>>>*/
    public static final String CONTENT_AUTHORITY_ASS = "com.example.tikz.personalassistantuk.providerASSEXAM";

    public static final Uri BASE_CONTENT_URI_ASS = Uri.parse("content://" + CONTENT_AUTHORITY_ASS);

    public static final String PATH_VEHICLE_ASS = MahasiswaDBOpenHelper.TABLE_NAME_ASSEXAM;

    //Exam
    public static final String CONTENT_AUTHORITY_EXAM = "com.example.tikz.personalassistantuk.providerExam";

    public static final Uri BASE_CONTENT_URI_EXAM = Uri.parse("content://" + CONTENT_AUTHORITY_EXAM);

    public static final String PATH_VEHICLE_EXAM = MahasiswaDBOpenHelper.TABLE_NAME_ASSEXAM;


    public static final class AssignmentEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI_ASS, PATH_VEHICLE_ASS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY_ASS + "/" + PATH_VEHICLE_ASS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY_ASS + "/" + PATH_VEHICLE_ASS;

        public final static String TABLE_NAME = MahasiswaDBOpenHelper.TABLE_NAME_ASSEXAM;

        //public final static String _ID = BaseColumns._ID;
    }
    public static final class ExamReminderEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI_EXAM, PATH_VEHICLE_EXAM);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY_EXAM + "/" + PATH_VEHICLE_EXAM;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY_EXAM + "/" + PATH_VEHICLE_EXAM;

        public final static String TABLE_NAME = MahasiswaDBOpenHelper.TABLE_NAME_ASSEXAM;
    }

    /**Assigntment and Exam<<*/

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
}
