package com.practice.shobhit.tuition;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

class RegisterTeacherAsyncTask extends AsyncTask<Void, Void, String> {
    private static final String LOG_TAG = RegisterTeacherAsyncTask.class.getSimpleName();
    Teacher teacher;
    Context callerContext;

    public RegisterTeacherAsyncTask(Teacher teacher, Context callerContext){
        this.teacher = teacher;
        this.callerContext = callerContext;
    }

    @Override
    protected String doInBackground(Void... params) {
        Geocoder geocoder = new Geocoder(this.callerContext);
        List<Address> addresses;

        double lat = 0;
        double lng = 0;

        try{
            addresses= geocoder.getFromLocationName(teacher.address,5);

            if (addresses==null) {
                return null;
            }

            Address address = addresses.get(0);

            lat= address.getLatitude();
            lng= address.getLongitude();

        }catch (IOException err){
            err.printStackTrace();
        }


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        final String BASE_URL = "http://".concat(Temp.IP_ADDRESS).concat("/TuitionPHPScripts/registerTeacher.php?");
        final String NAME_PARAM = "name";
        final String EMAIL_PARAM = "email";
        final String PASS_PARAM = "pass";
        final String ADDRESS_PARAM = "add";
        final String LONGI_PARAM = "ln";
        final String LATI_PARAM = "lt";
        final String CONTACT_PARAM = "ct";

        String serverResponse = null;


        try{
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(NAME_PARAM, teacher.name)
                    .appendQueryParameter(EMAIL_PARAM, teacher.email)
                    .appendQueryParameter(PASS_PARAM, teacher.password)
                    .appendQueryParameter(CONTACT_PARAM, teacher.contact)
                    .appendQueryParameter(ADDRESS_PARAM, teacher.address)
                    .appendQueryParameter(LATI_PARAM, Double.toString(lat))
                    .appendQueryParameter(LONGI_PARAM, Double.toString(lng))
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
    protected void onPostExecute(String serverResponse) {

        try {
            Log.v(LOG_TAG, serverResponse);
            JSONObject jsonObject = new JSONObject(serverResponse);
            int status = jsonObject.getInt("status");
            if(status == 0){
                Toast toast = Toast.makeText(callerContext, "E-Mail ID already registered.", Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(callerContext, "User registered successfully", Toast.LENGTH_LONG);
                toast.show();
                NavUtils.navigateUpFromSameTask((Activity) callerContext);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
