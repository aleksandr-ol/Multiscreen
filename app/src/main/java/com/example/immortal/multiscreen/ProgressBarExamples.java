package com.example.immortal.multiscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ProgressBarExamples extends FragmentActivity {
    public static final int PROGRESS_BAR = 0;
    public static final int RATING_BAR = 1;
    public static final int CHRONOMETER = 2;

    public static final int FRAGMENTS = 3;

    private FragmentPagerAdapter _fragmentPagerAdapter;
    private final List<Fragment> _fragments = new ArrayList<Fragment>();

    private ViewPager _viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar_examples);

        _fragments.add(PROGRESS_BAR, new ProgressBarFragment());
        _fragments.add(RATING_BAR, new RatingBarFragment());
        _fragments.add(CHRONOMETER, new ChronometerFragment());

        _fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return _fragments.get(position);
            }

            @Override
            public int getCount() {
                return FRAGMENTS;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case PROGRESS_BAR:
                        return "ProgressBar";
                    case RATING_BAR:
                        return "RatingBar";
                    case CHRONOMETER:
                        return "Chronometer";
                    default:
                        return null;
                }
            }
        };

        _viewPager = (ViewPager) findViewById(R.id.progressBar_pager);
        _viewPager.setAdapter(_fragmentPagerAdapter);
        _viewPager.setCurrentItem(0);
    }
}
