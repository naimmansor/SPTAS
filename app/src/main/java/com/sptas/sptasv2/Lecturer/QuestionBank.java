package com.sptas.sptasv2.Lecturer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sptas.sptasv2.Model.Test;
import com.sptas.sptasv2.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank extends Fragment {

    public static String userId;
    View myFragment;
    Button btnSave;
    EditText edtName;
    DatabaseReference databaseReference;
    ListView listViewUsers;
    List<Test> tests;

    public static QuestionBank newInstance() {
        QuestionBank questionBank = new QuestionBank();
        return questionBank;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.lecturer_activity_question_bank, container, false);

        tests = new ArrayList<Test>();
        databaseReference = FirebaseDatabase.getInstance().getReference("QuestionBank");

        btnSave = (Button) myFragment.findViewById(R.id.btnSave);
        edtName = (EditText) myFragment.findViewById(R.id.edtName);
        listViewUsers = (ListView) myFragment.findViewById(R.id.listViewUsers);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();

                if (TextUtils.isEmpty(userId)) {
                    //save
                    String id = databaseReference.push().getKey();
                    Test test = new Test(id, name);
                    databaseReference.child(id).setValue(test);

                    Toast.makeText(getActivity(), "Test Created Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    //update
                    databaseReference.child(userId).child("name").setValue(name);

                    Toast.makeText(getActivity(), "Test Updated Successfully!", Toast.LENGTH_SHORT).show();
                }

                edtName.setText(null);
            }
        });

        return myFragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tests.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Test test = postSnapshot.getValue(Test.class);
                    tests.add(test);
                }

                TestList testAdapter = new TestList(getActivity(), tests, databaseReference, edtName);
                listViewUsers.setAdapter(testAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
