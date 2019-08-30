package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAsyncTask() throws InterruptedException {
        Log.v("TestModule", "Create AsyncTask");
        EndpointsAsyncTask endpointsAsyncTask =
                new EndpointsAsyncTask(new EndpointsAsyncTask.OnEventListener<String>() {
            @Override
            public void onSuccess(String result) {
                assertNotNull(result);
                if(result != null) {
                    Log.v("TestModule", result);
                }
            }

            @Override
            public void onFailure(Exception e) {
                fail();
            }
        });
        Log.v("TestModule", "Execute AsyncTask");
        endpointsAsyncTask.execute();
        Thread.sleep(20000);
    }

}
