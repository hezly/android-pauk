package com.example.tikz.personalassistantuk.activity;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tikz.personalassistantuk.R;
import com.example.tikz.personalassistantuk.adapter.ScheduleCursorAdapter;
import com.example.tikz.personalassistantuk.data.AlarmReminderContract;
import com.example.tikz.personalassistantuk.data.MahasiswaDBOpenHelper;
import com.example.tikz.personalassistantuk.reminder.ScheduleAlarmScheduler;

public class ScheduleActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private FloatingActionButton btnAddSchedule;
    private Toolbar mToolbar;
    ScheduleCursorAdapter mCursorAdapter;
    MahasiswaDBOpenHelper mahasiswaDBOpenHelper = new MahasiswaDBOpenHelper(this);
    ListView scheduleListView;
    ProgressDialog progressDialog;
    TextView scheduleText,noScheduleText;

    private String scheduleTitle = "";

    private static final int VEHICLE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //mToolbar.setTitle(R.string.title_activity_schedule);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_schedule);

        scheduleListView = (ListView) findViewById(R.id.scheduleList);
        scheduleText = (TextView) findViewById(R.id.scheduleText);
        View noScheduleView = findViewById(R.id.empty_view_schedule);
        scheduleListView.setEmptyView(noScheduleView);

        mCursorAdapter = new ScheduleCursorAdapter(this, null);
        scheduleListView.setAdapter(mCursorAdapter);

        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(ScheduleActivity.this, Popup_Schedule.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.ScheduleEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentVehicleUri);

                startActivity(intent);

            }
        });

        scheduleListView.setLongClickable(true);

        scheduleListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //deleteSelectedItemList();

                String delete = "delete";

                final Intent intent = new Intent(ScheduleActivity.this, Popup_Schedule.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.ScheduleEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentVehicleUri);
                intent.putExtra("DELETE_DATA",delete);

                AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
                builder.setMessage("Are you sure to delete this?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });


        btnAddSchedule = (FloatingActionButton) findViewById(R.id.tambah_schedule);
        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReminderTitle();
                //tambah_schedule();
            }
        });

        getSupportLoaderManager().initLoader(VEHICLE_LOADER, null, this);
    }

    private void deleteSelectedItemList(){



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you delete this?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void tambah_schedule(){
        Intent intent = new Intent(this, Popup_Schedule.class);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MahasiswaDBOpenHelper.COL_SUBJECT_ID,
                MahasiswaDBOpenHelper.COL_SUBJECT_NAME,
                MahasiswaDBOpenHelper.COL_SUBJECT_DAY,
                MahasiswaDBOpenHelper.COL_SUBJECT_PARALLEL,
                MahasiswaDBOpenHelper.COL_SUBJECT_ROOM,
                MahasiswaDBOpenHelper.COL_SUBJECT_TEACHER,
                MahasiswaDBOpenHelper.COL_SUBJECT_TIME,
                MahasiswaDBOpenHelper.COL_SUBJECT_TIMER,
                MahasiswaDBOpenHelper.COL_SUBJECT_ACTIVE
        };

        return new CursorLoader(this,   // Parent activity context
                AlarmReminderContract.ScheduleEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
        if (cursor.getCount() > 0){
            scheduleText.setVisibility(View.VISIBLE);
        }else{
            scheduleText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

    public void addReminderTitle(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set ScheduleActivity Title");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().isEmpty()){
                    return;
                }

                scheduleTitle = input.getText().toString();
                ContentValues values = new ContentValues();

                values.put(MahasiswaDBOpenHelper.COL_SUBJECT_NAME, scheduleTitle);

                Uri newUri = getContentResolver().insert(AlarmReminderContract.ScheduleEntry.CONTENT_URI, values);

                restartLoader();


                if (newUri == null) {
                    Toast.makeText(getApplicationContext(), "Setting Reminder Title failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Title set successfully", Toast.LENGTH_SHORT).show();
                }

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

    public void restartLoader(){
        getSupportLoaderManager().restartLoader(VEHICLE_LOADER, null, this);
    }
}
