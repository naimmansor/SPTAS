package com.sptas.sptasv2.Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sptas.sptasv2.Common.Common;
import com.sptas.sptasv2.Interface.ItemClickListener;
import com.sptas.sptasv2.Model.Chapter;
import com.sptas.sptasv2.R;
import com.sptas.sptasv2.ViewHolder.ChapterViewHolder;

public class ChapterDetail extends AppCompatActivity {

    RecyclerView chapterList;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Chapter, ChapterViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference chapter_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_chapter_detail);

        //Firebase
        database = FirebaseDatabase.getInstance();
        chapter_number = database.getReference("Chapter");

        //View
        chapterList = findViewById(R.id.chapterList);
        chapterList.setHasFixedSize(false);  // a bit change from tutorial => true
        layoutManager = new LinearLayoutManager(this);
        chapterList.setLayoutManager(layoutManager);

        loadChapterDetail();
    }

    private void loadChapterDetail() {
        adapter = new FirebaseRecyclerAdapter<Chapter, ChapterViewHolder>(
                Chapter.class,
                R.layout.student_chapter_detail,
                ChapterViewHolder.class,
                chapter_number
        ) {
            @Override
            protected void populateViewHolder(ChapterViewHolder viewHolder, final Chapter model, int position) {

                if (model.getVaccess().equals("Yes")) {
                    viewHolder.txt_name.setText(model.getNameChapter());
                    viewHolder.txt_no.setText(String.valueOf(model.getNoChapter()));

                    //Fixed crash when click to item
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Intent startGame = new Intent(ChapterDetail.this, Start.class);
                            Common.chapterId = adapter.getRef(position).getKey();
                            Common.chapterName = model.getNameChapter();
                            startActivity(startGame);
                        }
                    });
                } else if (model.getVaccess().equals("No")) {
                    viewHolder.txt_name.setText(model.getNameChapter());
                    viewHolder.txt_no.setText(String.valueOf(model.getNoChapter()));

                    //Fixed crash when click to item
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(ChapterDetail.this, "Please get Permission from your Lecturer!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };

        adapter.notifyDataSetChanged();
        chapterList.setAdapter(adapter);
    }
}
