package com.practice.shobhit.tuition;

import android.content.Context;
import android.content.SharedPreferences;

public class TeacherStore {
    public static final String SP_NAME = "TeacherDetails";
    SharedPreferences localDb;
    SharedPreferences.Editor spEditor;

    public TeacherStore(Context context){
        localDb = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeDetailsOfTeacher(Teacher teacher){
        if (localDb != null)
            spEditor = localDb.edit();

        spEditor.putString("name", teacher.name);
        spEditor.putString("email", teacher.email);
        spEditor.putString("password", teacher.password);
        spEditor.putString("address", teacher.address);
        spEditor.putString("contact", teacher.contact);

        spEditor.apply();
    }

    public Teacher getLoggedInTeacher(){
        String name = localDb.getString("name", "");
        String email = localDb.getString("email", "");
        String password = localDb.getString("password", "");
        String contact = localDb.getString("contact", "");
        String address = localDb.getString("address", "");

        return new Teacher(name, email, password, address, contact);
    }

    public void setLoggedinStatus(boolean loggedIn) {
        spEditor = localDb.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.apply();
    }

    public boolean isLoggedIn(){
        return localDb.getBoolean("loggedIn", false);
    }

    public void clearSharedPref(){
        spEditor = localDb.edit();
        spEditor.clear();
        spEditor.apply();

        this.setLoggedinStatus(false);
    }
}
