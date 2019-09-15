package com.example.tikz.personalassistantuk.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tikz.personalassistantuk.R;
import com.example.tikz.personalassistantuk.data.MahasiswaDBAccess;

import static com.example.tikz.personalassistantuk.R.id.card_schedule;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView cardabsent, cardschedule, cardass, cardevent, cardabout, cardhelp;
    private TextView txtStuName;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Create access to class MahasiswaDBAccess
        MahasiswaDBAccess databaseAccess = MahasiswaDBAccess.getInstance(getApplicationContext());
        //Create access to class Constant

        cardabsent = (CardView) findViewById(R.id.card_absent);
        cardschedule = (CardView) findViewById(card_schedule);
        cardass = (CardView) findViewById(R.id.card_ass);
        cardevent = (CardView) findViewById(R.id.card_event);
        cardabout = (CardView) findViewById(R.id.card_about);
        cardhelp = (CardView) findViewById(R.id.card_help);

        cardabsent.setOnClickListener(this);
        cardschedule.setOnClickListener(this);
        cardass.setOnClickListener(this);
        cardevent.setOnClickListener(this);
        cardabout.setOnClickListener(this);
        cardhelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.card_schedule:
                i = new Intent(this, ScheduleActivity.class);
                startActivity(i);
                break;

            case R.id.card_event:
                i = new Intent(this, EventActivity.class);
                startActivity(i);
                break;

            case R.id.card_ass:
                i = new Intent(this, AssignmentExamActivity.class);
                startActivity(i);
                break;

            case R.id.card_absent:
                i = new Intent(this, AbsentActivity.class);
                startActivity(i);
                break;

            case R.id.card_about:
                i = new Intent(this, AboutActivity.class);
                startActivity(i);
                break;

            case R.id.card_help:
                i = new Intent(this, HelpActivity.class);
                startActivity(i);
                break;

            default:
                break;
        }
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        /*
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
        */
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Exit this application?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                finish();

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
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
