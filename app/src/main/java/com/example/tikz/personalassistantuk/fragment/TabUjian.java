package com.example.tikz.personalassistantuk.fragment;

import android.app.Activity;
import android.content.ContentUris;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tikz.personalassistantuk.R;
import com.example.tikz.personalassistantuk.activity.Popup_UjianTugas;
import com.example.tikz.personalassistantuk.adapter.ExamCursorAdapter;
import com.example.tikz.personalassistantuk.data.AlarmReminderContract;
import com.example.tikz.personalassistantuk.data.MahasiswaDBOpenHelper;

public class TabUjian extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    ExamCursorAdapter mCursorAdapter;
    ListView exListView;
    TextView exText,noAssText;
    Context context;

    private String assignmentTitle = "";

    private static final int VEHICLE_LOADER = 100;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        context=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_ujian, container, false);

        exListView = (ListView) rootView.findViewById(R.id.exList);
        exText = (TextView) rootView.findViewById(R.id.exText);
        View emptyView = rootView.findViewById(R.id.empty_view_ex);
        exListView.setEmptyView(emptyView);

        mCursorAdapter = new ExamCursorAdapter(getActivity(), null);
        exListView.setAdapter(mCursorAdapter);

        exListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), Popup_UjianTugas.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.ExamReminderEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentVehicleUri);

                view.getContext().startActivity(intent);

            }
        });

        exListView.setLongClickable(true);

        exListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //deleteSelectedItemList();

                String delete = "delete";

                final Intent intent = new Intent(getActivity().getApplicationContext(), Popup_UjianTugas.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.ExamReminderEntry.CONTENT_URI, id);

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

        String where = "Exam";
        String whereClause = MahasiswaDBOpenHelper.COL_ASSEXAM_TYPE+"=?";
        String [] whereArgs = {where};

        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                AlarmReminderContract.ExamReminderEntry.CONTENT_URI,
                projection, whereClause,
                whereArgs, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
        if (cursor.getCount() > 0){
            exText.setVisibility(View.VISIBLE);
        }else{
            exText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

    public void restartLoader(){
        getActivity().getSupportLoaderManager().restartLoader(VEHICLE_LOADER, null, this);
    }
}
