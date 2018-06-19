package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.example.android.andlib.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by lgpc on 2018-06-10.
 */

public class EndpointAsyncTask extends AsyncTask<Context, Void, String> {
    private static final String JOKE_KEY = "JOKE";
    private static MyApi myApiService = null;
    private Context context;
    private SimpleIdlingResource mIdlingResource;

    public EndpointAsyncTask(SimpleIdlingResource idlingResource){
        this.mIdlingResource = idlingResource;
    }

    @Override
    protected String doInBackground(Context... context) {
        if(myApiService == null){
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        this.context = context[0];

        try{
            if(mIdlingResource != null)
                mIdlingResource.setIsIdleNow(false);
            return myApiService.getJoke().execute().getData();
        }
        catch (IOException e){
            return e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(String joke) {
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JOKE_KEY, joke);
        context.startActivity(intent);
        if(mIdlingResource != null)
            mIdlingResource.setIsIdleNow(true);
    }
}
