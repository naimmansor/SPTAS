package com.sptas.sptasv2.Lecturer;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.sptas.sptasv2.Model.Question;
import com.sptas.sptasv2.R;

import java.util.List;

/**
 * Created by Na'im Mansor on 16-Mar-18.
 */

public class QuestionList extends ArrayAdapter<Question> {

    public static String userId;
    DatabaseReference databaseReference;
    EditText edtQuestion, edtAnswerA, edtAnswerB, edtAnswerC, edtAnswerD, edtCorrAnswer;
    RadioGroup edtCategoryId, edtIsImageQuestion;
    private Activity context;
    private List<Question> questions;

    private String image, cid = "";

    public QuestionList(@NonNull Activity context, List<Question> questions, DatabaseReference databaseReference, RadioGroup edtCategoryId, EditText edtQuestion, EditText edtAnswerA, EditText edtAnswerB, EditText edtAnswerC, EditText edtAnswerD, EditText edtCorrAnswer, RadioGroup edtIsImageQuestion) {
        super(context, R.layout.lecturer_question_bank_layout, questions);
        this.context = context;
        this.questions = questions;
        this.databaseReference = databaseReference;
        this.edtCategoryId = edtCategoryId;
        this.edtQuestion = edtQuestion;
        this.edtAnswerA = edtAnswerA;
        this.edtAnswerB = edtAnswerB;
        this.edtAnswerC = edtAnswerC;
        this.edtAnswerD = edtAnswerD;
        this.edtCorrAnswer = edtCorrAnswer;
        this.edtIsImageQuestion = edtIsImageQuestion;
    }

    public View getView(int pos, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.lecturer_question_bank_layout, null, true);

        TextView categoryId = listViewItem.findViewById(R.id.categoryId);
        Button btnDelete = listViewItem.findViewById(R.id.btnDelete);
        Button btnUpdate = listViewItem.findViewById(R.id.btnUpdate);
        Button btnView = listViewItem.findViewById(R.id.btnView);

        final Question question = questions.get(pos);
        if (question.getCategoryId().equals("01")) {
            categoryId.setText("DS");
        } else if (question.getCategoryId().equals("02")) {
            categoryId.setText("SE");
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child(question.getId()).removeValue();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.lecturer_popup_question, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final RadioGroup categoryId = promptsView
                        .findViewById(R.id.edtCategoryId);

                final RadioButton categoryIdDS = promptsView
                        .findViewById(R.id.edtCategoryIdDS);

                final EditText txtquestion = promptsView
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

                final RadioGroup isImageQuestion = promptsView
                        .findViewById(R.id.edtIsImageQuestion);

                final RadioButton isImageQuestionTrue = promptsView
                        .findViewById(R.id.edtIsImageQuestionTrue);

                if (question.getCategoryId().equals("02")) {
                    categoryId.check(R.id.edtCategoryIdSE);
                } else {
                    categoryId.check(R.id.edtCategoryIdDS);
                }

                txtquestion.setText(question.getQuestion());
                questionAnswerA.setText(question.getAnswerA());
                questionAnswerB.setText(question.getAnswerB());
                questionAnswerC.setText(question.getAnswerC());
                questionAnswerD.setText(question.getAnswerD());
                correctAnswer.setText(question.getCorrectAnswer());

                if (question.getIsImageQuestion().equals("true")) {
                    isImageQuestion.check(R.id.edtIsImageQuestionTrue);
                } else {
                    isImageQuestion.check(R.id.edtIsImageQuestionFalse);
                }

                userId = question.getId();

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Update",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text

                                        //condition radio button
                                        if (categoryIdDS.isChecked()) {
                                            cid = "01";
                                        } else {
                                            cid = "02";
                                        }

                                        String questionName = txtquestion.getText().toString();
                                        String answerA = questionAnswerA.getText().toString();
                                        String answerB = questionAnswerB.getText().toString();
                                        String answerC = questionAnswerC.getText().toString();
                                        String answerD = questionAnswerD.getText().toString();
                                        String corrAnswer = correctAnswer.getText().toString();

                                        //condition radio button
                                        if (isImageQuestionTrue.isChecked()) {
                                            image = "true";
                                        } else {
                                            image = "false";
                                        }

                                        //update
                                        databaseReference.child(userId).child("categoryId").setValue(cid);
                                        databaseReference.child(userId).child("question").setValue(questionName);
                                        databaseReference.child(userId).child("answerA").setValue(answerA);
                                        databaseReference.child(userId).child("answerB").setValue(answerB);
                                        databaseReference.child(userId).child("answerC").setValue(answerC);
                                        databaseReference.child(userId).child("answerD").setValue(answerD);
                                        databaseReference.child(userId).child("correctAnswer").setValue(corrAnswer);
                                        databaseReference.child(userId).child("isImageQuestion").setValue(image);

                                        Toast.makeText(context, "Question Updated Successfully!", Toast.LENGTH_SHORT).show();

                                        txtquestion.setText(null);
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

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.lecturer_popup_view_question, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final TextView categoryId = promptsView
                        .findViewById(R.id.categoryId);

                final TextView txtQuestion = promptsView
                        .findViewById(R.id.txtQuestion);

                final TextView txtAnswerA = promptsView
                        .findViewById(R.id.txtAnswerA);

                final TextView txtAnswerB = promptsView
                        .findViewById(R.id.txtAnswerB);

                final TextView txtAnswerC = promptsView
                        .findViewById(R.id.txtAnswerC);

                final TextView txtAnswerD = promptsView
                        .findViewById(R.id.txtAnswerD);

                final TextView corrAnswer = promptsView
                        .findViewById(R.id.corrAnswer);

                final TextView IsImageQuestion = promptsView
                        .findViewById(R.id.IsImageQuestion);

                categoryId.setText(question.getCategoryId());
                txtQuestion.setText(question.getQuestion());
                txtAnswerA.setText(question.getAnswerA());
                txtAnswerB.setText(question.getAnswerB());
                txtAnswerC.setText(question.getAnswerC());
                txtAnswerD.setText(question.getAnswerD());
                corrAnswer.setText(question.getCorrectAnswer());
                IsImageQuestion.setText(question.getIsImageQuestion());
                userId = question.getId();

                // set dialog message
                alertDialogBuilder
                        .setNegativeButton("Back",
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

        return listViewItem;
    }
}
