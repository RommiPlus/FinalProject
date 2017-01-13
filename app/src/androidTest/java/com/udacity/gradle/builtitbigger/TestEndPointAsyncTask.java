package com.udacity.gradle.builtitbigger;

import android.app.Activity;
import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.util.Pair;

import com.udacity.gradle.builditbigger.EndPointAsyncTask;
import com.udacity.gradle.builditbigger.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;

/**
 * Created by 123 on 2017/1/13.
 */

@RunWith(AndroidJUnit4.class)
public class TestEndPointAsyncTask {
    private Activity mContext;
    String mString = null;
    Exception mError = null;
    CountDownLatch mSignal = null;

    @Rule
    public ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mContext = mActivity.getActivity();
        mSignal = new CountDownLatch(1);
    }

    @After
    public void tearDown() {
        mSignal.countDown();
    }

    @Test
    public void testAsyncTask() throws InterruptedException {
        EndPointAsyncTask task = new EndPointAsyncTask();
        //noinspection unchecked
        task.setListener(new EndPointAsyncTask.EndPointTaskListener() {
            @Override
            public void onComplete(String string, Exception e) {
                mString = string;
                mError = e;
                mSignal.countDown();
            }
        }).execute(new Pair<Context, String>(mContext, "Manfred"));

        mSignal.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mString));
        assertEquals(
                "EndPointAsyncTask communicate to network error: please check google service is ok",
                "This is a joke: a man named Manfred fall into river",
                mString);
    }


}

