package com.example.trackexpensesv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackexpensesv1.DataBase.IncomeRecord;
import com.example.trackexpensesv1.ViewModel.ExpenseTrackerViewModel;
import com.example.trackexpensesv1.ViewModel.ViewModelFactory;

import java.util.Calendar;

public class SaveIncomeActivity extends AppCompatActivity {
    private ExpenseTrackerViewModel viewModel;

    Toolbar save_income_toolbar;
    RadioGroup save_income_radioGroup;
    RadioButton save_income_salaryIncome;
    RadioButton save_income_stockIncome;
    RadioButton save_income_interestIncome;
    EditText save_income_money;
    TextView save_income_dateTextView;
    Button save_income_dateButton;
    Button save_income_saveButton;
    Button save_income_clearButton;

    private String yearmonth;
    private String type;
    private String incomeAmount;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_income);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ExpenseTrackerViewModel.class);
        setToolbar();
        initView();
    }

    private void setToolbar() {
        save_income_toolbar = (Toolbar) findViewById(R.id.save_income_Toolbar);
        save_income_toolbar.setTitle("新增收入");
    }

    private void initView() {
        save_income_radioGroup = (RadioGroup) findViewById(R.id.save_income_RadioGroup);
        save_income_salaryIncome = (RadioButton) findViewById(R.id.save_income_SalaryIncome);
        save_income_stockIncome = (RadioButton) findViewById(R.id.save_income_StockIncome);
        save_income_interestIncome = (RadioButton) findViewById(R.id.save_income_InterestIncome);

        save_income_money = (EditText) findViewById(R.id.save_income_Money);
        save_income_dateTextView = (TextView) findViewById(R.id.save_income_DateTextView);
        save_income_dateButton = (Button) findViewById(R.id.save_income_DateButton);
        save_income_saveButton = (Button) findViewById(R.id.save_income_SaveButton);
        save_income_clearButton = (Button) findViewById(R.id.save_income_ClearButton);

        save_income_dateTextView.setText("支出日期:");
        save_income_dateButton.setText(getCurrentDate());

        if (save_income_radioGroup.getCheckedRadioButtonId() == R.id.save_income_SalaryIncome) {
            type = save_income_salaryIncome.getText().toString();
        }

        save_income_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.save_income_SalaryIncome:
                        type = save_income_salaryIncome.getText().toString();
                        break;

                    case R.id.save_income_StockIncome:
                        type = save_income_stockIncome.getText().toString();
                        break;

                    case R.id.save_income_InterestIncome:
                        type = save_income_interestIncome.getText().toString();
                        break;
                }
            }
        });
    }

    public void onDateClick(View view) {
        showDatePickerDialog();
    }

    public void onSaveClick(View view) {
        incomeAmount = save_income_money.getText().toString();
        if (incomeAmount.equals("") || incomeAmount.charAt(0) == '0') {
            Toast.makeText(this, "請正確輸入金額!!", Toast.LENGTH_SHORT).show();
        } else {
            date = save_income_dateButton.getText().toString();
            String[] dateData = date.split("/");
            yearmonth = dateData[0] + "/" + dateData[1];
            viewModel.insertIncomeRecord(new IncomeRecord(yearmonth, type, incomeAmount, date));
            finish();
        }
    }

    public void onClearClick(View view) {
        save_income_dateButton.setText(getCurrentDate());
        save_income_money.setText("");
        save_income_radioGroup.check(R.id.save_income_SalaryIncome);
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        return mYear + "/" + pad(mMonth+1) + "/" + pad(mDay);
    }

    private String pad(int number) {
        if (number >= 10) {
            return String.valueOf(number);
        } else {
            return "0" + number;
        }
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String format = year +"/"+ pad(month+1) +"/"+ pad(dayOfMonth);
                        save_income_dateButton.setText(format);
                    }
                }, currentYear, currentMonth, currentDay);

        datePickerDialog.show();
    }
}
