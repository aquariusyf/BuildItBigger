package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.imageactivity.ImageActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public final static String TEST_AD_ID = "ca-app-pub-3940256099942544/1033173712";
    private static InterstitialAd mInterstitialAd;

    public static MainActivity.AdHelper adHelper;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdView mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        initInterstitialAd();

        adHelper = new MainActivity.AdHelper() {
            @Override
            public void setInterAdListener(String result, Context context) {
                setAdListener(result, context);
            }

            @Override
            public void showInterAd() {
                showAd();
            }
        };

        return root;
    }

    private void initInterstitialAd() {
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(TEST_AD_ID);
        loadNewAd();
    }

    private static void loadNewAd() {
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
    }

    private static void showAd() {
        mInterstitialAd.show();
    }

    private static void setAdListener(final String result, final Context context) {
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra(ImageActivity.JOKE_KEY, result);
                context.startActivity(intent);
                loadNewAd();
            }
        });
    }

}