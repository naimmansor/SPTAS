package com.sptas.sptasv2.Student;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sptas.sptasv2.Common.Common;
import com.sptas.sptasv2.R;
import com.squareup.picasso.Picasso;

public class Playing extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000; // 1 sec
    final static long TIMEOUT = 20000; // 20 sec
    int progressValue = 0;

    CountDownTimer mCountDown;

    int index = 0, score = 0, thisQuestion = 0, totalQuestion, correctAnswer;


    ProgressBar progressBar;
    ImageView question_image;
    Button btnA, btnB, btnC, btnD, btnNext;
    TextView txtScore, txtQuestionNum, question_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_playing);


        //Views
        txtScore = findViewById(R.id.txtScore);
        txtQuestionNum = findViewById(R.id.txtTotalQuestion);
        question_text = findViewById(R.id.question_text);
        question_image = findViewById(R.id.question_image);

        progressBar = findViewById(R.id.progressBar);

        btnA = findViewById(R.id.btnAnswerA);
        btnB = findViewById(R.id.btnAnswerB);
        btnC = findViewById(R.id.btnAnswerC);
        btnD = findViewById(R.id.btnAnswerD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        mCountDown.cancel();
        if (index < totalQuestion) // still have question in list
        {
            Button clickedButton = (Button) view;
            if (clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())) {
                // Choose correct answer
                score += 10;
                correctAnswer++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                showQuestion(++index); //next question
            } else {
                //Choose wrong answer
                Toast.makeText(this, "Wrong, Answer is " + Common.questionList.get(index).getCorrectAnswer() + " !", Toast.LENGTH_SHORT).show();
                showQuestion(++index); //next question
            }

            txtScore.setText(String.format("%d", score));
        }

    }

    private void showQuestion(int index) {
        if (index < totalQuestion) {
            thisQuestion++;
            txtQuestionNum.setText(String.format("%d / %d", thisQuestion, totalQuestion));
            progressBar.setProgress(0);
            progressValue = 0;

            if (Common.questionList.get(index).getIsImageQuestion().equals("true")) {
                // If is image
                Picasso.with(getBaseContext())
                        .load(Common.questionList.get(index).getQuestion())
                        .into(question_image);
                question_image.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);
            } else {
                question_text.setText(Common.questionList.get(index).getQuestion());

                // If question is text, we will set image to invisible
                question_image.setVisibility(View.INVISIBLE);
                question_text.setVisibility(View.VISIBLE);
            }

            btnA.setText(Common.questionList.get(index).getAnswerA());
            btnB.setText(Common.questionList.get(index).getAnswerB());

            //condition for button C and D
            if (Common.questionList.get(index).getAnswerC().equals("null")) {
                btnC.setVisibility(View.INVISIBLE);
                btnD.setVisibility(View.INVISIBLE);
            } else {
                btnC.setText(Common.questionList.get(index).getAnswerC());
                btnD.setText(Common.questionList.get(index).getAnswerD());
            }

            mCountDown.start(); // Start Timer
        } else {
            // If it is final question
            Intent intent = new Intent(this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    //Press Ctrl + O

    @Override
    protected void onPostResume() {
        super.onPostResume();

        totalQuestion = Common.questionList.size();

        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long miniSec) {
                progressBar.setProgress(progressValue);
                progressValue++;

            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                Toast.makeText(Playing.this, "You should Answer the Question!", Toast.LENGTH_SHORT).show();
                showQuestion(++index);
            }
        };
        showQuestion(index);
    }
}
