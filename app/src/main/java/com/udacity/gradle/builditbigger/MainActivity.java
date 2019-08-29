package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {

    public final static String TEST_AD_ID = "ca-app-pub-3940256099942544/1033173712";
    private static InterstitialAd mInterstitialAd;
    private static boolean sIsFree;

    private static ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getPackageName().equals("com.udacity.gradle.builditbigger.free")) {
            sIsFree = true;
            initInterstitialAd();
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

    public static void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    public void tellJoke(View view) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        new EndpointsAsyncTask().execute(this);
    }

    public static boolean getIsFree() {
        return sIsFree;
    }

    private void initInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(TEST_AD_ID);
        loadNewAd();
    }

    public static void loadNewAd() {
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
    }

    public static void showAd() {
        if(mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public static void setAdListener(AdListener listener) {
        if(mInterstitialAd != null) {
            mInterstitialAd.setAdListener(listener);
        }
    }

}
