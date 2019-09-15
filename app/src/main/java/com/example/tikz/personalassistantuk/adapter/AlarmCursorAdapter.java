package com.example.tikz.personalassistantuk.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.tikz.personalassistantuk.R;
import com.example.tikz.personalassistantuk.data.AbsentDBOpenHelper;
import com.example.tikz.personalassistantuk.data.AlarmReminderContract;

public class AlarmCursorAdapter extends CursorAdapter {

    private TextView mTitleText, mDateAndTimeText, mRepeatInfoText;
    private ImageView mActiveImage , mThumbnailImage;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    public AlarmCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 );
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
        /*
        int titleColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
        int dateColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_DATE);
        int timeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TIME);
        int repeatColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT);
        int repeatNoColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO);
        int repeatTypeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE);
        int activeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE);

        String title = cursor.getString(titleColumnIndex);
        String date = cursor.getString(dateColumnIndex);
        String time = cursor.getString(timeColumnIndex);
        String repeat = cursor.getString(repeatColumnIndex);
        String repeatNo = cursor.getString(repeatNoColumnIndex);
        String repeatType = cursor.getString(repeatTypeColumnIndex);
        String active = cursor.getString(activeColumnIndex);

        /*
        mTitleText.setText(subcode);
        mDateAndTimeText.setText(date);
        mRepeatInfoText.setText(type);

        setReminderTitle(subcode);

        if (date != null){
            String dateTime = date;
            setReminderDateTime(dateTime);
        }else{
            mDateAndTimeText.setText("Date not set");
        }

        if(type != null){
            setReminderRepeatInfo(type);
        }else{
            mRepeatInfoText.setText("Repeat Not Set");
        }

        if (active != null){
            setActiveImage(active);
        }else{
            mActiveImage.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
        }
        */
        int dateColumnIndex = cursor.getColumnIndex(AbsentDBOpenHelper.COL_ABSENT_DATE);
        int nimColumnIndex = cursor.getColumnIndex(AbsentDBOpenHelper.COL_STUDENT_NIM);
        int regNumbColumnIndex = cursor.getColumnIndex(AbsentDBOpenHelper.COL_STU_REG_NUMB);
        int subcodeColumnIndex = cursor.getColumnIndex(AbsentDBOpenHelper.COL_SUBJECT_CODE);
        int typeColumnIndex = cursor.getColumnIndex(AbsentDBOpenHelper.COL_TYPE);

        String date = cursor.getString(dateColumnIndex);
        String nim = cursor.getString(nimColumnIndex);
        String regNumb = cursor.getString(regNumbColumnIndex);
        String subcode = cursor.getString(subcodeColumnIndex);
        String type = cursor.getString(typeColumnIndex);
        String active = "true";


        setReminderTitle(subcode);

        if (date != null){
            String dateTime = date;
            setReminderDateTime(dateTime);
        }else{
            mDateAndTimeText.setText("Date not set");
        }

        if(type != null){
            setReminderRepeatInfo(type);
        }else{
            mRepeatInfoText.setText("Repeat Not Set");
        }

        if (active != null){
            setActiveImage(active);
        }else{
            mActiveImage.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
        }

    }

    // Set reminder title view
    public void setReminderTitle(String title) {
        mTitleText.setText(title);
        String letter = "A";

        if(title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();

        // Create a circular icon consisting of  a random background colour and first letter of title
        mDrawableBuilder = TextDrawable.builder()
                .buildRound(letter, color);
        mThumbnailImage.setImageDrawable(mDrawableBuilder);
    }

    // Set date and time views
    public void setReminderDateTime(String datetime) {
        mDateAndTimeText.setText(datetime);
    }

    // Set repeat views
    public void setReminderRepeatInfo(String repeat) {
        if(repeat.equals("true")){
            mRepeatInfoText.setText("Every " + repeat);
        }else if (repeat.equals("false")) {
            mRepeatInfoText.setText("Repeat Off");
        }
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
