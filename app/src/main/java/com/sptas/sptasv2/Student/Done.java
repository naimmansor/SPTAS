package com.sptas.sptasv2.Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sptas.sptasv2.Common.Common;
import com.sptas.sptasv2.Model.QuestionScore;
import com.sptas.sptasv2.R;
import com.sptas.sptasv2.StudentActivity;

public class Done extends AppCompatActivity {

    Button btnTryAgain;
    TextView txtResultScore, getTxtResultQuestion, done_text;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference question_score;

    float total_percentage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_done);

        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");

        txtResultScore = findViewById(R.id.txtTotalScore);
        getTxtResultQuestion = findViewById(R.id.txtTotalQuestion);
        progressBar = findViewById(R.id.doneProgressBar);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        done_text = findViewById(R.id.done_text);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Done.this, StudentActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //Get data from bundle and set to view
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");

            txtResultScore.setText(String.format("SCORE : %d", score));
            getTxtResultQuestion.setText(String.format("PASSED : %d / %d", correctAnswer, totalQuestion));

            total_percentage += ((correctAnswer * 100) / totalQuestion);

            if (total_percentage >= 80) {
                done_text.setText("Congratulations, you really understand the chapter and should teach others!");
            } else if (total_percentage < 80 && total_percentage >= 50) {
                done_text.setText("Well done, you already understand the chapter!");
            } else if (total_percentage <= 49) {
                done_text.setText("Good Try, but you need try harder!");
            }

            progressBar.setMax(totalQuestion);
            progressBar.setProgress(correctAnswer);

            //Upload point to DB
            question_score.child(String.format("%s_%s", Common.currentUser.getnickName(),
                    Common.chapterId))
                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUser.getUserName(),
                            Common.categoryId),
                            Common.currentUser.getnickName(),
                            String.valueOf(score),
                            Common.categoryId,
                            Common.categoryName,
                            Common.chapterId,
                            Common.chapterName));

        }
    }
}
