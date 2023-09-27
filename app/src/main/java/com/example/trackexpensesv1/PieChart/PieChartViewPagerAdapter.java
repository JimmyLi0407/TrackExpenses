package com.example.trackexpensesv1.PieChart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.trackexpensesv1.PieChartExpensesFragment;
import com.example.trackexpensesv1.PieChartIncomeFragment;

public class PieChartViewPagerAdapter extends FragmentPagerAdapter {
    public PieChartViewPagerAdapter(FragmentManager fm, int bh) {
        super(fm, bh);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PieChartExpensesFragment();

            case 1:
                return new PieChartIncomeFragment();
        }
        return new PieChartExpensesFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "支出";

            case 1:
                return "收入";
        }
        return "支出";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
