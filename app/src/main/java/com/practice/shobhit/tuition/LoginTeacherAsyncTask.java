package com.practice.shobhit.tuition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginTeacherAsyncTask extends AsyncTask<String, Void, String> {
    private static final String LOG_TAG = LoginTeacherAsyncTask.class.getSimpleName();
    Context callerContext;

    String enteredEmail = null;

    public LoginTeacherAsyncTask(Context callerContext){
        this.callerContext = callerContext;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        Log.v(LOG_TAG, "AsyncTask executing");

        enteredEmail = params[0];

        final String BASE_URL = "http://".concat(Temp.IP_ADDRESS).concat("/TuitionPHPScripts/signInTeacher.php?");
        final String EMAIL_PARAM = "email";
        final String PASS_PARAM = "pass";

        String serverResponse = null;

        try{
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(EMAIL_PARAM, params[0])
                    .appendQueryParameter(PASS_PARAM, params[1])
                    .build();

            URL url = new URL(builtUri.toString());

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
    protected void onPostExecute(String serverResponse) {
        try {
            Log.v(LOG_TAG, serverResponse);
            JSONObject jsonObject = new JSONObject(serverResponse);
            int status = jsonObject.getInt("status");
            Toast toast;
            switch (status){
                case 0:
                    toast = Toast.makeText(callerContext, "E-Mail not registered", Toast.LENGTH_LONG);
                    toast.show();
                    break;

                case 1:
                    toast = Toast.makeText(callerContext, "Password incorrect", Toast.LENGTH_LONG);
                    toast.show();
                    break;

                case 2:
                    String teacherName = jsonObject.getString("name");
                    String teacherAddress = jsonObject.getString("address");
                    String teacherContact = jsonObject.getString("contact");

                    Intent intent = new Intent(callerContext, LoggedInTeacher.class);

                    TeacherStore storer = new TeacherStore(callerContext);
                    storer.storeDetailsOfTeacher(new Teacher(teacherName, enteredEmail, "", teacherAddress, teacherContact));
                    storer.setLoggedinStatus(true);

                    Activity callerActivity = (Activity) callerContext;
                    callerActivity.finish();

                    callerContext.startActivity(intent);
                    break;

                default:
                    Log.e(LOG_TAG, "INVALID STATUS FROM SERVER");
            }
        }catch(Exception e){
            Log.e(LOG_TAG, e.getLocalizedMessage());
        }
    }
}
