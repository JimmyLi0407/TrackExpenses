package com.example.trackexpensesv1.IndividualList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackexpensesv1.DataBase.IncomeRecord;
import com.example.trackexpensesv1.R;

import java.util.List;

public class IncomeRecordAdapter extends RecyclerView.Adapter<IncomeRecordAdapter.IncomeRecordViewHolder> {
    private Context mContext;
    private List<IncomeRecord> mIncomeRecordData;

    public IncomeRecordAdapter(Context context, List<IncomeRecord> incomeRecordData) {
        this.mContext = context;
        this.mIncomeRecordData = incomeRecordData;
    }

    class IncomeRecordViewHolder extends RecyclerView.ViewHolder {
        TextView record_list_type;
        TextView record_list_money;
        TextView record_list_date;

        IncomeRecordViewHolder(View itemView) {
            super(itemView);
            record_list_type = (TextView) itemView.findViewById(R.id.record_list_Type);
            record_list_money = (TextView) itemView.findViewById(R.id.record_list_Money);
            record_list_date = (TextView) itemView.findViewById(R.id.record_list_Date);
        }
    }

    @NonNull
    @Override
    public IncomeRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_record, parent, false);
        return new IncomeRecordAdapter.IncomeRecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeRecordViewHolder holder, int position) {
        holder.record_list_type.setText(mIncomeRecordData.get(position).getType());
        holder.record_list_money.setText(mIncomeRecordData.get(position).getIncomeMoney());
        holder.record_list_date.setText(mIncomeRecordData.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mIncomeRecordData.size();
    }
}
