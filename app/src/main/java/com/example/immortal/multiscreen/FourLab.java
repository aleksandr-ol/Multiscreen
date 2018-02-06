package com.example.immortal.multiscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class FourLab extends FragmentActivity {
    public static final int TIP_CALCULATOR = 0;
    public static final int CALCULATOR = 1;
    public static final int PERCENT_CALCULATE = 2;
    public static final int FRAGMENTS = 3;

    private FragmentPagerAdapter _fragmentPagerAdapter;
    private final List<Fragment> _fragments = new ArrayList<Fragment>();

    private ViewPager _viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_lab);

        _fragments.add(TIP_CALCULATOR, new TipCalculator());
        _fragments.add(CALCULATOR, new Calculator());
        _fragments.add(PERCENT_CALCULATE, new PercentCalculate());

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
                    case TIP_CALCULATOR:
                        return "Калькулятор чайових";
                    case CALCULATOR:
                        return "Калькулятор";
                    case PERCENT_CALCULATE:
                        return "Відсоток числа";
                    default:
                        return null;
                }
            }
        };

        _viewPager = (ViewPager) findViewById(R.id.pager);
        _viewPager.setAdapter(_fragmentPagerAdapter);
        _viewPager.setCurrentItem(1);
    }
}
