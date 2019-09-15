package com.example.tikz.personalassistantuk.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.TextClock;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;

import com.example.tikz.personalassistantuk.Constant;
import com.example.tikz.personalassistantuk.R;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmActivity extends AppCompatActivity {

    TextView txtTitle,txtTime,txtInfo,txtName;
    Button btnStop;
    Ringtone ringtone;
    Uri uriAlarm, uriNotification, uriRingtone;
    Vibrator vibrator;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        txtTitle = (TextView) findViewById(R.id.alarm_title);
        txtTime = (TextView) findViewById(R.id.alarm_time);
        txtInfo = (TextView) findViewById(R.id.alarm_info);
        txtName = (TextView) findViewById(R.id.name);
        btnStop = (Button) findViewById(R.id.button_alarm);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        uriAlarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        String title = getIntent().getStringExtra("title");
        String time = getIntent().getStringExtra("time");
        String info = getIntent().getStringExtra("info");

        txtTitle.setText(title);
        txtTime.setText(time);
        txtInfo.setText(info);
        txtName.setText(Constant.getFirstName());

        ringtone = RingtoneManager
                .getRingtone(getApplicationContext(), uriAlarm);

        //ringtone.play();
        //vibrator.vibrate(1000);
        Timer mTimer = new Timer();

        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (ringtone != null){
                    ringtone.play();
                    vibrator.vibrate(700);
                }
            }
        }, 1000, 1000);

        btnStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(ringtone != null){
                    ringtone.stop();
                    //vibrator.cancel();
                    ringtone = null;
                    //finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce){
            super.onBackPressed();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
