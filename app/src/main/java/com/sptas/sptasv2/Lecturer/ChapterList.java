package com.sptas.sptasv2.Lecturer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.sptas.sptasv2.Model.Chapter;
import com.sptas.sptasv2.R;

import java.util.List;

public class ChapterList extends ArrayAdapter<Chapter> {

    public static String userId;
    DatabaseReference databaseReference;
    private EditText edtChapterId, edtChapterName, edtChapterNo;
    private RadioGroup edtCategoryId, edtAccess;
    private Activity context;
    private List<Chapter> chapters;

    private String access, cid = "";

    public ChapterList(@NonNull Activity context, List<Chapter> chapters, DatabaseReference databaseReference, RadioGroup edtCategoryId, EditText edtChapterId, EditText edtChapterName, EditText edtChapterNo, RadioGroup edtAccess) {
        super(context, R.layout.lecturer_activity_chapter_bank, chapters);
        this.context = context;
        this.chapters = chapters;
        this.databaseReference = databaseReference;
        this.edtCategoryId = edtCategoryId;
        this.edtChapterId = edtChapterId;
        this.edtChapterName = edtChapterName;
        this.edtChapterNo = edtChapterNo;
        this.edtAccess = edtAccess;
    }

    public View getView(int pos, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.lecturer_activity_chapter_bank, null, true);

        TextView categoryId = listViewItem.findViewById(R.id.categoryId);
        Button btnDelete = listViewItem.findViewById(R.id.btnChapterDelete);
        Button btnUpdate = listViewItem.findViewById(R.id.btnChapterUpdate);
        Button btnView = listViewItem.findViewById(R.id.btnChapterView);

        final Chapter chapter = chapters.get(pos);
        categoryId.setText("Chapter " + chapter.getNoChapter());

        categoryId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuestionBank.class);
                context.startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child(chapter.getId()).removeValue();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.lecturer_popup_chapter, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final RadioGroup categoryId = promptsView
                        .findViewById(R.id.edtCategoryId);

                final RadioButton categoryIdDS = promptsView
                        .findViewById(R.id.edtCategoryIdDS);

                final EditText chapterId = promptsView
                        .findViewById(R.id.edtChapterId);

                final EditText chapterName = promptsView
                        .findViewById(R.id.edtChapterName);

                final EditText chapterNo = promptsView
                        .findViewById(R.id.edtChapterNo);

                final RadioGroup accessGroup = promptsView
                        .findViewById(R.id.edtAccess);

                final RadioButton accessYes = promptsView
                        .findViewById(R.id.edtAccessYes);

                if (chapter.getCategoryId().equals("02")) {
                    categoryId.check(R.id.edtCategoryIdSE);
                } else {
                    categoryId.check(R.id.edtCategoryIdDS);
                }

                chapterId.setText(chapter.getChapterId());
                chapterName.setText(chapter.getNameChapter());
                chapterNo.setText(chapter.getNoChapter());

                if (chapter.getVaccess().equals("Yes")) {
                    accessGroup.check(R.id.edtAccessYes);
                } else {
                    accessGroup.check(R.id.edtAccessNo);
                }

                userId = chapter.getId();

                userId = chapter.getId();

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

                                        String chapterID = chapterId.getText().toString();
                                        String chapter = chapterName.getText().toString();
                                        String no = chapterNo.getText().toString();

                                        //condition radio button
                                        if (accessYes.isChecked()) {
                                            access = "Yes";
                                        } else {
                                            access = "No";
                                        }

                                        //update
                                        databaseReference.child(userId).child("categoryId").setValue(cid);
                                        databaseReference.child(userId).child("chapterId").setValue(chapterID);
                                        databaseReference.child(userId).child("chapterName").setValue(chapter);
                                        databaseReference.child(userId).child("chapterNo").setValue(no);
                                        databaseReference.child(userId).child("vaccess").setValue(access);

                                        Toast.makeText(context, "Question Updated Successfully!", Toast.LENGTH_SHORT).show();

                                        chapterName.setText(null);
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
                View promptsView = li.inflate(R.layout.lecturer_popup_view_chapter, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                alertDialogBuilder.setView(promptsView);

                final TextView categoryId = promptsView
                        .findViewById(R.id.categoryId);

                final TextView txtChapterId = promptsView
                        .findViewById(R.id.chapterId);

                final TextView txtChapterName = promptsView
                        .findViewById(R.id.nameChapter);

                final TextView txtChapterNo = promptsView
                        .findViewById(R.id.noChapter);

                final TextView txtAccess = promptsView
                        .findViewById(R.id.vaccess);

                categoryId.setText(chapter.getCategoryId());
                txtChapterId.setText(chapter.getChapterId());
                txtChapterName.setText(chapter.getNameChapter());
                txtChapterNo.setText(chapter.getNoChapter());
                txtAccess.setText(chapter.getVaccess());

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
