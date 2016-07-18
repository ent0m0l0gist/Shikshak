package com.practice.shobhit.tuition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity{

    EditText etEmail;
    EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TeacherStore storer = new TeacherStore(this);
        if(storer.isLoggedIn()){
            startActivity(new Intent(this, LoggedInTeacher.class));
        }

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPassword);
    }

    public void registerNewUser(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void validateLogin(View view) {
        String email = etEmail.getText().toString();
        String pwd = etPass.getText().toString();
        LoginTeacherAsyncTask asyncTask = new LoginTeacherAsyncTask(this);
        asyncTask.execute(email, pwd);
    }
}
