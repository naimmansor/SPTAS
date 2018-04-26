package com.sptas.sptasv2.Lecturer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sptas.sptasv2.Model.StatisticScore;
import com.sptas.sptasv2.R;

import java.util.ArrayList;
import java.util.List;


public class StatisticFragment extends Fragment {

    final List<String> statisticName = new ArrayList<>();
    final List<Long> statisticListScore = new ArrayList<>();

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    View myFragment;
    HorizontalBarChart mChart;

    StatisticScore statisticScore;
    DatabaseReference ref = database.getReference("Question_Score");

    public static StatisticFragment newInstance() {
        StatisticFragment statisticFragment = new StatisticFragment();
        return statisticFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.lecturer_fragment_statistic, container, false);

        showStatistic();

        return myFragment;
    }

    private void showStatistic() {
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    statisticScore = snapshot.getValue(StatisticScore.class);

                    statisticName.add(statisticScore.getUserName());
                    statisticListScore.add(Long.parseLong(statisticScore.getScore()));
                }

                mChart = (HorizontalBarChart) myFragment.findViewById(R.id.chart);

                XAxis xAxis = mChart.getXAxis();
                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);

                ArrayList<BarEntry> yValues = new ArrayList<>();
                //float barWidth = 9f;
                //float  spaceForBar = 10f;

                for (int i = 0; i < statisticName.size(); i++) {
                    yValues.add(new BarEntry(i, statisticListScore.get(i)));
                }

                mChart.animateY(1000, Easing.EasingOption.EaseInOutElastic);

                BarDataSet barDataSet = new BarDataSet(yValues, "Data Score");
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                BarData data = new BarData(barDataSet);
                //data.setBarWidth(barWidth);
                data.setValueTextSize(13f);
                data.setValueTextColor(Color.MAGENTA);

                mChart.getXAxis().setValueFormatter(new LabelFormatter(statisticName));
                mChart.setData((data));
                mChart.invalidate();

                System.out.println("User Name : " + statisticName);
                System.out.println("Score : " + statisticListScore);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private class LabelFormatter implements IAxisValueFormatter {
        private final List<String> mLabels;

        public LabelFormatter(List<String> labels) {
            mLabels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mLabels.get((int) value);
        }
    }
}


