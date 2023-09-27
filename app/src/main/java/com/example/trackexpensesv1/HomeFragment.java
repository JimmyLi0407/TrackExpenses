package com.example.trackexpensesv1;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.trackexpensesv1.ViewModel.ExpenseTrackerViewModel;
import com.example.trackexpensesv1.ViewModel.ViewModelFactory;

public class HomeFragment extends Fragment {

    private Toolbar home_toolbar;
    private Button home_incomeButton;
    private Button home_expensesButton;
    private TextView home_totalAssets;
    private TextView home_totalIncome;
    private TextView home_totalExpenses;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ViewModelFactory viewModelFactory = new ViewModelFactory(requireActivity().getApplication());
        ExpenseTrackerViewModel viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(ExpenseTrackerViewModel.class);
        setToolbar(view);
        initView(view);

        home_incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(0);
            }
        });

        home_expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(1);
            }
        });

        viewModel.getTotalAssets().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer totalAssets) {
                home_totalAssets.setText(String.valueOf(totalAssets));
            }
        });

        viewModel.getTotalIncome().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer totalIncome) {
                home_totalIncome.setText(String.valueOf(totalIncome));
            }
        });

        viewModel.getTotalExpenses().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer totalExpenses) {
                home_totalExpenses.setText(String.valueOf(totalExpenses));
            }
        });

        return view;
    }

    private void setToolbar(View view) {
        home_toolbar = (Toolbar) view.findViewById(R.id.home_Toolbar);
        home_toolbar.setTitle("首頁");
    }

    private void initView(View view) {
        home_incomeButton = (Button) view.findViewById(R.id.home_IncomeButton);
        home_expensesButton = (Button) view.findViewById(R.id.home_ExpensesButton);
        home_totalAssets = (TextView) view.findViewById(R.id.home_totalAssets);
        home_totalIncome = (TextView) view.findViewById(R.id.home_totalIncome);
        home_totalExpenses = (TextView) view.findViewById(R.id.home_totalExpenses);
    }

    private void changePage(int type) {
        Intent intent;
        if (type == 0) {
            intent = new Intent(getContext(), SaveIncomeActivity.class);
            startActivity(intent);
        } else if (type == 1) {
            intent = new Intent(getContext(), SaveExpensesActivity.class);
            startActivity(intent);
        }
    }
}
