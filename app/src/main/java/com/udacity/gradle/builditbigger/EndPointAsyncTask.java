package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;

import com.example.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.jokelibrary.JokeViewActivity;

import java.io.IOException;

/**
 * Created by 123 on 2017/1/13.
 */

public class EndPointAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;

    private Context mContext;
    private Exception mError = null;
    private EndPointTaskListener mListener = null;

    public EndPointAsyncTask setListener(EndPointTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    public static interface EndPointTaskListener {
        public void onComplete(String jsonString, Exception e);
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://testfinalproject-155407.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            // end options for devappserver
            myApiService = builder.build();
        }

        mContext = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.getJoke(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onCancelled() {
        if (this.mListener != null) {
            mError = new InterruptedException("AsyncTask cancelled");
            this.mListener.onComplete(null, mError);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        tellJokeInNewActivity(result);

        if (this.mListener != null) {
            this.mListener.onComplete(result, mError);
        }
    }

    public void tellJokeInNewActivity(String result) {
        Intent intent = new Intent(mContext, JokeViewActivity.class);
        intent.putExtra(JokeViewActivity.JOKE_DATA, result);
        mContext.startActivity(intent);
    }
}
