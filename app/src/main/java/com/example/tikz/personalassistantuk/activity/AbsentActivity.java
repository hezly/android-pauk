package com.example.tikz.personalassistantuk.activity;

import android.app.LoaderManager;

import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.tikz.personalassistantuk.Constant;
import com.example.tikz.personalassistantuk.R;
import com.example.tikz.personalassistantuk.data.AbsentContentProvider;
import com.example.tikz.personalassistantuk.data.AbsentDBOpenHelper;
import com.example.tikz.personalassistantuk.data.MahasiswaDBAccess;

public class AbsentActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private SimpleCursorAdapter dataAdapter;
    ListView absentListView;
    TextView absenText, nimText;
    View emptyView;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_absen);

        nimText = (TextView) findViewById(R.id.nimText);

        String nim = Constant.getNIM();
        if (nim==null){
            String text = "Click here to input NIM!";
            nimText.setText(text);
        }else {
            String text = "NIM : "+nim;
            nimText.setText(text);
        }

        displayListView();

        nimText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNimUser();
            }
        });
    }

    private void setNimUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set your NIM :");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().isEmpty()){
                    return;
                }

                String nim = input.getText().toString();
                //Get instance to database
                MahasiswaDBAccess dbAccess = MahasiswaDBAccess.getInstance(getApplicationContext());
                dbAccess.openDB2();//open database
                dbAccess.insertNIM(nim);//Insert string nim to database
                dbAccess.getDataMahasiswa();//Get data NIM from database to Constant class
                dbAccess.closeDB2();//close database
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Starts a new or restarts an existing Loader in this manager
        getLoaderManager().restartLoader(0, null, this);
    }

    // This is called when a new Loader needs to be created.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                AbsentDBOpenHelper.ID,
                AbsentDBOpenHelper.COL_SUBJECT_NAME,
                AbsentDBOpenHelper.COL_ABSENT_DATE,
                AbsentDBOpenHelper.COL_TYPE};
        CursorLoader cursorLoader= new CursorLoader(this,
                AbsentContentProvider.CONTENT_URI, projection,
                AbsentDBOpenHelper.COL_STUDENT_NIM+" = "+ Constant.getNIM(),
                null, AbsentDBOpenHelper.COL_SUBJECT_NAME);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        dataAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        dataAdapter.swapCursor(null);
    }

    //Actionbar menu start
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_absent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the refresh
            case R.id.action_refresh:
                onResume();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Actionbar menu end

    private void displayListView() {

        // The desired columns to be bound
        String[] projection = new String[]{
                AbsentDBOpenHelper.COL_SUBJECT_NAME,
                AbsentDBOpenHelper.COL_ABSENT_DATE,
                AbsentDBOpenHelper.COL_TYPE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.absent_subject,
                R.id.absent_date_time,
                R.id.absent_type,
        };

        // create an adapter from the SimpleCursorAdapter
        dataAdapter = new SimpleCursorAdapter(
                this,
                R.layout.absent_items,
                null,
                projection,
                to,
                0);

        // get reference to the ListView
        absentListView = (ListView) findViewById(R.id.listAbsent);
        absenText = (TextView) findViewById(R.id.reminderText);
        emptyView = findViewById(R.id.empty_view_absent);
        absentListView.setEmptyView(emptyView);

        // Assign adapter to ListView
        absentListView.setAdapter(dataAdapter);

        //Ensures a loader is initialized and active.
        getLoaderManager().initLoader(0, null, this);

        //Change subject code to subject name
    }
}
