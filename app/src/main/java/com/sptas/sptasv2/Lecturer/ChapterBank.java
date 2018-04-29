package com.sptas.sptasv2.Lecturer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
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
import com.sptas.sptasv2.Model.Chapter;
import com.sptas.sptasv2.R;

import java.util.ArrayList;
import java.util.List;

public class ChapterBank extends AppCompatActivity {

    DatabaseReference databaseReference;
    ListView listViewChapter;
    List<Chapter> chapters;
    private FloatingActionButton btnAdd;
    private EditText edtChapterId, edtChapterName, edtChapterNo;
    private RadioGroup edtCategoryId, edtAccess;

    private String cid, access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_chapter_bank);

        chapters = new ArrayList<Chapter>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chapter");

        // components from main.xml
        btnAdd = (FloatingActionButton) findViewById(R.id.fab);
        edtCategoryId = (RadioGroup) findViewById(R.id.edtCategoryId);
        edtChapterId = (EditText) findViewById(R.id.edtChapterId);
        edtChapterName = (EditText) findViewById(R.id.edtChapterName);
        edtChapterNo = (EditText) findViewById(R.id.edtChapterNo);
        edtAccess = (RadioGroup) findViewById(R.id.edtAccess);
        listViewChapter = findViewById(R.id.listViewChapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(ChapterBank.this);
                View promptsView = li.inflate(R.layout.lecturer_popup_chapter, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ChapterBank.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final RadioButton categoryIdDS = promptsView
                        .findViewById(R.id.edtCategoryIdDS);

                final EditText chapterId = promptsView
                        .findViewById(R.id.edtChapterId);

                final EditText chapterName = promptsView
                        .findViewById(R.id.edtChapterName);

                final EditText chapterNo = promptsView
                        .findViewById(R.id.edtChapterNo);

                final RadioButton accessYes = promptsView
                        .findViewById(R.id.edtAccessYes);

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

                                        String chapterID = chapterId.getText().toString();
                                        String chapter = chapterName.getText().toString();
                                        String no = chapterNo.getText().toString();

                                        //condition checkbox
                                        if (accessYes.isChecked()) {
                                            access = "Yes";
                                        } else {
                                            access = "No";
                                        }

                                        //save
                                        String uid = databaseReference.push().getKey();
                                        Chapter test = new Chapter(uid, cid, chapterID, chapter, no, access);
                                        databaseReference.child(uid).setValue(test);

                                        Toast.makeText(ChapterBank.this, "Chapter Created Successfully!", Toast.LENGTH_SHORT).show();

                                        chapterName.setText(null);
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

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chapters.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Chapter chapter = postSnapshot.getValue(Chapter.class);
                    chapters.add(chapter);
                }

                ChapterList chapterAdapter = new ChapterList(ChapterBank.this, chapters, databaseReference, edtCategoryId, edtChapterId, edtChapterName, edtChapterNo, edtAccess);
                listViewChapter.setAdapter(chapterAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
