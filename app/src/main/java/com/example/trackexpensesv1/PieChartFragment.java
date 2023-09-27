package com.example.trackexpensesv1;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trackexpensesv1.PieChart.PieChartViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class PieChartFragment extends Fragment {
    private Toolbar pie_chart_toolbar;
    private ViewPager pie_chart_ViewPager;
    private TabLayout pie_chart_TabLayout;

    public PieChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        setToolbar(view);
        initView(view);

        return view;
    }

    private void setToolbar(View view) {
        pie_chart_toolbar = (Toolbar) view.findViewById(R.id.pie_chart_Toolbar);
        pie_chart_toolbar.setTitle("統計");
    }

    private void initView(View view) {
        pie_chart_ViewPager = (ViewPager) view.findViewById(R.id.pie_chart_ViewPager);
        pie_chart_TabLayout = (TabLayout) view.findViewById(R.id.pie_chart_TabLayout);

        pie_chart_ViewPager.setAdapter(new PieChartViewPagerAdapter(getChildFragmentManager(), 2));
        pie_chart_TabLayout.setupWithViewPager(pie_chart_ViewPager);
    }
}
