package com.sptas.sptasv2.Lecturer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.sptas.sptasv2.Common.Common;
import com.sptas.sptasv2.R;

public class LecturerFragment extends Fragment {

    View myFragment;
    TextView txt_name, txt_email, txt_phone, txt_class, txt_nickName, txt_score;

    FirebaseDatabase database;

    public static LecturerFragment newInstance() {
        LecturerFragment lecturerFragment = new LecturerFragment();
        return lecturerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Firebase
        database = FirebaseDatabase.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.lecturer_profile_layout, container, false);

        loadStudentProfile();

        return myFragment;
    }

    private void loadStudentProfile() {

        txt_score = myFragment.findViewById(R.id.txt_score);
        txt_name = myFragment.findViewById(R.id.txt_name);
        txt_email = myFragment.findViewById(R.id.txt_email);
        txt_nickName = myFragment.findViewById(R.id.txt_nickName);


        txt_name.setText(Common.currentUser.getUserName().toUpperCase());
        txt_email.setText(Common.currentUser.getEmail());
        txt_nickName.setText(Common.currentUser.getnickName());

    }

}
