package com.sptas.sptasv2.Lecturer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sptas.sptasv2.Model.Ranking;
import com.sptas.sptasv2.R;

import java.util.ArrayList;


public class StatisticFragment extends Fragment {

    View myFragment;
    HorizontalBarChart mChart;

    DatabaseReference database;

    Ranking ranking;


    public static StatisticFragment newInstance() {
        StatisticFragment statisticFragment = new StatisticFragment();
        return statisticFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance().getReference("Ranking");

        // database.child("score").child(String.valueOf(ranking.getScore()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.lecturer_fragment_statistic, container, false);

        mChart = (HorizontalBarChart) myFragment.findViewById(R.id.chart);

        setData(12, 50);

        return myFragment;
    }

    private void setData(int count, int range) {

        ArrayList<BarEntry> yVals = new ArrayList<>();
        float barWidth = 9f;
        float spaceForBar = 10f;

        mChart.animateY(1000, Easing.EasingOption.EaseInElastic);

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            yVals.add(new BarEntry(i * spaceForBar, val));

            /*float val = (float) (Integer.parseInt(questionScore.getScore())*range);
            yVals.add(new BarEntry(i*spaceForBar, val));*/
        }

        BarDataSet set1;

        set1 = new BarDataSet(yVals, "Score for the Student");

        BarData data = new BarData(set1);
        data.setBarWidth(barWidth);

        mChart.setData(data);

    }

}


//example for pie chart

     /*pieChart = (PieChart) myFragment.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f); //spin of pieChart

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(60f);

        ArrayList<PieEntry> yValues = new ArrayList();

        yValues.add(new PieEntry(34f, "Bangladesh"));
        yValues.add(new PieEntry(23f, "USA"));
        yValues.add(new PieEntry(14f, "UK"));
        yValues.add(new PieEntry(35f, "India"));
        yValues.add(new PieEntry(40f, "Russia"));
        yValues.add(new PieEntry(23f, "Japan"));

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "Countries");
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data); */
