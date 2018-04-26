package com.sptas.sptasv2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.sptas.sptasv2.Lecturer.LecturerFragment;
import com.sptas.sptasv2.Lecturer.StatisticFragment;

/**
 * Created by Na'im Mansor on 05-Mar-18.
 */

public class LecturerActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_activity_home);

        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_lecturerProfile:
                        selectedFragment = LecturerFragment.newInstance();
                        break;
                    case R.id.action_questionBank:
                        selectedFragment = CategoryFragment.newInstance();
                        break;
                    case R.id.action_statistic:
                        selectedFragment = StatisticFragment.newInstance();
                        break;
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();

                return true;
            }
        });

        setDefaultFragment();
    }


    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, LecturerFragment.newInstance());
        transaction.commit();
    }
}
