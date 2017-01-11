package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.JokeSmith;
import com.jokelibrary.JokeViewActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        JokeSmith jokeSmith = new JokeSmith();
        Intent intent = new Intent(MainActivity.this, JokeViewActivity.class);
        intent.putExtra(JokeViewActivity.JOKE_DATA, jokeSmith.makeJoke());
        startActivity(intent);
    }

    public void tellJoke() {
        JokeSmith jokeSmith = new JokeSmith();
        MainActivityFragment fragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.main_activity_fragment);
        View view1 = fragment.getView();
        TextView jokeInfo = (TextView) view1.findViewById(R.id.instructions_text_view);
        jokeInfo.setText(jokeSmith.makeJoke());
//      Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
    }


}
