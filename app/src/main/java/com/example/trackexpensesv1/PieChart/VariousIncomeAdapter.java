package com.example.trackexpensesv1.PieChart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackexpensesv1.Data.VariousIncomeData;
import com.example.trackexpensesv1.R;

import java.util.List;

public class VariousIncomeAdapter extends RecyclerView.Adapter<VariousIncomeAdapter.VariousIncomeViewHolder> {
    private Context mContext;
    private List<VariousIncomeData> mVariousIncomeData;

    public VariousIncomeAdapter(Context context, List<VariousIncomeData> variousIncomeData) {
        this.mContext = context;
        this.mVariousIncomeData = variousIncomeData;
    }

    class VariousIncomeViewHolder extends RecyclerView.ViewHolder {
        TextView various_adapter_type;
        TextView various_adapter_totalVariousExpense;

        VariousIncomeViewHolder(View itemView) {
            super(itemView);
            various_adapter_type = (TextView) itemView.findViewById(R.id.various_adapter_Type);
            various_adapter_totalVariousExpense = (TextView) itemView.findViewById(R.id.various_adapter_totalVariousExpense);
        }
    }

    @NonNull
    @Override
    public VariousIncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_various_adapter, parent, false);
        return new VariousIncomeAdapter.VariousIncomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VariousIncomeViewHolder holder, int position) {
        holder.various_adapter_type.setText(mVariousIncomeData.get(position).getType());
        holder.various_adapter_totalVariousExpense.setText(String.valueOf(mVariousIncomeData.get(position).getTotalVariousIncome()));
    }

    @Override
    public int getItemCount() {
        return mVariousIncomeData.size();
    }


}
