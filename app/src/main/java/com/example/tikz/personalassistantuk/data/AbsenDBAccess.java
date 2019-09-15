package com.example.tikz.personalassistantuk.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tikz.personalassistantuk.Constant;

import java.util.ArrayList;
import java.util.List;

public class AbsenDBAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static AbsenDBAccess instance;
    private Cursor c;

    //Private constructor so that object creation from outside the class is avoided
    private AbsenDBAccess(Context context){
        this.openHelper=new AbsentDBOpenHelper(context);
    }

    //to return the single instance of database
    public static AbsenDBAccess getInstance(Context context){
        if (instance==null){
            instance=new AbsenDBAccess(context);
        }
        return instance;
    }

    //to open the database
    public void openDB(){
        this.db=openHelper.getWritableDatabase();
    }

    public void getDB(){
        this.db=openHelper.getReadableDatabase();
    }

    //closing the database connection
    public void closeDB(){
        if (db!=null){
            this.db.close();
        }
    }

    //Get Subject Code
    public List<String> getSubjectCode(){
        List<String> list = new ArrayList<>();
        this.db=openHelper.getReadableDatabase();
        //Constant constant = new Constant(null,null,null);
        try {
            String query = "SELECT * FROM " + AbsentDBOpenHelper.TABLE_STUDENT + " WHERE "
                    + AbsentDBOpenHelper.COL_STUDENT_NIM + " ='" + Constant.getNIM() + "'";
            c = db.rawQuery(query,null);
            if (c.getCount()==0){
                list = null;
            }else {
                if (c.moveToFirst()){
                    do {
                        list.add(c.getString(c.getColumnIndex(AbsentDBOpenHelper.COL_SUBJECT_CODE)));
                    }while (c.moveToNext());
                    /*while (c.moveToNext()){
                        String subject_code = c.getString(c.getColumnIndex(AbsenDBOpenHelper.COL_SUBJECT_CODE));
                        list.add(subject_code);
                    }*/
                }
            }
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public Cursor getAbsenData(){
        this.db=openHelper.getReadableDatabase();
        String query = "SELECT * FROM " + AbsentDBOpenHelper.TABLE_STUDENT;
        c = db.rawQuery(query, null);

        return c;
    }
}
