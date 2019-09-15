package com.example.tikz.personalassistantuk.fragment;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tikz.personalassistantuk.R;
import com.example.tikz.personalassistantuk.activity.Popup_UjianTugas;
import com.example.tikz.personalassistantuk.adapter.AssExamCursorAdapter;
import com.example.tikz.personalassistantuk.data.AlarmReminderContract;
import com.example.tikz.personalassistantuk.data.MahasiswaDBOpenHelper;

public class TabTugas extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    AssExamCursorAdapter mCursorAdapter2;
    ListView assListView;
    TextView assText,noAssText;
    Context context;

    private String assignmentTitle = "";

    private static final int VEHICLE_LOADER = 0;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        context=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_tugas, container, false);

        assListView = (ListView) rootView.findViewById(R.id.assList);
        assText = (TextView) rootView.findViewById(R.id.assignmentText);
        View emptyView = rootView.findViewById(R.id.empty_view_ass);
        assListView.setEmptyView(emptyView);

        mCursorAdapter2 = new AssExamCursorAdapter(getActivity(), null);
        assListView.setAdapter(mCursorAdapter2);

        assListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(getActivity().getApplicationContext(), Popup_UjianTugas.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.AssignmentEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentVehicleUri);

                view.getContext().startActivity(intent);

            }
        });

        assListView.setLongClickable(true);

        assListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //deleteSelectedItemList();

                String delete = "delete";

                final Intent intent = new Intent(getActivity().getApplicationContext(), Popup_UjianTugas.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.AssignmentEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentVehicleUri);
                intent.putExtra("DELETE_DATA",delete);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

        getActivity().getSupportLoaderManager().initLoader(VEHICLE_LOADER, null, this);

        return rootView;
    }

    private void deleteSelectedItemList() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
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

        String where = "Assignment";
        String whereClause = MahasiswaDBOpenHelper.COL_ASSEXAM_TYPE+"=?";
        String [] whereArgs = {where};

        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                AlarmReminderContract.AssignmentEntry.CONTENT_URI,
                projection, whereClause,
                whereArgs, null);

        return cursorLoader;
    }

    public void addReminderTitle(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Set ScheduleActivity Title");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().isEmpty()){
                    return;
                }

                assignmentTitle = input.getText().toString();
                ContentValues values = new ContentValues();

                values.put(MahasiswaDBOpenHelper.COL_SUBJECT_NAME, assignmentTitle);
                values.put(MahasiswaDBOpenHelper.COL_ASSEXAM_TYPE, "Assignment");

                Uri newUri = getActivity().getContentResolver().insert(AlarmReminderContract.AssignmentEntry.CONTENT_URI, values);

                restartLoader();


                if (newUri == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Setting Reminder Title failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Title set successfully", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter2.swapCursor(cursor);
        if (cursor.getCount() > 0){
            assText.setVisibility(View.VISIBLE);
        }else{
            assText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter2.swapCursor(null);
    }

    public void restartLoader(){
        getActivity().getSupportLoaderManager().restartLoader(VEHICLE_LOADER, null, this);
    }
}
