package com.sptas.sptasv2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StudentFragment extends Fragment {

    View myFragment;

        RecyclerView listStudent;
        RecyclerView.LayoutManager layoutManager;

    public static StudentFragment newInstance(){
        StudentFragment studentFragment = new StudentFragment();
        return studentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_student, container, false);

        listStudent = (RecyclerView)myFragment.findViewById(R.id.listStudent);
        listStudent.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listStudent.setLayoutManager(layoutManager);

        return myFragment;
    }
}
