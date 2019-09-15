package com.example.tikz.personalassistantuk.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tikz.personalassistantuk.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_about);
    }
}
