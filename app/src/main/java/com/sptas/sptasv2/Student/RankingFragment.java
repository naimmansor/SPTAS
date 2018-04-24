package com.sptas.sptasv2.Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sptas.sptasv2.Common.Common;
import com.sptas.sptasv2.Interface.ItemClickListener;
import com.sptas.sptasv2.Interface.RankingCallBack;
import com.sptas.sptasv2.Model.QuestionScore;
import com.sptas.sptasv2.Model.Ranking;
import com.sptas.sptasv2.R;
import com.sptas.sptasv2.ViewHolder.RankingViewHolder;


public class RankingFragment extends Fragment {

    View myFragment;

    RecyclerView rankingList;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Ranking, RankingViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference questionScore, rankingTbl;

    int sum = 0;

    public static RankingFragment newInstance() {
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;
    }

    //Press ctrl + O

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        questionScore = database.getReference("Question_Score");
        rankingTbl = database.getReference("Ranking");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.student_fragment_ranking, container, false);

        // Init View
        rankingList = myFragment.findViewById(R.id.rankingList);
        layoutManager = new LinearLayoutManager(getActivity());
        rankingList.setHasFixedSize(false); // a bit change from tutorial => true
        // Because OrderByChild method of Firebase will sort list with ascending
        // So we need reverse our Recycler data
        // By LayoutManager
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rankingList.setLayoutManager(layoutManager);

        // Now, we need implement callback
        updateScore(Common.currentUser.getnickName(), new RankingCallBack<Ranking>() {
            @Override
            public void callBack(Ranking ranking) {
                // Update to Ranking table
                rankingTbl.child(ranking.getUserName())
                        .setValue(ranking);
                // showRanking(); // After upload, we will sort Ranking table and show result
            }
        });

        // Set Adapter
        adapter = new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(
                Ranking.class,
                R.layout.student_layout_ranking,
                RankingViewHolder.class,
                rankingTbl.orderByChild("score")
        ) {
            @Override
            protected void populateViewHolder(RankingViewHolder viewHolder, final Ranking model, int position) {

                if (model.getUserName().equals(Common.currentUser.getnickName())) {
                    viewHolder.txt_name.setText("You");
                } else {
                    viewHolder.txt_name.setText(model.getUserName());
                }

                viewHolder.txt_score.setText(String.valueOf(model.getScore()));

                //Fixed crash when click to item
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent scoreDetail = new Intent(getActivity(), ScoreDetail.class);
                        scoreDetail.putExtra("viewUser", model.getUserName());
                        startActivity(scoreDetail);
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        rankingList.setAdapter(adapter);

        return myFragment;
    }


    private void updateScore(final String userName, final RankingCallBack<Ranking> callback) {
        questionScore.orderByChild("user").equalTo(userName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            QuestionScore ques = data.getValue(QuestionScore.class);
                            sum += Integer.parseInt(ques.getScore());
                        }
                        // After summary all score, we need process sum variable here
                        // Because firebase is sync db, so if process outside , our 'sum'
                        //value will be reset to 0
                        Ranking ranking = new Ranking(userName, sum);
                        callback.callBack(ranking);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
