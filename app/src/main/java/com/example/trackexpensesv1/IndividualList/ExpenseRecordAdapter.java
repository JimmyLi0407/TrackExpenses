package com.example.trackexpensesv1.IndividualList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackexpensesv1.DataBase.ExpensesRecord;
import com.example.trackexpensesv1.R;

import java.util.List;

public class ExpenseRecordAdapter extends RecyclerView.Adapter<ExpenseRecordAdapter.ExpenseRecordViewHolder> {
    private Context mContext;
    private List<ExpensesRecord> mExpensesRecordData;

    public ExpenseRecordAdapter(Context context, List<ExpensesRecord> ExpensesRecordData) {
        this.mContext = context;
        this.mExpensesRecordData = ExpensesRecordData;
    }

    class ExpenseRecordViewHolder extends RecyclerView.ViewHolder {
        TextView record_list_type;
        TextView record_list_money;
        TextView record_list_date;

        ExpenseRecordViewHolder(View itemView) {
            super(itemView);
            record_list_type = (TextView) itemView.findViewById(R.id.record_list_Type);
            record_list_money = (TextView) itemView.findViewById(R.id.record_list_Money);
            record_list_date = (TextView) itemView.findViewById(R.id.record_list_Date);
        }
    }

    @NonNull
    @Override
    public ExpenseRecordAdapter.ExpenseRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_record, parent, false);
        return new ExpenseRecordAdapter.ExpenseRecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseRecordAdapter.ExpenseRecordViewHolder holder, int position) {
        holder.record_list_type.setText(mExpensesRecordData.get(position).getType());
        holder.record_list_money.setText(mExpensesRecordData.get(position).getCost());
        holder.record_list_date.setText(mExpensesRecordData.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mExpensesRecordData.size();
    }
}
