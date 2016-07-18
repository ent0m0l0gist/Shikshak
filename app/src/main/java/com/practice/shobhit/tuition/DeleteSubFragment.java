package com.practice.shobhit.tuition;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DeleteSubFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.delete_sub_fragment, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.subject_list_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

//        Subject[] arr = new Subject[2];
//
//        arr[0] = new Subject("Phy", "7", "10");
//        arr[1] = new Subject("Chem", "10", "12");
//
//        RecyclerView.Adapter mAdapter = new MyAdapter(arr);
//        mRecyclerView.setAdapter(mAdapter);

        FetchSubjectsOfTeacherAsyncTask asyncTask = new FetchSubjectsOfTeacherAsyncTask(rootView, getActivity(), mRecyclerView);
        asyncTask.execute();


        return rootView;
    }


}
