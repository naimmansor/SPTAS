package com.sptas.sptasv2.Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sptas.sptasv2.Common.Common;
import com.sptas.sptasv2.Model.Question;
import com.sptas.sptasv2.R;

import java.util.Collections;

public class Start extends AppCompatActivity {

    Button btnPlay;

    FirebaseDatabase database;
    DatabaseReference questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_start);

        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");

        loadQuestion(Common.chapterId);

        btnPlay = findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start.this, Playing.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadQuestion(String chapterId) {

        // First, clear list if have old question
        if (Common.questionList.size() > 0)
            Common.questionList.clear();

        if (Common.categoryName.equals("Data Structure")) {
            questions.orderByChild("chapterId").equalTo(chapterId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Question ques = postSnapshot.getValue(Question.class);
                                Common.questionList.add(ques);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        } else if (Common.categoryName.equals("Software Engineering")) {
            questions.orderByChild("chapterId").equalTo(chapterId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Question ques = postSnapshot.getValue(Question.class);
                                Common.questionList.add(ques);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
        // Random list
        Collections.shuffle(Common.questionList);
    }
}
