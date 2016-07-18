package com.practice.shobhit.tuition;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FetchSubjectsOfTeacherAsyncTask extends AsyncTask<Void, Void, List<Subject>> {
    private static final String LOG_TAG = FetchSubjectsOfTeacherAsyncTask.class.getSimpleName();
    private View rootView;
    private Context callerContext;
    private RecyclerView mRecyclerView;

    public FetchSubjectsOfTeacherAsyncTask(View rootView, Context callerContext, RecyclerView mRecyclerView){
        this.rootView = rootView;
        this.callerContext = callerContext;
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    protected List<Subject> doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        final String BASE_URL = "http://".concat(Temp.IP_ADDRESS).concat("/TuitionPHPScripts/fetchSubjects.php?");
        final String SUBJECT_PARAM = "email";

        TeacherStore storer = new TeacherStore(callerContext);
        final String userEmail = storer.getLoggedInTeacher().email;

        String serverResponse = null;
        try{
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(SUBJECT_PARAM, userEmail)
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
        return this.parseJson(serverResponse);

    }

        private List<Subject> parseJson(String serverResponse) {

            if(serverResponse == null)
                return null;

        List<Subject> subjList = null;
        try {
            JSONObject masterJsonObj = new JSONObject(serverResponse);

            int numOfSubjects = masterJsonObj.getInt("count");
            JSONArray subjJsonArray = masterJsonObj.getJSONArray("array");

            subjList = new ArrayList<>();

            for(int i = 0; i<numOfSubjects; i++){
                JSONObject currentSubject = subjJsonArray.getJSONObject(i);

                String fromClass = currentSubject.getString("fromClass");
                String toClass = currentSubject.getString("toClass");
                String subjName = currentSubject.getString("subjectName");

                Subject currentSubjectObject = new Subject(subjName, fromClass, toClass);
                subjList.add(currentSubjectObject);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return subjList;
    }

    @Override
    protected void onPostExecute(List<Subject> subjects) {
        if(subjects == null)
            return;

        mRecyclerView.setAdapter(new RecyclerViewCustomAdapter(subjects, callerContext));
    }

}
