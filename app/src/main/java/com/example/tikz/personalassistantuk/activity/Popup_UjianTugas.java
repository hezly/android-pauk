package com.example.tikz.personalassistantuk.activity;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tikz.personalassistantuk.R;
import com.example.tikz.personalassistantuk.data.AlarmReminderContract;
import com.example.tikz.personalassistantuk.data.MahasiswaDBAccess;
import com.example.tikz.personalassistantuk.data.MahasiswaDBOpenHelper;
import com.example.tikz.personalassistantuk.reminder.AssExAlarmScheduler;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;

public class Popup_UjianTugas extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_VEHICLE_LOADER = 0;


    private Toolbar mToolbar;
    private EditText mTitleText, mInfoText;
    private TextView mDateText, mTimeText, mSubjectText, mTypeText;
    private FloatingActionButton mFAB1;
    private FloatingActionButton mFAB2;
    private Calendar mCalendar, mCalender2;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private long timer;
    private String mTitle;
    private String mTime;
    private String mDate;
    private String mSubject;
    private String mInfo;
    private String mActive, mType;
    private Spinner spinSubject;

    private Uri mCurrentReminderUri, mCurrentReminderUri2;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_ujian_tugas);

        //Initial View
        MahasiswaDBAccess dbAccessMahasiswa = MahasiswaDBAccess.getInstance(getApplicationContext());
        List<String> tes = dbAccessMahasiswa.getSubjectName();
        if (tes.size() == 0){
            Toast.makeText(this, "ScheduleActivity Empty, Enter ScheduleActivity First!", Toast.LENGTH_LONG).show();
            finish();
        }

        Intent intent = getIntent();
        mCurrentReminderUri = intent.getData();


        if (mCurrentReminderUri == null) {

            setTitle(getString(R.string.editor_activity_title_new_assexam));

            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a reminder that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {

            setTitle(getString(R.string.editor_activity_title_edit_assexam));


            getLoaderManager().initLoader(EXISTING_VEHICLE_LOADER, null, this);
        }


        // Initialize Views

        initDeleteView();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mInfoText = (EditText) findViewById(R.id.etAssexam_details);
        mTitleText = (EditText) findViewById(R.id.assexam_title);
        mDateText = (TextView) findViewById(R.id.set_date_assexam);
        mTimeText = (TextView) findViewById(R.id.set_time_assexam);
        spinSubject = (Spinner) findViewById(R.id.set_subject_assexam);
        mTypeText = (TextView) findViewById(R.id.set_type_assexam);
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred_assexam);
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred_assexam2);

        // Initialize default values
        mActive = "true";
        mSubject = "None";
        mType = "Assignment";
        getSubjectData();
        //spinSubject.setOnItemSelectedListener(this);

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;
        final Context context = this;
        //Setup spinner to display the selected item id


        // Setup Reminder Title EditText
        mTitleText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                mTitleText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mInfoText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mInfo = s.toString().trim();
                mInfoText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        try {
            mSubject = spinSubject.getSelectedItem().toString();
        }catch (Exception e){
            Toast.makeText(this, "Error : "+e, Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent(this, AssignmentExamActivity.class);
            startActivity(intent2);
        }

        // Setup TextViews using default reminder values
        mTitleText.setText(mTitle);
        mDateText.setText(mDate);
        mInfoText.setText(mInfo);
        mTimeText.setText(mTime);
        mTypeText.setText(mType);
        //mSubjectText.setText(mSubject);


        // To save state on device rotation
        if (savedInstanceState != null) {
            String savedTitle = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_ASSEXAM_TITLE);
            mTitleText.setText(savedTitle);
            mTitle = savedTitle;

            String savedInfo = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_ASSEXAM_INFO);
            mInfoText.setText(savedInfo);
            mInfo = savedInfo;

            String savedTime = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_ASSEXAM_TIME);
            mTimeText.setText(savedTime);
            mTime = savedTime;

            String savedDate = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_ASSEXAM_DATE);
            mDateText.setText(savedDate);
            mDate = savedDate;

            String saveSubject = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_ASSEXAM_SUB);
            mSubject = saveSubject;

            mActive = savedInstanceState.getString(MahasiswaDBOpenHelper.COL_ASSEXAM_ACTIVE);
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

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(MahasiswaDBOpenHelper.COL_ASSEXAM_TYPE, mTypeText.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_ASSEXAM_TITLE, mTitleText.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_ASSEXAM_INFO, mInfoText.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_ASSEXAM_DATE, mDateText.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_ASSEXAM_TIME, mTimeText.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_ASSEXAM_SUB, mSubjectText.getText());
        outState.putCharSequence(MahasiswaDBOpenHelper.COL_ASSEXAM_ACTIVE, mActive);
    }

    private void initDeleteView() {
        if (getIntent().getStringExtra("DELETE_DATA")!=null){
            String delete = getIntent().getStringExtra("DELETE_DATA");
            if (delete.equals("delete")){
                deleteReminder();
            }
        }
    }

    // On clicking Time picker
    public void setTimeEvent(View v){
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

    // On clicking Date picker
    public void setDateEvent(View v){
        if(mCurrentReminderUri == null){
            Toast.makeText(this, "click again on the reminder list to set date alarm", Toast.LENGTH_LONG).show();
        }
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
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
        mTimeText.setText(mTime);
    }

    // Obtain date from date picker
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear ++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        mDateText.setText(mDate);
    }

    // On clicking the active button
    public void selectFab1(View v) {
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred_assexam);
        mFAB1.setVisibility(View.GONE);
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred_assexam2);
        mFAB2.setVisibility(View.VISIBLE);
        mActive = "true";
    }

    // On clicking the inactive button
    public void selectFab2(View v) {
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred_assexam2);
        mFAB2.setVisibility(View.GONE);
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred_assexam);
        mFAB1.setVisibility(View.VISIBLE);
        mActive = "false";
    }

    //On clicking type
    public void selectType(View v){
        final String[] items = new String[2];

        items[0] = "Assignment";
        items[1] = "Exam";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Type");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mType = items[item];
                mTypeText.setText(mType);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // On clicking subject button
    public void getSubjectData(){


        MahasiswaDBAccess dbAccessMahasiswa = MahasiswaDBAccess.getInstance(getApplicationContext());
        //Get spinner dropdown element
        List<String> subjects = dbAccessMahasiswa.getSubjectName();
        if (subjects==null){
            Toast.makeText(this, "ScheduleActivity Empty", Toast.LENGTH_LONG).show();
            finish();
        }
        //Create adapter for spinner
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(Popup_UjianTugas.this,
                android.R.layout.simple_list_item_1, subjects);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Attach subject adapter to spinner
        spinSubject.setAdapter(subjectAdapter);

        spinSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemvalue = parent.getItemAtPosition(position).toString();
                mSubject = itemvalue;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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


                if (mTitleText.getText().toString().length() == 0){
                    mTitleText.setError("Title cannot be blank!");
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
                    NavUtils.navigateUpFromSameTask(Popup_UjianTugas.this);
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
                                NavUtils.navigateUpFromSameTask(Popup_UjianTugas.this);
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
                new AssExAlarmScheduler().cancelAlarmAssEx(getApplicationContext(), mCurrentReminderUri);

                new AssExAlarmScheduler().cancelAlarmAssEx(getApplicationContext(), mCurrentReminderUri2);

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
        values.put(MahasiswaDBOpenHelper.COL_ASSEXAM_TYPE, mType);
        values.put(MahasiswaDBOpenHelper.COL_ASSEXAM_TITLE, mTitle);
        values.put(MahasiswaDBOpenHelper.COL_ASSEXAM_INFO, mInfo);
        values.put(MahasiswaDBOpenHelper.COL_ASSEXAM_DATE, mDate);
        values.put(MahasiswaDBOpenHelper.COL_ASSEXAM_TIME, mTime);
        values.put(MahasiswaDBOpenHelper.COL_ASSEXAM_SUB, mSubject);
        values.put(MahasiswaDBOpenHelper.COL_ASSEXAM_ACTIVE, mActive);


        // Set up calender for creating the notification
        mCalendar.set(Calendar.MONTH, --mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);

        long selectedTimestamp =  mCalendar.getTimeInMillis();

        mCalender2 = Calendar.getInstance();

        mCalender2.set(Calendar.HOUR_OF_DAY, 9);
        mCalender2.set(Calendar.AM_PM, Calendar.PM);
        mCalender2.set(Calendar.MINUTE, 0);

        long repeatTimestamp = mCalender2.getTimeInMillis();

        if (mCurrentReminderUri == null) {
            // This is a NEW reminder, so insert a new reminder into the provider,
            // returning the content URI for the new reminder.
            Uri newUri = getContentResolver().insert(AlarmReminderContract.AssignmentEntry.CONTENT_URI, values);

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

            new AssExAlarmScheduler().setAlarmAssExam(getApplicationContext(), selectedTimestamp, mCurrentReminderUri);

            new AssExAlarmScheduler().setRepeatAssExam(getApplicationContext(), repeatTimestamp, mCurrentReminderUri2, 7*24*60*60*1000);

            Toast.makeText(this, "AlarmActivity time is " + mTimeText.getText().toString(),
                    Toast.LENGTH_LONG).show();
        }

        // Deactivated alarm
        else if (mActive.equals("false")){
            new AssExAlarmScheduler().cancelAlarmAssEx(getApplicationContext(), mCurrentReminderUri);

            new AssExAlarmScheduler().cancelAlarmAssEx(getApplicationContext(), mCurrentReminderUri2);

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
                MahasiswaDBOpenHelper.COL_ASSEXAM_ID,
                MahasiswaDBOpenHelper.COL_ASSEXAM_TITLE,
                MahasiswaDBOpenHelper.COL_ASSEXAM_SUB,
                MahasiswaDBOpenHelper.COL_ASSEXAM_DATE,
                MahasiswaDBOpenHelper.COL_ASSEXAM_INFO,
                MahasiswaDBOpenHelper.COL_ASSEXAM_TIME,
                MahasiswaDBOpenHelper.COL_ASSEXAM_TYPE,
                MahasiswaDBOpenHelper.COL_ASSEXAM_ACTIVE
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
            int idColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_ID);
            int titleColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_TITLE);
            int subColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_SUB);
            int dateColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_DATE);
            int infoColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_INFO);
            int timeColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_TIME);
            int typeColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_TYPE);
            int activeColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_ACTIVE);


            // Extract out the value from the Cursor for the given column index
            String id = cursor.getString(idColumnIndex);
            String title = cursor.getString(titleColumnIndex);
            String subject = cursor.getString(subColumnIndex);
            String date = cursor.getString(dateColumnIndex);
            String info = cursor.getString(infoColumnIndex);
            String time = cursor.getString(timeColumnIndex);
            String type = cursor.getString(typeColumnIndex);
            String active = cursor.getString(activeColumnIndex);



            // Update the views on the screen with the values from the database
            mTitleText.setText(title);
            mTypeText.setText(type);
            //mSubjectText.setText(subject);

            mDateText.setText(date);
            mTimeText.setText(time);
            mInfoText.setText(info);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
