package com.sptas.sptasv2.Lecturer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    DatabaseReference databaseReference;
    ListView listViewUsers;
    List<Test> tests;
    private FloatingActionButton btnAdd;
    private EditText edtName;

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
        databaseReference = FirebaseDatabase.getInstance().getReference("Question_Bank");

        // components from main.xml
        btnAdd = (FloatingActionButton) myFragment.findViewById(R.id.buttonPrompt);
        edtName = (EditText) myFragment.findViewById(R.id.edtName);

        listViewUsers = (ListView) myFragment.findViewById(R.id.listViewUsers);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.lecturer_create_question, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.edtName);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        String name = userInput.getText().toString();

                                        //save
                                        String uid = databaseReference.push().getKey();
                                        Test test = new Test(uid, name);
                                        databaseReference.child(uid).setValue(test);

                                        Toast.makeText(getActivity(), "Test Created Successfully!", Toast.LENGTH_SHORT).show();

                                        userInput.setText(null);
                                    }
                                })
                        .setNegativeButton("Discard",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
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
