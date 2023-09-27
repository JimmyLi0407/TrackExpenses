package com.example.trackexpensesv1;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trackexpensesv1.Data.VariousExpensesData;
import com.example.trackexpensesv1.PieChart.VariousExpensesAdapter;
import com.example.trackexpensesv1.ViewModel.ExpenseTrackerViewModel;
import com.example.trackexpensesv1.ViewModel.ViewModelFactory;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.teaanddogdog.mpandroidchartutil.PieChartFixCover;

import java.util.ArrayList;

public class PieChartExpensesFragment extends Fragment {
    private Context context;

    private PieChartFixCover expenses_pie_chart_piechart;
    private RecyclerView expenses_pie_chart_recyclerview;

    public PieChartExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pie_chart_expenses, container, false);
        final String[] expensesType = {"食", "衣", "住", "行"};
        ViewModelFactory viewModelFactory = new ViewModelFactory(requireActivity().getApplication());
        ExpenseTrackerViewModel viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(ExpenseTrackerViewModel.class);
        initView(view);
        context = getActivity();
        viewModel.getExpensesPieChartList().observe(requireActivity(), new Observer<ArrayList<PieEntry>>() {
            @Override
            public void onChanged(ArrayList<PieEntry> pieEntries) {
                ArrayList<Integer> colors = new ArrayList<>();
                if (pieEntries.size() == 1 && pieEntries.get(0).getLabel().equals("尚未有紀錄")) {
                    colors.add(Color.GRAY);
                } else {
                    for (int i = 0; i < pieEntries.size(); i++) {
                        if (pieEntries.get(i).getLabel().equals(expensesType[0])) {
                            colors.add(ContextCompat.getColor(context, R.color.colorPieChart1));
                        }

                        if (pieEntries.get(i).getLabel().equals(expensesType[1])) {
                            colors.add(ContextCompat.getColor(context, R.color.colorPieChart2));
                        }

                        if (pieEntries.get(i).getLabel().equals(expensesType[2])) {
                            colors.add(ContextCompat.getColor(context, R.color.colorPieChart3));
                        }

                        if (pieEntries.get(i).getLabel().equals(expensesType[3])) {
                            colors.add(ContextCompat.getColor(context, R.color.colorPieChart4));
                        }
                    }
                }
                initChart(expenses_pie_chart_piechart, pieEntries, colors);
            }
        });

        viewModel.getVariousExpensesList().observe(requireActivity(), new Observer<ArrayList<VariousExpensesData>>() {
            @Override
            public void onChanged(ArrayList<VariousExpensesData> variousExpensesData) {
                expenses_pie_chart_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                expenses_pie_chart_recyclerview.setHasFixedSize(true);
                expenses_pie_chart_recyclerview.setAdapter(new VariousExpensesAdapter(getContext(), variousExpensesData));
            }
        });

        return view;
    }

    private void initView(View view) {
        expenses_pie_chart_piechart = (PieChartFixCover) view.findViewById(R.id.expenses_pie_chart_PieChart);
        expenses_pie_chart_recyclerview = (RecyclerView) view.findViewById(R.id.expenses_pie_chart_Recyclerview);
    }

    private void initChart(PieChartFixCover chart, ArrayList<PieEntry> expensesPieChartList, ArrayList<Integer> colors) {
        PieDataSet pieDataSet = setPieDataSet(expensesPieChartList, colors);
        PieData pieData = setPieData(pieDataSet);
        Description description = setDescription();

        chart.setData(pieData);
        chart.setDescription(description);
        chart.setUsePercentValues(true);
        chart.setDrawEntryLabels(true);
        chart.setExtraOffsets(5f, 5f, 5f, 15f);
        chart.setEntryLabelColor(Color.BLACK);
        chart.animateY(1400);
        chart.setHoleRadius(0f);
        chart.setTransparentCircleAlpha(0);
        chart.invalidate();
    }

    private PieDataSet setPieDataSet(ArrayList<PieEntry> expensesPieChartList, ArrayList<Integer> colors) {
        PieDataSet pieDataSet = new PieDataSet(expensesPieChartList, "");
        pieDataSet.setColors(colors);
        pieDataSet.setDrawIcons(false);
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        return pieDataSet;
    }

    private PieData setPieData(PieDataSet pieDataSet) {
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setDrawValues(true);
        pieData.setValueTextSize(12f);

        return pieData;
    }

    private Description setDescription() {
        Description description = new Description();
        description.setText("");

        return description;
    }
}
