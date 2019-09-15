package com.example.tikz.personalassistantuk.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tikz.personalassistantuk.R;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.tikz.personalassistantuk.data.AbsentDBOpenHelper;

public class AbsentCursorAdapter extends
        RecyclerView.Adapter<AbsentCursorAdapter.MenuHolder> {


    ImageView mActiveImage,mThumbnailImage;

    private Cursor mCursor = null;
    private Context mContext;
    private final LayoutInflater mInflater;

    private static final String TAG = AbsentCursorAdapter.class.getSimpleName();

    public class MenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitleText,mDateAndTimeText,mRepeatInfoText;

        public MenuHolder(View itemView) {
            super(itemView);


            mTitleText = (TextView) itemView.findViewById(R.id.absent_subject);
            mDateAndTimeText = (TextView) itemView.findViewById(R.id.absent_date_time);
            mRepeatInfoText = (TextView) itemView.findViewById(R.id.absent_type);
            //mActiveImage = (ImageView) itemView.findViewById(R.id.active_image);
            //mThumbnailImage = (ImageView) itemView.findViewById(R.id.thumbnail_image);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });



        }

        @Override
        public void onClick(View view) {

        }
    }

    public AbsentCursorAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        //this.mCursor = cursor;
    }

    public void setData(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public AbsentCursorAdapter.MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.alarm_items, parent, false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {

        if (mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                int date = mCursor.getColumnIndex(AbsentDBOpenHelper.COL_ABSENT_DATE);
                int subcode = mCursor.getColumnIndex(AbsentDBOpenHelper.COL_SUBJECT_CODE);
                int type = mCursor.getColumnIndex(AbsentDBOpenHelper.COL_TYPE);

                String datetime = mCursor.getString(date);
                String subjectcode = mCursor.getString(subcode);
                String types = mCursor.getString(type);

                holder.mTitleText.setText(subcode);
                holder.mDateAndTimeText.setText(datetime);
                holder.mRepeatInfoText.setText(types);


                Log.e (TAG, "onBindViewHolder: is on point");
            } else {
                Log.e (TAG, "onBindViewHolder: Cursor is null.");
            }
        } else {
            Log.e (TAG, "onBindViewHolder: Cursor is null.");
        }

    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else {
            return -1;
        }
    }
}
