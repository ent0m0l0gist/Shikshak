package com.practice.shobhit.tuition;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class EditProfileAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String LOG_TAG = EditProfileAsyncTask.class.getSimpleName();
    Context callerContext;
    Teacher teacher;

    public EditProfileAsyncTask(Context callerContext, Teacher teacher){
        this.callerContext = callerContext;
        this.teacher = teacher;
    }

    @Override
    protected String doInBackground(Void... params) {

        Geocoder geocoder = new Geocoder(this.callerContext);

        List<Address> addressList = null;
        double lat = 0;
        double lng = 0;

        try {
            addressList= geocoder.getFromLocationName(teacher.address, 5);


            if (addressList==null) {
                    return null;
            }

            Address address = addressList.get(0);

            lat= address.getLatitude();
            lng= address.getLongitude();

        }catch (Exception err){
            err.printStackTrace();
        }


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        final String BASE_URL = "http://".concat(Temp.IP_ADDRESS).concat("/TuitionPHPScripts/editProfile.php?");
        final String EMAIL_PARAM = "email";
        final String NAME_PARAM = "name";
        final String CONTACT_PARAM = "cnt";
        final String ADDRESS_PARAM = "add";
        final String LATITUDE_PARAM = "lat";
        final String LONGITUDE_PARAM = "lon";

        String serverResponse = null;
        try{
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(EMAIL_PARAM, teacher.email)
                    .appendQueryParameter(NAME_PARAM, teacher.name)
                    .appendQueryParameter(CONTACT_PARAM, teacher.contact)
                    .appendQueryParameter(ADDRESS_PARAM, teacher.address)
                    .appendQueryParameter(LATITUDE_PARAM, Double.toString(lat))
                    .appendQueryParameter(LONGITUDE_PARAM, Double.toString(lng))
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
    protected void onPostExecute(String s) {
        Toast toast = Toast.makeText(callerContext, "Changes Saved", Toast.LENGTH_SHORT);
        toast.show();

        super.onPostExecute(s);
    }



    public static JSONObject getLocationInfo(String address) throws JSONException {

        address = address.replaceAll(" ","%20");

        final String URL = "http://maps.google.com/maps/api/geocode/json?";
        final String ADDRESS_PARAM = "address";
        final String SENSOR_PARAM = "sensor";

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String serverResponse = null;

        try{
            Uri builtUri = Uri.parse(URL).buildUpon()
                    .appendQueryParameter(ADDRESS_PARAM, address)
                    .appendQueryParameter(SENSOR_PARAM, "false")
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

        JSONObject jsonObject = new JSONObject(serverResponse);
        return jsonObject;
    }

    private static List<Address> getAddrByWeb(JSONObject jsonObject){
        List<Address> res = new ArrayList<>();
        try
        {
            JSONArray array = (JSONArray) jsonObject.get("results");
            for (int i = 0; i < array.length(); i++)
            {
                Double lon = new Double(0);
                Double lat = new Double(0);
                String name = "";
                try
                {
                    lon = array.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                    lat = array.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                    name = array.getJSONObject(i).getString("formatted_address");
                    Address addr = new Address(Locale.getDefault());
                    addr.setLatitude(lat);
                    addr.setLongitude(lon);
                    addr.setAddressLine(0, name != null ? name : "");
                    res.add(addr);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();

                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();

        }

        return res;
    }
}
