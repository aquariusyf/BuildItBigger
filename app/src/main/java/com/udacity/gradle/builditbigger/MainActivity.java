package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.imageactivity.ImageActivity;

public class MainActivity extends AppCompatActivity {

    private static boolean sIsFree;
    private static ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getPackageName().equals("com.udacity.gradle.builditbigger.free")) {
            sIsFree = true;
        } else {
            sIsFree = false;
        }

        mLoadingIndicator = findViewById(R.id.pb_loading);
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
        mLoadingIndicator.setVisibility(View.VISIBLE);

        EndpointsAsyncTask asyncTask =
                new EndpointsAsyncTask(new EndpointsAsyncTask.OnEventListener<String>() {
            @Override
            public void onSuccess(final String result) {
                if(sIsFree) {
                    MainActivityFragment.adHelper.setInterAdListener(result, MainActivity.this);
                    MainActivityFragment.adHelper.showInterAd();
                    mLoadingIndicator.setVisibility(View.GONE);
                } else {
                    Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                    intent.putExtra(ImageActivity.JOKE_KEY, result);
                    mLoadingIndicator.setVisibility(View.GONE);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(MainActivity.this, "Loading failed!!!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                mLoadingIndicator.setVisibility(View.GONE);
            }
        });
        asyncTask.execute();
    }

    public interface AdHelper {
        void setInterAdListener(String result, Context context);
        void showInterAd();
    }

}
