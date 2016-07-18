package com.practice.shobhit.tuition;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class EditProfileFragment extends Fragment{

    EditText nameEt;
    EditText contactNumberEt;
    EditText addressEt;

    public View rootView;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.edit_profile, container, false);
        nameEt = (EditText) rootView.findViewById(R.id.etNameEP);
        contactNumberEt = (EditText) rootView.findViewById(R.id.etContactEP);
        addressEt = (EditText) rootView.findViewById(R.id.etAddressEP);

        TeacherStore storer = new TeacherStore(getActivity());
        Teacher loggedInTeacher = storer.getLoggedInTeacher();


        nameEt.setText(loggedInTeacher.name);
   //     emailEt.setText(loggedInTeacher.email);
        contactNumberEt.setText(loggedInTeacher.contact);
        addressEt.setText(loggedInTeacher.address);

        Button button = (Button) rootView.findViewById(R.id.editProfile_save_changes_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEt = (EditText) rootView.findViewById(R.id.etNameEP);
                contactNumberEt = (EditText) rootView.findViewById(R.id.etContactEP);
                addressEt = (EditText) rootView.findViewById(R.id.etAddressEP);

                Teacher teacher = new Teacher(nameEt.getText().toString(), new TeacherStore(getActivity()).getLoggedInTeacher().email, "", addressEt.getText().toString(), contactNumberEt.getText().toString());

                TeacherStore storer = new TeacherStore(getActivity());

//                loggedInTeacher.name = teacher.name;
//                loggedInTeacher.contact = teacher.contact;
//                loggedInTeacher.address = teacher.address;

                storer.storeDetailsOfTeacher(teacher);

                EditProfileAsyncTask asyncTask = new EditProfileAsyncTask(getActivity(), teacher);
                asyncTask.execute();
            }
        });
        return  rootView;
    }
}
