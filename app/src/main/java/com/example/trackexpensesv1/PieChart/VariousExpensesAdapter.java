package com.example.trackexpensesv1.PieChart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackexpensesv1.Data.VariousExpensesData;
import com.example.trackexpensesv1.R;

import java.util.List;

public class VariousExpensesAdapter extends RecyclerView.Adapter<VariousExpensesAdapter.VariousExpensesViewHolder> {
    private Context mContext;
    private List<VariousExpensesData> mVariousExpensesData;

    public VariousExpensesAdapter(Context context, List<VariousExpensesData> variousExpensesData) {
        this.mContext = context;
        this.mVariousExpensesData = variousExpensesData;
    }

    class VariousExpensesViewHolder extends RecyclerView.ViewHolder {
        TextView various_adapter_type;
        TextView various_adapter_totalVariousExpense;

        VariousExpensesViewHolder(View itemView) {
            super(itemView);
            various_adapter_type = (TextView) itemView.findViewById(R.id.various_adapter_Type);
            various_adapter_totalVariousExpense = (TextView) itemView.findViewById(R.id.various_adapter_totalVariousExpense);
        }
    }

    @NonNull
    @Override
    public VariousExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_various_adapter, parent, false);
        return new VariousExpensesAdapter.VariousExpensesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VariousExpensesViewHolder holder, int position) {
        holder.various_adapter_type.setText(mVariousExpensesData.get(position).getType());
        holder.various_adapter_totalVariousExpense.setText(String.valueOf(mVariousExpensesData.get(position).getTotalVariousExpenses()));
    }

    @Override
    public int getItemCount() {
        return mVariousExpensesData.size();
    }
}


