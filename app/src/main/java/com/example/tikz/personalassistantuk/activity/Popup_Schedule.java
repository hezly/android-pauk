package com.example.tikz.personalassistantuk.activity;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tikz.personalassistantuk.R;
import com.example.tikz.personalassistantuk.data.AlarmReminderContract;
import com.example.tikz.personalassistantuk.data.MahasiswaDBOpenHelper;
import com.example.tikz.personalassistantuk.reminder.ScheduleAlarmScheduler;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class Popup_Schedule extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EXISTING_VEHICLE_LOADER = 0;

    private Toolbar mToolbar;
    private EditText etTitle, etSubParallel, etSubRoom, etSubTeacher, etSubCode;
    private TextView txtDay, txtTime, txtTimer, txtSubCode;
    private FloatingActionButton mFAB1;
    private FloatingActionButton mFAB2;
    private Calendar mCalendar,mCalendar2;
    private int mYear, mMonth, mHour, mMinute, mDay, mDayName, days, days2=0;
    private long timer;
    private String mTitle, mSubCode, mParallel, mRoom, mTeacher, mTime, mTimer, mDays, mDate;
    private String mActive;

    private Uri mCurrentReminderUri = null, mCurrentReminderUri2 = null;
    private boolean mVehicleHasChanged = false;

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mVehicleHasChanged = true;
            return false;
        }
    };

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_schedule);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        mCurrentReminderUri = intent.getData();

        if (mCurrentReminderUri == null) {

            setTitle(getString(R.string.editor_title_popup_new_schedule));

            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a reminder that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {

            setTitle(getString(R.string.editor_title_popup_edit_schedule));


            getLoaderManager().initLoader(EXISTING_VEHICLE_LOADER, null, this);
        }

        // Initialize Views
        initDeleteView();

        etTitle = (EditText) findViewById(R.id.subject_title);

        etSubParallel = (EditText) findViewById(R.id.subject_parallel);
        etSubRoom = (EditText) findViewById(R.id.subject_room);
        etSubTeacher = (EditText) findViewById(R.id.subject_teacher);
        etSubCode = (EditText) findViewById(R.id.set_code_schedule);
        txtDay = (TextView) findViewById(R.id.set_days_schedule);
        txtTime = (TextView) findViewById(R.id.set_time_schedule);
        txtTimer = (TextView) findViewById(R.id.set_timer_schedule);
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred_sub);
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred_sub2);

        // Initialize default values
        mActive = "true";
        mTimer = "None";

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        //mYear = mCalendar.get(Calendar.YEAR);
        //mMonth = mCalendar.get(Calendar.MONTH) + 1;
        //mDay = mCalendar.get(Calendar.DATE);

        mTime = mHour + ":" + mMinute;

        etTitle.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                etTitle.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etSubParallel.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mParallel = s.toString().trim();
                etSubParallel.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etSubRoom.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRoom = s.toString().trim();
                etSubRoom.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etSubTeacher.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTeacher = s.toString().trim();
                etSubTeacher.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Setup TextViews using reminder values
        etTitle.setText(mTitle);
        etSubCode.setText(mSubCode);
        etSubParallel.setText(mParallel);
        etSubTeacher.setText(mTeacher);
        etSubRoom.setText(mRoom);
        txtTime.setText(mTime);
        txtTimer.setText(mTimer);
        txtDay.setText(mDays);

        // To save state on device rotation
        if (savedInstanceState != null) {
            String savedTitle = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_SUBJECT_NAME);
            etTitle.setText(savedTitle);
            mTitle = savedTitle;

            String savedId = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_SUB_ID);
            etSubCode.setText(savedId);
            mSubCode = savedId;

            String savedParallel = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_SUBJECT_PARALLEL);
            etSubParallel.setText(savedParallel);
            mParallel = savedParallel;

            String savedTeacher = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_SUBJECT_TEACHER);
            etSubTeacher.setText(savedTeacher);
            mTeacher = savedTeacher;

            String saveRoom = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_SUBJECT_ROOM);
            etSubRoom.setText(saveRoom);
            mRoom = saveRoom;

            String saveTime = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_SUBJECT_TIME);
            txtTime.setText(saveTime);
            mTime = saveTime;

            String saveTimer = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_SUBJECT_TIMER);
            txtTimer.setText(saveTimer);
            mTimer = saveTimer;

            String saveDay = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_SUBJECT_DAY);
            txtDay.setText(saveDay);
            mDays = saveDay;

            mActive = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_EVENT_ACTIVE);
        }

        // Setup up active buttons
        if (mActive.equals("false")) {
            mFAB1.setVisibility(View.VISIBLE);
            mFAB2.setVisibility(View.GONE);

        } else if (mActive.equals("true")) {
            mFAB1.setVisibility(View.GONE);
            mFAB2.setVisibility(View.VISIBLE);
        }
    }

    private void initDeleteView() {
        if (getIntent().getStringExtra("DELETE_DATA")!=null){
            String delete = getIntent().getStringExtra("DELETE_DATA");
            if (delete.equals("delete")){
                deleteReminder();
            }
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(MahasiswaDBOpenHelper.COL_SUB_ID, etSubCode.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_SUBJECT_NAME, etTitle.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_SUBJECT_PARALLEL, etSubParallel.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_SUBJECT_TIME, txtTime.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_SUBJECT_TIMER, txtTimer.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_SUBJECT_DAY, txtDay.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_SUBJECT_ROOM, etSubRoom.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_SUBJECT_TEACHER, etSubTeacher.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_SUBJECT_ACTIVE, mActive);
    }

    // On clicking the active button
    public void selectFab1(View v) {
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred_sub);
        mFAB1.setVisibility(View.GONE);
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred_sub2);
        mFAB2.setVisibility(View.VISIBLE);
        mActive = "true";
    }

    // On clicking the inactive button
    public void selectFab2(View v) {
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred_sub2);
        mFAB2.setVisibility(View.GONE);
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred_sub);
        mFAB1.setVisibility(View.VISIBLE);
        mActive = "false";
    }

    // On clicking Time picker
    public void selectTimeSchedule(View v){
        if(mCurrentReminderUri == null){
            Toast.makeText(this, "click again on the reminder list to set time alarm", Toast.LENGTH_LONG).show();
        }
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    public void selectSubCode(View v){
        final String[] items = new String[11];

        items[0] = "None";
        items[1] = "1";
        items[2] = "2";
        items[3] = "3";
        items[4] = "4";
        items[5] = "5";
        items[6] = "6";
        items[7] = "7";
        items[8] = "8";
        items[9] = "9";
        items[10] = "10";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Timer");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mTimer = items[item];
                txtSubCode.setText(mTimer);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // On clicking repeat type button
    public void selectTimerSchedule(View v){
        final String[] items = new String[11];

        items[0] = "None";
        items[1] = "1";
        items[2] = "2";
        items[3] = "5";
        items[4] = "10";
        items[5] = "15";
        items[6] = "20";
        items[7] = "25";
        items[8] = "35";
        items[9] = "50";
        items[10] = "60";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Timer");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mTimer = items[item];
                txtTimer.setText(mTimer);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    // Obtain time from time picker
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }
        txtTime.setText(mTime);
    }

    public void selectDay(View v){
        final String[] items = new String[8];

        items[0] = "Sunday";
        items[1] = "Monday";
        items[2] = "Tuesday";
        items[3] = "Wednesday";
        items[4] = "Thursday";
        items[5] = "Friday";
        items[6] = "Monday-Wednesday";
        items[7] = "Tuesday-Thursday";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Day(s)");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mDays = items[item];
                txtDay.setText(mDays);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_add_reminder.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new reminder, hide the "Delete" menu item.
        if (mCurrentReminderUri == null) {
            MenuItem menuItem = menu.findItem(R.id.discard_reminder);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.save_reminder:


                if (etTitle.getText().toString().length() == 0){
                    etTitle.setError("EventActivity title cannot be blank!");
                }

                else {
                    saveReminder();
                    finish();
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.discard_reminder:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the reminder hasn't changed, continue with navigating up to parent activity
                // which is the {@link MainActivity}.
                if (!mVehicleHasChanged) {
                    NavUtils.navigateUpFromSameTask(Popup_Schedule.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(Popup_Schedule.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the reminder.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the reminder.
                deleteReminder();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the reminder.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteReminder() {
        // Only perform the delete if this is an existing reminder.
        if (mCurrentReminderUri != null) {
            // Call the ContentResolver to delete the reminder at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentreminderUri
            // content URI already identifies the reminder that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentReminderUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                //Cancel notification
                new ScheduleAlarmScheduler().cancelAlarm(getApplicationContext(), mCurrentReminderUri);
                //Cancel alarm
                new ScheduleAlarmScheduler().cancelAlarm(getApplicationContext(), mCurrentReminderUri2);
                Toast.makeText(this, getString(R.string.editor_delete_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }



    // On clicking the save button
    public void saveReminder(){

     /*   if (mCurrentReminderUri == null ) {
            // Since no fields were modified, we can return early without creating a new reminder.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }
*/

        ContentValues values = new ContentValues();
        values.put(MahasiswaDBOpenHelper.COL_SUB_ID, etSubCode.getText().toString());
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_NAME, mTitle);
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_PARALLEL, etSubParallel.getText().toString());
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_TIME, txtTime.getText().toString());
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_TIMER, txtTimer.getText().toString());
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_DAY, txtDay.getText().toString());
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_ROOM, etSubRoom.getText().toString());
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_TEACHER, etSubTeacher.getText().toString());
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_ACTIVE, mActive);
        /*
        values.put(MahasiswaDBOpenHelper.COL_SUB_ID, mSubCode);
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_NAME, mTitle);
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_PARALLEL, mParallel);
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_TIME, mTime);
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_TIMER, mTimer);
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_DAY, mDays);
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_ROOM, mRoom);
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_TEACHER, mTeacher);
        values.put(MahasiswaDBOpenHelper.COL_SUBJECT_ACTIVE, mActive);
        */


        // Check Day
        if (txtDay.getText().equals("Sunday")){
            days = Calendar.SUNDAY;
        } else if (txtDay.getText().equals("Monday")){
            days = Calendar.MONDAY;
        } else if (txtDay.getText().equals("Tuesday")){
            days = Calendar.TUESDAY;
        } else if (txtDay.getText().equals("Wednesday")){
            days = Calendar.WEDNESDAY;
        } else if (txtDay.getText().equals("Thursday")){
            days = Calendar.THURSDAY;
        } else if (txtDay.getText().equals("Friday")){
            days = Calendar.FRIDAY;
        } else if (txtDay.getText().equals("Monday-Wednesday")){
            days = Calendar.MONDAY;
            days2 = Calendar.WEDNESDAY;
        } else if (txtDay.getText().equals("Tuesday-Thursday")){
            days = Calendar.TUESDAY;
            days2 = Calendar.THURSDAY;
        }

        // Set up calender for creating the notification
        mCalendar.set(Calendar.DAY_OF_WEEK, days);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);



        // Check timer
        if (mTimer.equals("None")){
            timer = 0;
        }
        else if (mTimer.equals("1")) {
            timer = milMinute;
        }
        else if (mTimer.equals("2")) {
            timer = 2 * milMinute;
        }
        else if (mTimer.equals("5")) {
            timer = 5 * milMinute;
        }
        else if (mTimer.equals("10")) {
            timer = 10 * milMinute;
        }
        else if (mTimer.equals("15")) {
            timer = 15 * milMinute;
        }
        else if (mTimer.equals("20")) {
            timer = 20 * milMinute;
        }
        else if (mTimer.equals("35")) {
            timer = 35 * milMinute;
        }
        else if (mTimer.equals("50")) {
            timer = 50 * milMinute;
        }
        else if (mTimer.equals("60")) {
            timer = 60 * milMinute;
        }

        long timeStampAlarm =  mCalendar.getTimeInMillis();
        long timeStamp = timeStampAlarm - timer;



        if (mCurrentReminderUri == null) {
            // This is a NEW reminder, so insert a new reminder into the provider,
            // returning the content URI for the new reminder.
            Uri newUri = getContentResolver().insert(AlarmReminderContract.ScheduleEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            int rowsAffected = getContentResolver().update(mCurrentReminderUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Create a new notification
        if (mActive.equals("true")) {
            //Set notification
            new ScheduleAlarmScheduler().setRepeatAlarm(getApplicationContext(), timeStamp,
                    mCurrentReminderUri, 7*24*60*60*1000);
            //Check variable days2
            if (days2 != 0){

                mCalendar2 = Calendar.getInstance();
                mCalendar2.set(Calendar.DAY_OF_WEEK, days2);
                mCalendar2.set(Calendar.HOUR_OF_DAY, mHour);
                mCalendar2.set(Calendar.MINUTE, mMinute);
                mCalendar2.set(Calendar.SECOND, 0);
                mCalendar2.set(Calendar.MILLISECOND, 0);

                long timeStampAlarm2 = mCalendar2.getTimeInMillis();
                long timeStamp2  = timeStampAlarm2 - timer;

                //Set Notifikasi for days2
                new ScheduleAlarmScheduler().setRepeatAlarm(getApplicationContext(), timeStamp2,
                        mCurrentReminderUri2, 7*24*60*60*1000);
            }



            Toast.makeText(this, "AlarmActivity time is " + txtTime.getText().toString(),
                    Toast.LENGTH_LONG).show();

        }
        // Deactivated alarm
        else if (mActive.equals("false")){
            new ScheduleAlarmScheduler().cancelAlarm(getApplicationContext(), mCurrentReminderUri);
            new ScheduleAlarmScheduler().cancelAlarm(getApplicationContext(), mCurrentReminderUri2);

            Toast.makeText(this, "AlarmActivity is deactivated", Toast.LENGTH_SHORT).show();
        }

        // Create toast to confirm new reminder
        Toast.makeText(getApplicationContext(), "Saved",
                Toast.LENGTH_SHORT).show();

    }


    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                MahasiswaDBOpenHelper.COL_SUBJECT_ID,
                MahasiswaDBOpenHelper.COL_SUB_ID,
                MahasiswaDBOpenHelper.COL_SUBJECT_NAME,
                MahasiswaDBOpenHelper.COL_SUBJECT_PARALLEL,
                MahasiswaDBOpenHelper.COL_SUBJECT_TIME,
                MahasiswaDBOpenHelper.COL_SUBJECT_TIMER,
                MahasiswaDBOpenHelper.COL_SUBJECT_DAY,
                MahasiswaDBOpenHelper.COL_SUBJECT_ROOM,
                MahasiswaDBOpenHelper.COL_SUBJECT_TEACHER,
                MahasiswaDBOpenHelper.COL_SUBJECT_ACTIVE,
        };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentReminderUri,         // Query the content URI for the current reminder
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUB_ID);
            int titleColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_NAME);
            int parallelColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_PARALLEL);
            int timeColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_TIME);
            int timerColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_TIMER);
            int dayColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_DAY);
            int roomColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_ROOM);
            int teacherColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_TEACHER);
            int activeColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_ACTIVE);


            // Extract out the value from the Cursor for the given column index
            String id = cursor.getString(idColumnIndex);
            String title = cursor.getString(titleColumnIndex);
            String parallel = cursor.getString(parallelColumnIndex);
            String time = cursor.getString(timeColumnIndex);
            String timer = cursor.getString(timerColumnIndex);
            String day = cursor.getString(dayColumnIndex);
            String room = cursor.getString(roomColumnIndex);
            String teacher = cursor.getString(teacherColumnIndex);
            String active = cursor.getString(activeColumnIndex);



            // Update the views on the screen with the values from the database
            etSubCode.setText(id);
            etTitle.setText(title);
            etSubParallel.setText(parallel);
            txtTime.setText(time);
            txtTimer.setText(timer);
            txtDay.setText(day);
            etSubRoom.setText(room);
            etSubTeacher.setText(teacher);
            // Setup up active buttons
            // Setup repeat switch
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
