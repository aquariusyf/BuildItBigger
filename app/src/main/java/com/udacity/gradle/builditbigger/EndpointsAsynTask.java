package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.android.imageactivity.ImageActivity;
import com.google.android.gms.ads.AdListener;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {

    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }
        if(params != null && params.length != 0) {
            context = params[0];
        }

        try {
            return myApiService.getJokeFromEndPoint().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(final String result) {
        if(MainActivity.getIsFree()) {
            MainActivity.setAdListener(new AdListener(){
                @Override
                public void onAdClosed() {
                    if(context != null) {
                        Intent intent = new Intent(context, ImageActivity.class);
                        intent.putExtra(ImageActivity.JOKE_KEY, result);
                        MainActivity.hideLoadingIndicator();
                        context.startActivity(intent);
                    }
                    MainActivity.loadNewAd();
                }
            });
            MainActivity.showAd();
        } else {
            if(context != null) {
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra(ImageActivity.JOKE_KEY, result);
                MainActivity.hideLoadingIndicator();
                context.startActivity(intent);
            }
        }
    }
}
