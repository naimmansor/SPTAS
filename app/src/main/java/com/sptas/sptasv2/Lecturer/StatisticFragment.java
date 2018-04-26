package com.sptas.sptasv2.Lecturer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
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

    final List<String> statisticNameSTF = new ArrayList<>();
    final List<Long> statisticListScoreSTF = new ArrayList<>();
    final List<String> statisticNameSMC = new ArrayList<>();
    final List<Long> statisticListScoreSMC = new ArrayList<>();
    final List<String> statisticNameLTF = new ArrayList<>();
    final List<Long> statisticListScoreLTF = new ArrayList<>();
    final List<String> statisticNameLMC = new ArrayList<>();
    final List<Long> statisticListScoreLMC = new ArrayList<>();
    final List<String> statisticNameStack = new ArrayList<>();
    final List<Long> statisticListScoreStack = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String ChapterNameSTF = "";
    String ChapterNameSMC = "";
    String ChapterNameLTF = "";
    String ChapterNameLMC = "";
    String ChapterNameStack = "";
    View myFragment;
    BarChart stfChart, smcChart, ltfChart, lmcChart, stackChart;

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
        ref.orderByChild("chapterId")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            statisticScore = snapshot.getValue(StatisticScore.class);

                            if (statisticScore.getChapterId().equals("ds07")) {
                                ChapterNameSTF = statisticScore.getChapterName();
                                statisticNameSTF.add(statisticScore.getUserName());
                                statisticListScoreSTF.add(Long.parseLong(statisticScore.getScore()));
                            } else if (statisticScore.getChapterId().equals("ds08")) {

                                ChapterNameSMC = statisticScore.getChapterName();
                                statisticNameSMC.add(statisticScore.getUserName());
                                statisticListScoreSMC.add(Long.parseLong(statisticScore.getScore()));
                            } else if (statisticScore.getChapterId().equals("ds09")) {

                                ChapterNameLTF = statisticScore.getChapterName();
                                statisticNameLTF.add(statisticScore.getUserName());
                                statisticListScoreLTF.add(Long.parseLong(statisticScore.getScore()));
                            } else if (statisticScore.getChapterId().equals("ds10")) {

                                ChapterNameLMC = statisticScore.getChapterName();
                                statisticNameLMC.add(statisticScore.getUserName());
                                statisticListScoreLMC.add(Long.parseLong(statisticScore.getScore()));
                            } else if (statisticScore.getChapterId().equals("ds12")) {

                                ChapterNameStack = statisticScore.getChapterName();
                                statisticNameStack.add(statisticScore.getUserName());
                                statisticListScoreStack.add(Long.parseLong(statisticScore.getScore()));
                            }


                        }

                        // graph statistic for Searching T/F
                        stfChart = (BarChart) myFragment.findViewById(R.id.searchingTFchart);

                        XAxis xAxisSTF = stfChart.getXAxis();
                        xAxisSTF.setGranularity(1f);
                        xAxisSTF.setGranularityEnabled(true);

                        ArrayList<BarEntry> yValSTF = new ArrayList<>();
                        //float barWidth = 9f;
                        //float  spaceForBar = 10f;

                        for (int i = 0; i < statisticNameSTF.size(); i++) {
                            yValSTF.add(new BarEntry(i, statisticListScoreSTF.get(i)));
                        }

                        stfChart.animateY(1000, Easing.EasingOption.EaseInOutElastic);

                        BarDataSet barDataSetSTF = new BarDataSet(yValSTF, "Score for Chapter : " + ChapterNameSTF);
                        barDataSetSTF.setColors(ColorTemplate.COLORFUL_COLORS);

                        BarData dataSTF = new BarData(barDataSetSTF);
                        //data.setBarWidth(barWidth);
                        dataSTF.setValueTextSize(13f);
                        dataSTF.setValueTextColor(Color.MAGENTA);

                        stfChart.getXAxis().setValueFormatter(new LabelFormatter(statisticNameSTF));
                        stfChart.setData((dataSTF));
                        stfChart.invalidate();


                        // graph statistic for Searching MC
                        smcChart = (BarChart) myFragment.findViewById(R.id.searchingMCchart);

                        ArrayList<BarEntry> yValSMC = new ArrayList<>();
                        //float barWidth = 9f;
                        //float  spaceForBar = 10f;

                        for (int i = 0; i < statisticNameSMC.size(); i++) {
                            yValSMC.add(new BarEntry(i, statisticListScoreSMC.get(i)));
                        }

                        smcChart.animateY(1000, Easing.EasingOption.EaseInOutElastic);

                        BarDataSet barDataSetSMC = new BarDataSet(yValSMC, "Score for Chapter : " + ChapterNameSMC);
                        barDataSetSMC.setColors(ColorTemplate.COLORFUL_COLORS);

                        BarData dataSMC = new BarData(barDataSetSMC);
                        //data.setBarWidth(barWidth);
                        dataSMC.setValueTextSize(13f);
                        dataSMC.setValueTextColor(Color.MAGENTA);

                        smcChart.setData((dataSMC));
                        smcChart.invalidate();


                        // graph statistic for Linked List T/F
                        ltfChart = (BarChart) myFragment.findViewById(R.id.linkListTFchart);

                        ArrayList<BarEntry> yValLTF = new ArrayList<>();
                        //float barWidth = 9f;
                        //float  spaceForBar = 10f;

                        for (int i = 0; i < statisticNameLTF.size(); i++) {
                            yValLTF.add(new BarEntry(i, statisticListScoreLTF.get(i)));
                        }

                        ltfChart.animateY(1000, Easing.EasingOption.EaseInOutElastic);

                        BarDataSet barDataSetLTF = new BarDataSet(yValLTF, "Score for Chapter : " + ChapterNameLTF);
                        barDataSetLTF.setColors(ColorTemplate.COLORFUL_COLORS);

                        BarData dataLTF = new BarData(barDataSetLTF);
                        //data.setBarWidth(barWidth);
                        dataLTF.setValueTextSize(13f);
                        dataLTF.setValueTextColor(Color.MAGENTA);

                        ltfChart.setData((dataLTF));
                        ltfChart.invalidate();


                        // graph statistic for Linked List MC
                        lmcChart = (BarChart) myFragment.findViewById(R.id.linkListMCchart);

                        ArrayList<BarEntry> yValLMC = new ArrayList<>();
                        //float barWidth = 9f;
                        //float  spaceForBar = 10f;

                        for (int i = 0; i < statisticNameLMC.size(); i++) {
                            yValLMC.add(new BarEntry(i, statisticListScoreLMC.get(i)));
                        }

                        lmcChart.animateY(1000, Easing.EasingOption.EaseInOutElastic);

                        BarDataSet barDataSetLMC = new BarDataSet(yValLMC, "Score for Chapter : " + ChapterNameLMC);
                        barDataSetLMC.setColors(ColorTemplate.COLORFUL_COLORS);

                        BarData dataLMC = new BarData(barDataSetLMC);
                        //data.setBarWidth(barWidth);
                        dataLMC.setValueTextSize(13f);
                        dataLMC.setValueTextColor(Color.MAGENTA);

                        lmcChart.setData((dataLMC));
                        lmcChart.invalidate();


                        // graph statistic for Stack
                        stackChart = (BarChart) myFragment.findViewById(R.id.stackMCchart);

                        ArrayList<BarEntry> yValStack = new ArrayList<>();
                        //float barWidth = 9f;
                        //float  spaceForBar = 10f;

                        for (int i = 0; i < statisticNameStack.size(); i++) {
                            yValStack.add(new BarEntry(i, statisticListScoreStack.get(i)));
                        }

                        stackChart.animateY(1000, Easing.EasingOption.EaseInOutElastic);

                        BarDataSet barDataSetStack = new BarDataSet(yValStack, "Score for Chapter : " + ChapterNameStack);
                        barDataSetStack.setColors(ColorTemplate.COLORFUL_COLORS);

                        BarData dataStack = new BarData(barDataSetStack);
                        //data.setBarWidth(barWidth);
                        dataStack.setValueTextSize(13f);
                        dataStack.setValueTextColor(Color.MAGENTA);

                        stackChart.setData((dataStack));
                        stackChart.invalidate();

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


