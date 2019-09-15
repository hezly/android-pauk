package com.example.tikz.personalassistantuk.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.tikz.personalassistantuk.R;
import com.example.tikz.personalassistantuk.data.AlarmReminderContract;
import com.example.tikz.personalassistantuk.data.MahasiswaDBOpenHelper;


public class ScheduleCursorAdapter extends CursorAdapter {

    private TextView mTitleText, mDateAndTimeText, mRepeatInfoText;
    private ImageView mActiveImage , mThumbnailImage;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    public ScheduleCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.alarm_items, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        mTitleText = (TextView) view.findViewById(R.id.recycle_title);
        mDateAndTimeText = (TextView) view.findViewById(R.id.recycle_date_time);
        mRepeatInfoText = (TextView) view.findViewById(R.id.recycle_repeat_info);
        mActiveImage = (ImageView) view.findViewById(R.id.active_image);
        mThumbnailImage = (ImageView) view.findViewById(R.id.thumbnail_image);


        int titleColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_NAME);
        int dateColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_DAY);
        int parallelColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_PARALLEL);
        int roomColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_ROOM);
        int teacherNoColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_TEACHER);
        int timeColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_TIME);
        int timerColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_TIMER);
        int activeColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_SUBJECT_ACTIVE);

        String title = cursor.getString(titleColumnIndex);
        String date = cursor.getString(dateColumnIndex);
        String parallel = cursor.getString(parallelColumnIndex);
        String room = cursor.getString(roomColumnIndex);
        String teacher = cursor.getString(teacherNoColumnIndex);
        String time = cursor.getString(timeColumnIndex);
        String timer = cursor.getString(timerColumnIndex);
        String active = cursor.getString(activeColumnIndex);

        setReminderTitle(title,parallel);

        if (date != null){
            String dateTime = date + " " + time;
            mDateAndTimeText.setText(dateTime);
        }else{
            mDateAndTimeText.setText("Date not set");
        }

        if (room !=null){
            String classroom = room;
            mRepeatInfoText.setText(classroom);
        }else {
            mRepeatInfoText.setText("Classromm not set");
        }
        if (active != null){
            setActiveImage(active);
        }else{
            mActiveImage.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
        }
    }

    // Set reminder title view
    public void setReminderTitle(String title, String parallel) {
        String letter = "A";

        if(title != null && !title.isEmpty() && parallel != null) {
            String set = title + " - " + parallel;
            mTitleText.setText(set);
            letter = title.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();

        // Create a circular icon consisting of  a random background colour and first letter of title
        mDrawableBuilder = TextDrawable.builder()
                .buildRound(letter, color);
        mThumbnailImage.setImageDrawable(mDrawableBuilder);
    }

    // Set active image as on or off
    public void setActiveImage(String active){
        if(active.equals("true")){
            mActiveImage.setImageResource(R.drawable.ic_notifications_on_white_24dp);
        }else if (active.equals("false")) {
            mActiveImage.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
        }

    }
}
