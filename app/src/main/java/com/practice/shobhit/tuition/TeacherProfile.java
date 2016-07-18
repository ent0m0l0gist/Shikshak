package com.practice.shobhit.tuition;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TeacherProfile extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.teacherprofilefragment, container, false);

        TeacherStore storer = new TeacherStore(getActivity());
        Teacher loggedInTeacher = storer.getLoggedInTeacher();

        TextView tv =(TextView)rootView.findViewById(R.id.nameTextview);

        tv.setText(loggedInTeacher.name);

        return rootView;
    }
}

