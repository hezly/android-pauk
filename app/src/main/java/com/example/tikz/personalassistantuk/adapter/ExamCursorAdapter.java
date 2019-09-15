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
import com.example.tikz.personalassistantuk.data.MahasiswaDBOpenHelper;

public class ExamCursorAdapter extends CursorAdapter {

    private TextView mTitleText, mDateAndTimeText, mRepeatInfoText;
    private ImageView mActiveImage , mThumbnailImage;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    public ExamCursorAdapter(Context context, Cursor c) {
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

        int idColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_ID);
        int titleColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_TITLE);
        int subColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_SUB);
        int dateColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_DATE);
        int timeColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_TIME);
        int infoColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_INFO);
        int typeColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_TYPE);
        int activeColumnIndex = cursor.getColumnIndex(MahasiswaDBOpenHelper.COL_ASSEXAM_ACTIVE);

        String id = cursor.getString(idColumnIndex);
        String title = cursor.getString(titleColumnIndex);
        String subject = cursor.getString(subColumnIndex);
        String date = cursor.getString(dateColumnIndex);
        String time = cursor.getString(timeColumnIndex);
        String info = cursor.getString(infoColumnIndex);
        String type = cursor.getString(typeColumnIndex);
        String active = cursor.getString(activeColumnIndex);

        setReminderTitle(title);

        if (date != null){
            String dateTime = date + " " + time;
            setReminderDateTime(dateTime);
        }else{
            mDateAndTimeText.setText("Date not set");
        }

        if(subject != null){
            setReminderRepeatInfo(subject);
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
    public void setReminderRepeatInfo(String repeatNo) {
            mRepeatInfoText.setText(repeatNo);
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
