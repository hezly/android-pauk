package com.example.tikz.personalassistantuk.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tikz.personalassistantuk.Constant;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

public class MahasiswaDBAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private  static MahasiswaDBAccess instance;
    private Cursor c;


    //Private constructor so that object creation from outside the class is avoided
    private MahasiswaDBAccess(Context context){
        this.openHelper=new MahasiswaDBOpenHelper(context);
    }

    //to return the single instance of database
    public static MahasiswaDBAccess getInstance(Context context){
        if (instance==null){
            instance=new MahasiswaDBAccess(context);
        }
        return instance;
    }

    //to open the database

    public void openDB2(){
        this.db=openHelper.getWritableDatabase();
    }

    //closing the database connection
    public void closeDB2(){
        if (db!=null){
            this.db.close();
        }
    }

    //method to query and return result from database
    public void insertData(String fname, String lname, String nim, String pass){
        db = this.openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MahasiswaDBOpenHelper.COL_FNAME, fname);
        contentValues.put(MahasiswaDBOpenHelper.COL_LNAME, lname);
        contentValues.put(MahasiswaDBOpenHelper.COL_NIM, nim);
        contentValues.put(MahasiswaDBOpenHelper.COL_PASS, pass);
        db.insert(MahasiswaDBOpenHelper.TABLE_NAME, null, contentValues);
        db.close();
    }

    public void insertNIM(String nim){
        db = this.openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MahasiswaDBOpenHelper.COL_NIM, nim);
        db.insert(MahasiswaDBOpenHelper.TABLE_NAME, null, contentValues);
        db.close();
    }

    public int checkMahasiswa(){
        db = this.openHelper.getReadableDatabase();
        int count = 0;
        String query = "SELECT * FROM mahasiswa";
        c = db.rawQuery(query, null);
        count = count + c.getCount();
        db.close();
        return count;
    }

    public void deleteMahasiswa(){
        openHelper.getWritableDatabase();
        /*
        String query = "DELETE FROM "+ MahasiswaDBOpenHelper.TABLE_NAME;
        c = db.rawQuery(query, null);
        */
        db.delete(MahasiswaDBOpenHelper.TABLE_NAME,null,null);
        db.close();
    }

    public void getDataMahasiswa(){

        db = this.openHelper.getWritableDatabase();
        String query = "SELECT * FROM " + MahasiswaDBOpenHelper.TABLE_NAME;
        c = db.rawQuery(query, null);
        String fname,lname,nim,pass;

        while (c.moveToNext()){
            fname = c.getString(c.getColumnIndex(MahasiswaDBOpenHelper.COL_FNAME));
            lname = c.getString(c.getColumnIndex(MahasiswaDBOpenHelper.COL_LNAME));
            nim = c.getString(c.getColumnIndex(MahasiswaDBOpenHelper.COL_NIM));
            pass = c.getString(c.getColumnIndex(MahasiswaDBOpenHelper.COL_PASS));
            Constant constant = new Constant(fname,lname,nim,pass);
        }
    }

    public List<String> getSubjectName(){

        List<String> subjects = new ArrayList<String>();

        String query = "SELECT * FROM " + MahasiswaDBOpenHelper.TABLE_SCHEDULE;

        db = openHelper.getReadableDatabase();

        c = db.rawQuery(query, null);

        if (c.moveToFirst()){
            do {
                subjects.add(c.getString(c.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_NAME)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        return subjects;
    }
}
