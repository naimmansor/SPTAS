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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sptas.sptasv2.Model.Question;
import com.sptas.sptasv2.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank extends Fragment {

    View myFragment;
    DatabaseReference databaseReference;
    ListView listViewUsers;
    List<Question> questions;
    private FloatingActionButton btnAdd;
    private EditText edtQuestion, edtAnswerA, edtAnswerB, edtAnswerC, edtAnswerD, edtCorrAnswer;
    private RadioGroup edtCategoryId, edtIsImageQuestion;

    private String image, cid = "";

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

        questions = new ArrayList<Question>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Questions");

        // components from main.xml
        btnAdd = myFragment.findViewById(R.id.buttonPrompt);
        edtCategoryId = myFragment.findViewById(R.id.edtCategoryId);
        edtQuestion = myFragment.findViewById(R.id.edtQuestion);
        edtAnswerA = myFragment.findViewById(R.id.edtAnswerA);
        edtAnswerB = myFragment.findViewById(R.id.edtAnswerB);
        edtAnswerC = myFragment.findViewById(R.id.edtAnswerC);
        edtAnswerD = myFragment.findViewById(R.id.edtAnswerD);
        edtCorrAnswer = myFragment.findViewById(R.id.edtCorrAnswer);
        edtIsImageQuestion = myFragment.findViewById(R.id.edtIsImageQuestion);
        listViewUsers = myFragment.findViewById(R.id.listViewUsers);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.lecturer_popup_question, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final RadioButton categoryIdDS = promptsView
                        .findViewById(R.id.edtCategoryIdDS);

                final EditText question = promptsView
                        .findViewById(R.id.edtQuestion);

                final EditText questionAnswerA = promptsView
                        .findViewById(R.id.edtAnswerA);

                final EditText questionAnswerB = promptsView
                        .findViewById(R.id.edtAnswerB);

                final EditText questionAnswerC = promptsView
                        .findViewById(R.id.edtAnswerC);

                final EditText questionAnswerD = promptsView
                        .findViewById(R.id.edtAnswerD);

                final EditText correctAnswer = promptsView
                        .findViewById(R.id.edtCorrAnswer);

                final RadioButton isImageQuestionTrue = promptsView
                        .findViewById(R.id.edtIsImageQuestionTrue);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text

                                        //condition checkbox
                                        if (categoryIdDS.isChecked()) {
                                            cid = "01";
                                        } else {
                                            cid = "02";
                                        }

                                        String questionName = question.getText().toString();
                                        String answerA = questionAnswerA.getText().toString();
                                        String answerB = questionAnswerB.getText().toString();
                                        String answerC = questionAnswerC.getText().toString();
                                        String answerD = questionAnswerD.getText().toString();
                                        String corrAnswer = correctAnswer.getText().toString();

                                        //condition checkbox
                                        if (isImageQuestionTrue.isChecked()) {
                                            image = "true";
                                        } else {
                                            image = "false";
                                        }

                                        //save
                                        String uid = databaseReference.push().getKey();
                                        Question test = new Question(uid, questionName, answerA, answerB, answerC, answerD, corrAnswer, cid, "", image);
                                        databaseReference.child(uid).setValue(test);

                                        Toast.makeText(getActivity(), "Question Created Successfully!", Toast.LENGTH_SHORT).show();

                                        question.setText(null);
                                    }
                                })
                        .setNegativeButton("Cancel",
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
                questions.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Question question = postSnapshot.getValue(Question.class);
                    questions.add(question);
                }

                QuestionList questionAdapter = new QuestionList(getActivity(), questions, databaseReference, edtCategoryId, edtQuestion, edtAnswerA, edtAnswerB, edtAnswerC, edtAnswerD, edtCorrAnswer, edtIsImageQuestion);
                listViewUsers.setAdapter(questionAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
