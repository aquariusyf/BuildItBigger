package com.example.android.imageactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ImageActivity extends AppCompatActivity {

    public static final String JOKE_KEY = "joke";
    private TextView mJokeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mJokeTv = findViewById(R.id.tv_joke);

        Intent intent = getIntent();
        String joke = intent.getStringExtra(JOKE_KEY);
        mJokeTv.setText(joke);

    }
}
