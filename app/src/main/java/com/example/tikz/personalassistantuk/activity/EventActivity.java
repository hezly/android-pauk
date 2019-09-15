package com.example.tikz.personalassistantuk.activity;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tikz.personalassistantuk.R;
import com.example.tikz.personalassistantuk.adapter.EventCursorAdapter;
import com.example.tikz.personalassistantuk.data.MahasiswaDBOpenHelper;
import com.example.tikz.personalassistantuk.data.AlarmReminderContract;

public class EventActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private FloatingActionButton btnAddEvent;
    private Toolbar mToolbar;
    EventCursorAdapter mCursorAdapter;
    MahasiswaDBOpenHelper mahasiswaDBOpenHelper = new MahasiswaDBOpenHelper(this);
    ListView eventListView;
    ProgressDialog progressDialog;
    TextView eventText,noEventText;

    private String eventTitle = "";

    private static final int VEHICLE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //mToolbar.setTitle(R.string.title_activity_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_event);

        eventListView = (ListView) findViewById(R.id.eventList);
        eventText = (TextView) findViewById(R.id.reminderText);
        View noEventView = findViewById(R.id.empty_view_event);

        View emptyView = noEventView;
        eventListView.setEmptyView(emptyView);

        mCursorAdapter = new EventCursorAdapter(this, null);
        eventListView.setAdapter(mCursorAdapter);

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(EventActivity.this, Popup_Event.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.AlarmReminderEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentVehicleUri);

                startActivity(intent);

            }
        });

        eventListView.setLongClickable(true);

        eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //deleteSelectedItemList();

                String delete = "delete";

                final Intent intent = new Intent(EventActivity.this, Popup_Event.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.AlarmReminderEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentVehicleUri);
                intent.putExtra("DELETE_DATA",delete);

                AlertDialog.Builder builder = new AlertDialog.Builder(EventActivity.this);
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

        btnAddEvent = (FloatingActionButton) findViewById(R.id.tambah_event);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReminderTitle();
                //tambah_event();
            }
        });

        getSupportLoaderManager().initLoader(VEHICLE_LOADER, null, this);
    }
    public void tambah_event(){
        Intent intent = new Intent(this, Popup_Event.class);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                MahasiswaDBOpenHelper.COL_EVENT_ID,
                MahasiswaDBOpenHelper.COL_EVENT_NAME,
                MahasiswaDBOpenHelper.COL_EVENT_INFO,
                MahasiswaDBOpenHelper.COL_EVENT_DATE,
                MahasiswaDBOpenHelper.COL_EVENT_TIME,
                MahasiswaDBOpenHelper.COL_EVENT_TIMER,
                MahasiswaDBOpenHelper.COL_EVENT_ACTIVE
        };

        return new CursorLoader(this,   // Parent activity context
                AlarmReminderContract.AlarmReminderEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
        if (cursor.getCount() > 0){
            eventText.setVisibility(View.VISIBLE);
        }else{
            eventText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }

    public void addReminderTitle(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set EventActivity Title");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().isEmpty()){
                    return;
                }

                eventTitle = input.getText().toString();
                ContentValues values = new ContentValues();

                values.put(MahasiswaDBOpenHelper.COL_EVENT_NAME, eventTitle);

                Uri newUri = getContentResolver().insert(AlarmReminderContract.AlarmReminderEntry.CONTENT_URI, values);

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
