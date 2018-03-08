package com.sptas.sptasv2.Student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sptas.sptasv2.R;
import com.sptas.sptasv2.Student.Common.Common;
import com.sptas.sptasv2.Student.Model.Ranking;

public class ProfileFragment extends Fragment {

    View myFragment;
    TextView txt_name, txt_email, txt_phone, txt_class, txt_sv, txt_score;

    Ranking score;

    FirebaseDatabase database;
    DatabaseReference ranking;

    public static ProfileFragment newInstance() {
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Firebase
        database = FirebaseDatabase.getInstance();
        ranking = database.getReference("Ranking");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.student_profile_layout, container, false);

        loadStudentProfile();

        return myFragment;
    }

    private void loadStudentProfile() {

        txt_score = (TextView) myFragment.findViewById(R.id.txt_score);
        txt_name = (TextView) myFragment.findViewById(R.id.txt_name);
        txt_email = (TextView) myFragment.findViewById(R.id.txt_email);
        txt_phone = (TextView) myFragment.findViewById(R.id.txt_phone);
        txt_class = (TextView) myFragment.findViewById(R.id.txt_class);
        txt_sv = (TextView) myFragment.findViewById(R.id.txt_sv);


        txt_name.setText(Common.currentUser.getUserName().toUpperCase());
        txt_email.setText(Common.currentUser.getEmail());
        txt_phone.setText(Common.currentUser.getNoPhone());
        txt_class.setText(Common.currentUser.getYear());
        txt_sv.setText(Common.currentUser.getSv());

    }
}
