package com.practice.shobhit.tuition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText nameEt;
    EditText passwordEt;
    EditText emailEt;
    EditText contactNumberEt;
    EditText addressEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    //on click of register button
    public void registerUser(View view){
        nameEt = (EditText) findViewById(R.id.etNewName);
        passwordEt = (EditText) findViewById(R.id.etNewPassword);
        emailEt = (EditText) findViewById(R.id.etNewEmail);
        contactNumberEt = (EditText) findViewById(R.id.et_contact_number);
        addressEt = (EditText) findViewById(R.id.et_address);

        Teacher teacher = new Teacher(nameEt.getText().toString(), emailEt.getText().toString(), passwordEt.getText().toString(),addressEt.getText().toString(),contactNumberEt.getText().toString());
        if(this.isValid(teacher)){
            RegisterTeacherAsyncTask registerTask = new RegisterTeacherAsyncTask(teacher, this);
            registerTask.execute();
        }
    }

    private boolean isValid(Teacher teacher) {

        //E:Mail validation:
        final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(teacher.email);

        if(!matcher.find()) {
            Toast toast = Toast.makeText(this, "E-mail address is not valid", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }

        //Password validation:
        if(teacher.password.length() < 5){
            Toast toast = Toast.makeText(this, "Password must be at least 5 characters long", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }

        //Name validation:
        if(teacher.name.length() == 0){
            Toast toast = Toast.makeText(this, "Please enter your name", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }

        return true;
    }
}
