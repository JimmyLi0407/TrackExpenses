package com.example.trackexpensesv1.IndividualList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.trackexpensesv1.ExpenseListFragment;
import com.example.trackexpensesv1.IncomeListFragment;

public class IndividualListViewPagerAdapter extends FragmentPagerAdapter {
    public IndividualListViewPagerAdapter(FragmentManager fm, int bh) {
        super(fm, bh);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ExpenseListFragment();

            case 1:
                return new IncomeListFragment();
        }
        return new ExpenseListFragment();
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
