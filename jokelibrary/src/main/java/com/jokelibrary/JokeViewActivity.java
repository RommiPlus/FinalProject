package com.jokelibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class JokeViewActivity extends AppCompatActivity {
    public static String JOKE_DATA = "joke_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_info);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        String joke = getIntent().getStringExtra(JOKE_DATA);
        ((TextView)findViewById(R.id.joke_info)).setText(joke);
    }


}
