package com.example.tikz.personalassistantuk.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class AbsentDBOpenHelper extends SQLiteAssetHelper {
    public static final String DATABASE_NAME = "absent.db";

    public static final String ID = "_id";
    public static final String TABLE_STUDENT = "student_class_absent";
    public static final String COL_ABSENT_DATE = "absent_date";
    public static final String COL_SUBJECT_CODE = "code";
    public static final String COL_REG_NUMB = "registration_number";
    public static final String COL_STU_REG_NUMB = "student_reg_number";
    public static final String COL_STUDENT_NIM = "student_nim";
    public static final String COL_TYPE = "absent_type";
    public static final String COL_SUBJECT_NAME = "name_eng";
    public static final String COL_SCHEDULE_ID = "schedule_id";
    public static final String COL_SEMESTER_ID = "semester_id";

    public static final String TABLE_SCHEDULE = "ScheduleActivity";
    public static final String COL_ID = "id";

    public static final String TABLE_SUBJECT = "subject";
    //public static final String ROW_SUBJECT_CODE = "subject_code";

    private static final int DATABASE_VERSION = 1;

    public AbsentDBOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
