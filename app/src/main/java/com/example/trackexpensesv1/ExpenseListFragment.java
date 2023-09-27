package com.example.trackexpensesv1;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trackexpensesv1.DataBase.ExpensesRecord;
import com.example.trackexpensesv1.IndividualList.ExpenseRecordAdapter;
import com.example.trackexpensesv1.ViewModel.ExpenseTrackerViewModel;
import com.example.trackexpensesv1.ViewModel.ViewModelFactory;

import java.util.List;

public class ExpenseListFragment extends Fragment {

    private RecyclerView expense_list_recycler;

    public ExpenseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);
        ViewModelFactory viewModelFactory = new ViewModelFactory(requireActivity().getApplication());
        ExpenseTrackerViewModel viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(ExpenseTrackerViewModel.class);
        initView(view);
        viewModel.getExpensesRecordAll().observe(requireActivity(), new Observer<List<ExpensesRecord>>() {
            @Override
            public void onChanged(List<ExpensesRecord> expensesRecords) {
                expense_list_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                expense_list_recycler.setHasFixedSize(true);
                expense_list_recycler.setAdapter(new ExpenseRecordAdapter(getContext(), expensesRecords));
            }
        });

        return view;
    }
    private void initView(View view) {
        expense_list_recycler = (RecyclerView) view.findViewById(R.id.expense_list_Recycler);
    }
}
