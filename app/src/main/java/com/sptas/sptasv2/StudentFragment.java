package com.sptas.sptasv2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sptas.sptasv2.Model.User;
import com.sptas.sptasv2.ViewHolder.StudentViewHolder;

public class StudentFragment extends Fragment {

    View myFragment;

        RecyclerView listStudent;
        RecyclerView.LayoutManager layoutManager;
        FirebaseRecyclerAdapter<User, StudentViewHolder> adapter;

        FirebaseDatabase database;
        DatabaseReference student_profile;

    public static StudentFragment newInstance(){
        StudentFragment studentFragment = new StudentFragment();
        return studentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_student, container, false);

        // Firebase
        database = FirebaseDatabase.getInstance();
        student_profile = database.getReference("Users");

        //View
        listStudent = (RecyclerView)myFragment.findViewById(R.id.listStudent);
        listStudent.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listStudent.setLayoutManager(layoutManager);

        loadStudentProfile();

        return myFragment;
    }

    private void loadStudentProfile() {
        adapter = new FirebaseRecyclerAdapter<User, StudentViewHolder>(
                User.class,
                R.layout.student_profile_layout,
                StudentViewHolder.class,
                student_profile
        ) {
            @Override
            protected void populateViewHolder(StudentViewHolder viewHolder, User model, int position) {
                viewHolder.txt_name.setText(model.getUserName());
                viewHolder.txt_email.setText(model.getEmail());
                viewHolder.txt_phone.setText(model.getNoPhone());
                viewHolder.txt_class.setText(model.getYear());
                viewHolder.txt_sv.setText(model.getSv());
            }
        };
        adapter.notifyDataSetChanged();
        listStudent.setAdapter(adapter);
    }
}
