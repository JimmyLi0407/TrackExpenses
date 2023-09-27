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

import com.example.trackexpensesv1.DataBase.IncomeRecord;
import com.example.trackexpensesv1.IndividualList.IncomeRecordAdapter;
import com.example.trackexpensesv1.ViewModel.ExpenseTrackerViewModel;
import com.example.trackexpensesv1.ViewModel.ViewModelFactory;

import java.util.List;

public class IncomeListFragment extends Fragment {
    private RecyclerView income_list_recycler;

    public IncomeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income_list, container, false);
        ViewModelFactory viewModelFactory = new ViewModelFactory(requireActivity().getApplication());
        ExpenseTrackerViewModel viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(ExpenseTrackerViewModel.class);
        initView(view);
        viewModel.getIncomeRecordsAll().observe(requireActivity(), new Observer<List<IncomeRecord>>() {
            @Override
            public void onChanged(List<IncomeRecord> incomeRecords) {
                income_list_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                income_list_recycler.setHasFixedSize(true);
                income_list_recycler.setAdapter(new IncomeRecordAdapter(getContext(), incomeRecords));
            }
        });

        return view;
    }

    private void initView(View view) {
        income_list_recycler = (RecyclerView) view.findViewById(R.id.income_list_Recycler);
    }
}
