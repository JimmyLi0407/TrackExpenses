package com.example.trackexpensesv1;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trackexpensesv1.IndividualList.IndividualListViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class IndividualListFragment extends Fragment {
    private Toolbar individual_toolbar;
    private TabLayout individual_tablayout;
    private ViewPager individual_viewpager;

    public IndividualListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_individual_list, container, false);
        setToolbar(view);
        initView(view);
        individual_viewpager.setAdapter(new IndividualListViewPagerAdapter(getChildFragmentManager(), 2));
        individual_tablayout.setupWithViewPager(individual_viewpager);

        return view;
    }
    private void setToolbar(View view) {
        individual_toolbar =  view.findViewById(R.id.individual_Toolbar);
        individual_toolbar.setTitle("清單");
    }

    private void initView(View view) {
        individual_tablayout = (TabLayout) view.findViewById(R.id.individual_Tablayout);
        individual_viewpager = (ViewPager) view.findViewById(R.id.individual_ViewPager);
    }
}
