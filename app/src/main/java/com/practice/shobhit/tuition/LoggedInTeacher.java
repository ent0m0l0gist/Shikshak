package com.practice.shobhit.tuition;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class LoggedInTeacher extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = LoggedInTeacher.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_teacher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TeacherStore storer = new TeacherStore(this);
        if(!storer.isLoggedIn()){
            startActivity(new Intent(this, LoginActivity.class));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_holder, new TeacherProfile()).commit();

    }

    //Close the application on press of back button:
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.logged_in_teacher, menu);
//
        TextView nametv = (TextView)findViewById(R.id.name_navigation_drawer);
        TeacherStore storer = new TeacherStore(this);
        nametv.setText(storer.getLoggedInTeacher().name);
//
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentManager fm = getFragmentManager();

        if (id == R.id.nav_Profile) {
            fm.beginTransaction().replace(R.id.fragment_holder, new TeacherProfile()).commit();
        }

        else if (id == R.id.nav_addSubject) {
            fm.beginTransaction().replace(R.id.fragment_holder, new AddSubjectFragment()).commit();
        } else if (id == R.id.nav_removeSubject) {
            fm.beginTransaction().replace(R.id.fragment_holder, new DeleteSubFragment()).commit();

        } else if (id == R.id.nav_logOut) {
            TeacherStore storer = new TeacherStore(this);
            storer.clearSharedPref();
            startActivity(new Intent(this, LoginActivity.class));
            LoggedInTeacher.this.finish();
        } else if (id == R.id.nav_editProfile){
            fm.beginTransaction().replace(R.id.fragment_holder, new EditProfileFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}