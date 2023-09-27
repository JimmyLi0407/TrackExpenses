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

import com.example.trackexpensesv1.DataBase.ExpensesRecord;
import com.example.trackexpensesv1.ViewModel.ExpenseTrackerViewModel;
import com.example.trackexpensesv1.ViewModel.ViewModelFactory;

import java.util.Calendar;

public class SaveExpensesActivity extends AppCompatActivity {
    private ExpenseTrackerViewModel viewModel;

    Toolbar save_expenses_toolbar;
    RadioGroup save_expenses_radioGroup;
    RadioButton save_expenses_foodExpenses;
    RadioButton save_expenses_clothingExpenses;
    RadioButton save_expenses_housingExpenses;
    RadioButton save_expenses_transportationExpenses;
    EditText save_expenses_money;
    TextView save_expenses_dateTextView;
    Button save_expenses_dateButton;
    Button save_expenses_saveButton;
    Button save_expenses_clearButton;

    private String yearmonth;
    private String type;
    private String cost;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_expenses);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ExpenseTrackerViewModel.class);
        setToolbar();
        initView();
    }

    private void setToolbar() {
        save_expenses_toolbar = (Toolbar) findViewById(R.id.save_expenses_Toolbar);
        save_expenses_toolbar.setTitle("新增支出");
    }

    private void initView() {
        save_expenses_radioGroup = (RadioGroup) findViewById(R.id.save_expenses_RadioGroup);
        save_expenses_foodExpenses = (RadioButton) findViewById(R.id.save_expenses_FoodExpenses);
        save_expenses_clothingExpenses = (RadioButton) findViewById(R.id.save_expenses_ClothingExpenses);
        save_expenses_housingExpenses = (RadioButton) findViewById(R.id.save_expenses_HousingExpenses);
        save_expenses_transportationExpenses = (RadioButton) findViewById(R.id.save_expenses_TransportationExpenses);

        save_expenses_money = (EditText) findViewById(R.id.save_expenses_Money);
        save_expenses_dateTextView = (TextView) findViewById(R.id.save_expenses_DateTextView);
        save_expenses_dateButton = (Button) findViewById(R.id.save_expenses_DateButton);
        save_expenses_saveButton = (Button) findViewById(R.id.save_expenses_SaveButton);
        save_expenses_clearButton = (Button) findViewById(R.id.save_expenses_ClearButton);

        save_expenses_dateTextView.setText("支出日期:");
        save_expenses_dateButton.setText(getCurrentDate());

        if (save_expenses_radioGroup.getCheckedRadioButtonId() == R.id.save_expenses_FoodExpenses) {
            type = save_expenses_foodExpenses.getText().toString();
        }

        save_expenses_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.save_expenses_FoodExpenses:
                        type = save_expenses_foodExpenses.getText().toString();
                        break;

                    case R.id.save_expenses_ClothingExpenses:
                        type = save_expenses_clothingExpenses.getText().toString();
                        break;

                    case R.id.save_expenses_HousingExpenses:
                        type = save_expenses_housingExpenses.getText().toString();
                        break;

                    case R.id.save_expenses_TransportationExpenses:
                        type = save_expenses_transportationExpenses.getText().toString();
                        break;
                }
            }
        });
    }

    public void onDateClick(View view) {
        showDatePickerDialog();
    }

    public void onSaveClick(View view) {
        cost = save_expenses_money.getText().toString();
        if (cost.equals("") || cost.charAt(0) == '0') {
            Toast.makeText(this, "請正確輸入金額!!", Toast.LENGTH_SHORT).show();
        } else {
            date = save_expenses_dateButton.getText().toString();
            String[] dateData = date.split("/");
            yearmonth = dateData[0] + "/" + dateData[1];
            viewModel.insertExpensesRecord(new ExpensesRecord(yearmonth, type, cost, date));
            finish();
        }
    }

    public void onClearClick(View view) {
        save_expenses_dateButton.setText(getCurrentDate());
        save_expenses_money.setText("");
        save_expenses_radioGroup.check(R.id.save_expenses_FoodExpenses);
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
                        save_expenses_dateButton.setText(format);
                    }
                }, currentYear, currentMonth, currentDay);

        datePickerDialog.show();
    }
}
