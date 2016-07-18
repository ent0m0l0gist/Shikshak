package com.practice.shobhit.tuition;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Abc on 1/16/2016.
 */
public class RemoveSubjectAsyncTask extends AsyncTask<String[],Void,String> {
    private Context callerContext;
    public  RemoveSubjectAsyncTask(Context context){
        this.callerContext=context;
    }
    private static final String LOG_TAG=RemoveSubjectAsyncTask.class.getSimpleName();
    @Override
    protected String doInBackground(String[]... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        Log.v(LOG_TAG, "AsyncTask executing");

        final String BASE_URL = "http://".concat(Temp.IP_ADDRESS).concat("/TuitionPHPScripts/deleteSubject.php?");
        final String SUBJECT_PARAM = "subject";
        final String FROM_PARAM = "from";
        final String TO_PARAM = "to";
        final String EMAIL_PARAM = "emailId";

        String serverResponse = null;
        try{
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(SUBJECT_PARAM, params[0][0])
                    .appendQueryParameter(FROM_PARAM, params[0][1])
                    .appendQueryParameter(TO_PARAM, params[0][2])
                    .appendQueryParameter(EMAIL_PARAM, params[0][3])
                    .build();

            URL url = new URL(builtUri.toString());

            Log.e(LOG_TAG, builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            if (inputStream == null)
                return null;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while((line = reader.readLine()) != null){
                buffer.append(line).append("\n");
            }
            serverResponse = buffer.toString();
            Log.v(LOG_TAG, serverResponse);

        }catch (Exception err){
            Log.e(LOG_TAG, err.getLocalizedMessage());
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing BufferedReader object", e);
                }
            }
        }
        return serverResponse;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast toast = Toast.makeText(callerContext,"Subject Removed",Toast.LENGTH_SHORT);
        toast.show();
    }
}
