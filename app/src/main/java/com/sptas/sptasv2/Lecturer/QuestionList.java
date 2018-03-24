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
    EditText edtCategoryId, edtQuestion, edtAnswerA, edtAnswerB, edtAnswerC, edtAnswerD, edtCorrAnswer, edtIsImageQuestion;
    private Activity context;
    private List<Question> questions;

    public QuestionList(@NonNull Activity context, List<Question> questions, DatabaseReference databaseReference, EditText edtCategoryId, EditText edtQuestion, EditText edtAnswerA, EditText edtAnswerB, EditText edtAnswerC, EditText edtAnswerD, EditText edtCorrAnswer, EditText edtIsImageQuestion) {
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

        TextView categoryId = (TextView) listViewItem.findViewById(R.id.categoryId);
        TextView txtQuestion = (TextView) listViewItem.findViewById(R.id.txtQuestion);
        TextView txtAnswerA = (TextView) listViewItem.findViewById(R.id.txtAnswerA);
        TextView txtAnswerB = (TextView) listViewItem.findViewById(R.id.txtAnswerB);
        TextView txtAnswerC = (TextView) listViewItem.findViewById(R.id.txtAnswerC);
        TextView txtAnswerD = (TextView) listViewItem.findViewById(R.id.txtAnswerD);
        TextView corrAnswer = (TextView) listViewItem.findViewById(R.id.corrAnswer);
        TextView IsImageQuestion = (TextView) listViewItem.findViewById(R.id.IsImageQuestion);
        Button btnDelete = (Button) listViewItem.findViewById(R.id.btnDelete);
        Button btnUpdate = (Button) listViewItem.findViewById(R.id.btnUpdate);
        Button btnView = (Button) listViewItem.findViewById(R.id.btnView);

        final Question question = questions.get(pos);
        categoryId.setText(question.getCategoryId());

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

                final EditText edtcategoryId = (EditText) promptsView
                        .findViewById(R.id.edtCategoryId);

                final EditText txtquestion = (EditText) promptsView
                        .findViewById(R.id.edtQuestion);

                final EditText questionAnswerA = (EditText) promptsView
                        .findViewById(R.id.edtAnswerA);

                final EditText questionAnswerB = (EditText) promptsView
                        .findViewById(R.id.edtAnswerB);

                final EditText questionAnswerC = (EditText) promptsView
                        .findViewById(R.id.edtAnswerC);

                final EditText questionAnswerD = (EditText) promptsView
                        .findViewById(R.id.edtAnswerD);

                final EditText correctAnswer = (EditText) promptsView
                        .findViewById(R.id.edtCorrAnswer);

                final EditText isImageQuestion = (EditText) promptsView
                        .findViewById(R.id.edtIsImageQuestion);

                edtcategoryId.setText(question.getCategoryId());
                txtquestion.setText(question.getQuestion());
                questionAnswerA.setText(question.getAnswerA());
                questionAnswerB.setText(question.getAnswerB());
                questionAnswerC.setText(question.getAnswerC());
                questionAnswerD.setText(question.getAnswerD());
                correctAnswer.setText(question.getCorrectAnswer());
                isImageQuestion.setText(question.getIsImageQuestion());
                userId = question.getId();

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Update",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        String questionCategoryId = edtcategoryId.getText().toString();
                                        String questionName = txtquestion.getText().toString();
                                        String answerA = questionAnswerA.getText().toString();
                                        String answerB = questionAnswerB.getText().toString();
                                        String answerC = questionAnswerC.getText().toString();
                                        String answerD = questionAnswerD.getText().toString();
                                        String corrAnswer = correctAnswer.getText().toString();
                                        String image = isImageQuestion.getText().toString();

                                        //update
                                        databaseReference.child(userId).child("categoryId").setValue(questionCategoryId);
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

                final TextView categoryId = (TextView) promptsView
                        .findViewById(R.id.categoryId);

                final TextView txtQuestion = (TextView) promptsView
                        .findViewById(R.id.txtQuestion);

                final TextView txtAnswerA = (TextView) promptsView
                        .findViewById(R.id.txtAnswerA);

                final TextView txtAnswerB = (TextView) promptsView
                        .findViewById(R.id.txtAnswerB);

                final TextView txtAnswerC = (TextView) promptsView
                        .findViewById(R.id.txtAnswerC);

                final TextView txtAnswerD = (TextView) promptsView
                        .findViewById(R.id.txtAnswerD);

                final TextView corrAnswer = (TextView) promptsView
                        .findViewById(R.id.corrAnswer);

                final TextView IsImageQuestion = (TextView) promptsView
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
