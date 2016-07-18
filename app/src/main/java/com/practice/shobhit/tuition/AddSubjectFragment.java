package com.practice.shobhit.tuition;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;


public class AddSubjectFragment extends Fragment {

    public View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.add_subject_fragment, container, false);

        AutoCompleteTextView autoCompleteTextViewSubject = (AutoCompleteTextView) rootView.findViewById(R.id.edit_text_subject);
        ArrayAdapter<String> subjectListAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.subject_suggestion_list_element,
                getResources().getStringArray(R.array.subjects_string_array));

        autoCompleteTextViewSubject.setAdapter(subjectListAdapter);


        Button button = (Button)rootView.findViewById(R.id.add_subject_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSubjectAsyncTask asyncTask = new AddSubjectAsyncTask(rootView, getActivity());

                EditText etFrom = (EditText) rootView.findViewById(R.id.starting_class1);
                EditText etTo = (EditText) rootView.findViewById(R.id.highest_class1);
                AutoCompleteTextView etSubject = (AutoCompleteTextView)rootView.findViewById(R.id.edit_text_subject);

                String subjectToAdd = etSubject.getText().toString();
                String startClass = etFrom.getText().toString();
                String endClass = etTo.getText().toString();

                TeacherStore storer = new TeacherStore(getActivity());
                Teacher loggedInTeacher = storer.getLoggedInTeacher();
                String email = loggedInTeacher.email;

                String[] params = {subjectToAdd, startClass, endClass, email};

                asyncTask.execute(params);

            }
        });

        return this.rootView;
    }
}
