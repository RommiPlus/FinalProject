package com.jokelibrary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeViewActivity extends Activity {
    public static String JOKE_DATA = "joke_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_info);
        String joke = getIntent().getStringExtra(JOKE_DATA);
        ((TextView)findViewById(R.id.joke_info)).setText(joke);
    }


}
