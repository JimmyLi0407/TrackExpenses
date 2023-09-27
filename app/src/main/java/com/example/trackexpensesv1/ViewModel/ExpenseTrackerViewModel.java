package com.example.trackexpensesv1.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.trackexpensesv1.Data.VariousExpensesData;
import com.example.trackexpensesv1.Data.VariousIncomeData;
import com.example.trackexpensesv1.DataBase.ExpensesRecord;
import com.example.trackexpensesv1.DataBase.IncomeRecord;
import com.example.trackexpensesv1.DataBase.RecordDao;
import com.example.trackexpensesv1.DataBase.RecordDataBase;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerViewModel extends AndroidViewModel {
    private RecordDataBase db;
    private RecordDao recordDao;
    private String[] incomeType = {"薪資", "股票", "利息"};
    private String[] expensesType = {"食", "衣", "住", "行"};

    private LiveData<List<IncomeRecord>> incomeRecordsAll;
    private LiveData<List<ExpensesRecord>> expensesRecordAll;

    private LiveData<Integer> totalIncomeValue;
    private LiveData<Integer> totalExpensesValue;
    private MediatorLiveData<Integer> totalAssets = new MediatorLiveData<>();
    private MediatorLiveData<Integer> totalIncome = new MediatorLiveData<>();
    private MediatorLiveData<Integer> totalExpenses = new MediatorLiveData<>();

    private LiveData<Integer> SalaryIncomeValue;
    private LiveData<Integer> StockIncomeValue;
    private LiveData<Integer> InterestIncomeValue;
    private MediatorLiveData<ArrayList<PieEntry>> IncomePieChartList = new MediatorLiveData<>();
    private MediatorLiveData<ArrayList<VariousIncomeData>> VariousIncomeList = new MediatorLiveData<>();

    private LiveData<Integer> FoodExpensesValue;
    private LiveData<Integer> ClothingExpensesValue;
    private LiveData<Integer> HousingExpensesValue;
    private LiveData<Integer> TransportationExpensesValue;
    private MediatorLiveData<ArrayList<PieEntry>> ExpensesPieChartList = new MediatorLiveData<>();
    private MediatorLiveData<ArrayList<VariousExpensesData>> VariousExpensesList = new MediatorLiveData<>();

    ExpenseTrackerViewModel(Application application) {
        super(application);
        db = RecordDataBase.getInstance(application);
        recordDao = db.recordDao();
        initIncom();
        initExpenses();
    }

    public void insertIncomeRecord(IncomeRecord record) {
        insertIncomeRecordToRoom(record);
    }

    private void insertIncomeRecordToRoom(IncomeRecord record) {
        new InsertAsyncTask(db, record).execute();
    }

    public void insertExpensesRecord(ExpensesRecord record) {
        insertExpensesRecordToRoom(record);
    }

    private void insertExpensesRecordToRoom(ExpensesRecord record) {
        new InsertAsyncTask(db, record).execute();
    }

    public LiveData<List<IncomeRecord>> getIncomeRecordsAll() {
        return incomeRecordsAll;
    }

    public LiveData<List<ExpensesRecord>> getExpensesRecordAll() {
        return expensesRecordAll;
    }

    public LiveData<Integer> getTotalIncome() {
        return totalIncome;
    }

    public LiveData<Integer> getTotalExpenses() {
        return totalExpenses;
    }

    private void initIncom() {
        incomeRecordsAll = recordDao.getIncomeRecordsAll();
        totalIncomeValue = recordDao.getIncomeTotal();

        changeIncomeTotal();

        SalaryIncomeValue = recordDao.getTypeIncomeTotal(incomeType[0]);
        StockIncomeValue = recordDao.getTypeIncomeTotal(incomeType[1]);
        InterestIncomeValue = recordDao.getTypeIncomeTotal(incomeType[2]);

        changeSalaryIncome();
        changeStockIncome();
        changeInterestIncome();
    }

    private void changeIncomeTotal() {
        totalAssets.addSource(totalIncomeValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer income) {
                Integer expenses = totalExpensesValue.getValue();
                if (income != null && expenses != null) {
                    totalAssets.setValue(income - expenses);
                } else if (income != null){
                    totalAssets.setValue(income); // income - expenses(0) = income
                } else if (expenses != null) {
                    totalAssets.setValue(0 - expenses);
                } else {
                    totalAssets.setValue(0);
                }
            }
        });

        totalIncome.addSource(totalIncomeValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer income) {
                if (income == null) {
                    totalIncome.setValue(0);
                } else {
                    totalIncome.setValue(income);
                }
            }
        });
    }

    private void initExpenses() {
        expensesRecordAll = recordDao.getExpensesRecordAll();
        totalExpensesValue = recordDao.getExpensesTotal();

        changeExpensesTotal();

        FoodExpensesValue = recordDao.getTypeExpensesTotal(expensesType[0]);
        ClothingExpensesValue = recordDao.getTypeExpensesTotal(expensesType[1]);
        HousingExpensesValue = recordDao.getTypeExpensesTotal(expensesType[2]);
        TransportationExpensesValue = recordDao.getTypeExpensesTotal(expensesType[3]);

        changeFoodExpenses();
        changeClothingExpenses();
        changeHousingExpenses();
        changeTransportationExpenses();
    }

    private void changeExpensesTotal() {
        totalAssets.addSource(totalExpensesValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer expenses) {
                Integer income = totalIncomeValue.getValue();
                if (income != null && expenses != null) {
                    totalAssets.setValue(income - expenses);
                } else if (income != null){
                    totalAssets.setValue(income); // income - expenses(0) = income
                } else if (expenses != null) {
                    totalAssets.setValue(0 - expenses);
                } else {
                    totalAssets.setValue(0);
                }
            }
        });

        totalExpenses.addSource(totalExpensesValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer expenses) {
                if (expenses == null) {
                    totalExpenses.setValue(0);
                } else {
                    totalExpenses.setValue(expenses);
                }
            }
        });
    }

    public LiveData<Integer> getTotalAssets() {
        return totalAssets;
    }

    private void changeSalaryIncome() {
        IncomePieChartList.addSource(SalaryIncomeValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer salaryIncome) {
                int AvgSalaryIncome;
                int AvgStockIncome;
                int AvgInterestIncome;

                ArrayList<PieEntry> PieChartArrayList = new ArrayList<>();
                if (totalIncomeValue.getValue() != null && totalIncomeValue.getValue() != 0) {
                    if (salaryIncome != null && salaryIncome != 0) {
                        AvgSalaryIncome = salaryIncome * 100 / totalIncomeValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgSalaryIncome, incomeType[0]));
                    }

                    if (StockIncomeValue.getValue() != null && StockIncomeValue.getValue() != 0) {
                        AvgStockIncome = StockIncomeValue.getValue() * 100 / totalIncomeValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgStockIncome, incomeType[1]));
                    }

                    if (InterestIncomeValue.getValue() != null && InterestIncomeValue.getValue() != 0) {
                        AvgInterestIncome = InterestIncomeValue.getValue() * 100 / totalIncomeValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgInterestIncome, incomeType[2]));
                    }
                    IncomePieChartList.setValue(PieChartArrayList);
                } else {
                    PieChartArrayList.add(new PieEntry(100.0F, "尚未有紀錄"));

                    IncomePieChartList.setValue(PieChartArrayList);
                }
            }
        });

        VariousIncomeList.addSource(SalaryIncomeValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer salaryIncome) {
                ArrayList<VariousIncomeData> VariousIncomeDataList = new ArrayList<>();
                if (salaryIncome != null && salaryIncome != 0) {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[0], salaryIncome));
                } else {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[0], 0));
                }

                if (StockIncomeValue.getValue() != null && StockIncomeValue.getValue() != 0) {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[1], StockIncomeValue.getValue()));
                } else {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[1], 0));
                }

                if (InterestIncomeValue.getValue() != null && InterestIncomeValue.getValue() != 0) {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[2], InterestIncomeValue.getValue()));
                } else {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[2], 0));
                }

                VariousIncomeList.setValue(VariousIncomeDataList);
            }
        });
    }

    private void changeStockIncome() {
        IncomePieChartList.addSource(StockIncomeValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer stockIncome) {
                int AvgSalaryIncome;
                int AvgStockIncome;
                int AvgInterestIncome;

                ArrayList<PieEntry> PieChartArrayList = new ArrayList<>();
                if (totalIncomeValue.getValue() != null && totalIncomeValue.getValue() != 0) {
                    if (SalaryIncomeValue.getValue() != null && SalaryIncomeValue.getValue() != 0) {
                        AvgSalaryIncome = SalaryIncomeValue.getValue() * 100 / totalIncomeValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgSalaryIncome, incomeType[0]));
                    }

                    if (stockIncome != null && stockIncome != 0) {
                        AvgStockIncome = stockIncome * 100 / totalIncomeValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgStockIncome, incomeType[1]));
                    }

                    if (InterestIncomeValue.getValue() != null && InterestIncomeValue.getValue() != 0) {
                        AvgInterestIncome = InterestIncomeValue.getValue() * 100 / totalIncomeValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgInterestIncome, incomeType[2]));
                    }
                    IncomePieChartList.setValue(PieChartArrayList);
                } else {
                    PieChartArrayList.add(new PieEntry(100.0F, "尚未有紀錄"));

                    IncomePieChartList.setValue(PieChartArrayList);
                }
            }
        });

        VariousIncomeList.addSource(StockIncomeValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer stockIncome) {
                ArrayList<VariousIncomeData> VariousIncomeDataList = new ArrayList<>();
                if (SalaryIncomeValue.getValue() != null && SalaryIncomeValue.getValue() != 0) {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[0], SalaryIncomeValue.getValue()));
                } else {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[0], 0));
                }

                if (stockIncome != null && stockIncome != 0) {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[1], stockIncome));
                } else {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[1], 0));
                }

                if (InterestIncomeValue.getValue() != null && InterestIncomeValue.getValue() != 0) {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[2], InterestIncomeValue.getValue()));
                } else {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[2], 0));
                }

                VariousIncomeList.setValue(VariousIncomeDataList);
            }
        });
    }

    private void changeInterestIncome() {
        IncomePieChartList.addSource(InterestIncomeValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer interestIncome) {
                int AvgSalaryIncome;
                int AvgStockIncome;
                int AvgInterestIncome;

                ArrayList<PieEntry> PieChartArrayList = new ArrayList<>();
                if (totalIncomeValue.getValue() != null && totalIncomeValue.getValue() != 0) {
                    if (SalaryIncomeValue.getValue() != null && SalaryIncomeValue.getValue() != 0) {
                        AvgSalaryIncome = SalaryIncomeValue.getValue() * 100 / totalIncomeValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgSalaryIncome, incomeType[0]));
                    }

                    if (StockIncomeValue.getValue() != null && StockIncomeValue.getValue() != 0) {
                        AvgStockIncome = StockIncomeValue.getValue() * 100 / totalIncomeValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgStockIncome, incomeType[1]));
                    }

                    if (interestIncome != null && interestIncome != 0) {
                        AvgInterestIncome = interestIncome * 100 / totalIncomeValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgInterestIncome, incomeType[2]));
                    }
                    IncomePieChartList.setValue(PieChartArrayList);
                } else {
                    PieChartArrayList.add(new PieEntry(100.0F, "尚未有紀錄"));

                    IncomePieChartList.setValue(PieChartArrayList);
                }
            }
        });

        VariousIncomeList.addSource(InterestIncomeValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer interestIncome) {
                ArrayList<VariousIncomeData> VariousIncomeDataList = new ArrayList<>();
                if (SalaryIncomeValue.getValue() != null && SalaryIncomeValue.getValue() != 0) {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[0], SalaryIncomeValue.getValue()));
                } else {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[0], 0));
                }

                if (StockIncomeValue.getValue() != null && StockIncomeValue.getValue() != 0) {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[1], StockIncomeValue.getValue()));
                } else {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[1], 0));
                }

                if (interestIncome != null && interestIncome != 0) {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[2], interestIncome));
                } else {
                    VariousIncomeDataList.add(new VariousIncomeData(incomeType[2], 0));
                }

                VariousIncomeList.setValue(VariousIncomeDataList);
            }
        });
    }

    public LiveData<ArrayList<PieEntry>> getIncomePieChartList() {
        return IncomePieChartList;
    }

    public LiveData<ArrayList<VariousIncomeData>> getVariousIncomeList() {
        return VariousIncomeList;
    }

    private void changeFoodExpenses() {
        ExpensesPieChartList.addSource(FoodExpensesValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer foodExpenses) {
                int AvgFoodExpenses;
                int AvgClothingExpenses;
                int AvgHousingExpenses;
                int AvgTransportationExpenses;

                ArrayList<PieEntry> PieChartArrayList = new ArrayList<>();
                if (totalExpensesValue.getValue() != null && totalExpensesValue.getValue() != 0) {
                    if (foodExpenses != null && foodExpenses != 0) {
                        AvgFoodExpenses = foodExpenses * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgFoodExpenses, expensesType[0]));
                    }

                    if (ClothingExpensesValue.getValue() != null && ClothingExpensesValue.getValue() != 0) {
                        AvgClothingExpenses = ClothingExpensesValue.getValue() * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgClothingExpenses, expensesType[1]));
                    }

                    if (HousingExpensesValue.getValue() != null && HousingExpensesValue.getValue() != 0) {
                        AvgHousingExpenses = HousingExpensesValue.getValue() * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgHousingExpenses, expensesType[2]));
                    }

                    if (TransportationExpensesValue.getValue() != null && TransportationExpensesValue.getValue() != 0) {
                        AvgTransportationExpenses = TransportationExpensesValue.getValue() * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgTransportationExpenses, expensesType[3]));
                    }

                    ExpensesPieChartList.setValue(PieChartArrayList);
                } else {
                    PieChartArrayList.add(new PieEntry(100.0F, "尚未有紀錄"));

                    ExpensesPieChartList.setValue(PieChartArrayList);
                }
            }
        });

        VariousExpensesList.addSource(FoodExpensesValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer foodExpenses) {
                ArrayList<VariousExpensesData> VariousExpenseDatasList = new ArrayList<>();
                if (foodExpenses != null && foodExpenses != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[0], foodExpenses));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[0], 0));
                }

                if (ClothingExpensesValue.getValue() != null && ClothingExpensesValue.getValue() != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[1], ClothingExpensesValue.getValue()));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[1], 0));
                }

                if (HousingExpensesValue.getValue() != null && HousingExpensesValue.getValue() != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[2], HousingExpensesValue.getValue()));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[2], 0));
                }

                if (TransportationExpensesValue.getValue() != null && TransportationExpensesValue.getValue() != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[3], TransportationExpensesValue.getValue()));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[3], 0));
                }

                VariousExpensesList.setValue(VariousExpenseDatasList);
            }
        });
    }

    private void changeClothingExpenses() {
        ExpensesPieChartList.addSource(ClothingExpensesValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer clothingExpenses) {
                int AvgFoodExpenses;
                int AvgClothingExpenses;
                int AvgHousingExpenses;
                int AvgTransportationExpenses;

                ArrayList<PieEntry> PieChartArrayList = new ArrayList<>();
                if (totalExpensesValue.getValue() != null && totalExpensesValue.getValue() != 0) {
                    if (FoodExpensesValue.getValue() != null && FoodExpensesValue.getValue() != 0) {
                        AvgFoodExpenses = FoodExpensesValue.getValue() * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgFoodExpenses, expensesType[0]));
                    }

                    if (clothingExpenses != null && clothingExpenses != 0) {
                        AvgClothingExpenses = clothingExpenses * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgClothingExpenses, expensesType[1]));
                    }

                    if (HousingExpensesValue.getValue() != null && HousingExpensesValue.getValue() != 0) {
                        AvgHousingExpenses = HousingExpensesValue.getValue() * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgHousingExpenses, expensesType[2]));
                    }

                    if (TransportationExpensesValue.getValue() != null && TransportationExpensesValue.getValue() != 0) {
                        AvgTransportationExpenses = TransportationExpensesValue.getValue() * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgTransportationExpenses, expensesType[3]));
                    }

                    ExpensesPieChartList.setValue(PieChartArrayList);
                } else {
                    PieChartArrayList.add(new PieEntry(100.0F, "尚未有紀錄"));

                    ExpensesPieChartList.setValue(PieChartArrayList);
                }
            }
        });

        VariousExpensesList.addSource(ClothingExpensesValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer clothingExpenses) {
                ArrayList<VariousExpensesData> VariousExpenseDatasList = new ArrayList<>();
                if (FoodExpensesValue.getValue() != null && FoodExpensesValue.getValue() != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[0], FoodExpensesValue.getValue()));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[0], 0));
                }

                if (clothingExpenses != null && clothingExpenses != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[1], clothingExpenses));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[1], 0));
                }

                if (HousingExpensesValue.getValue() != null && HousingExpensesValue.getValue() != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[2], HousingExpensesValue.getValue()));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[2], 0));
                }

                if (TransportationExpensesValue.getValue() != null && TransportationExpensesValue.getValue() != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[3], TransportationExpensesValue.getValue()));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[3], 0));
                }

                VariousExpensesList.setValue(VariousExpenseDatasList);
            }
        });
    }

    private void changeHousingExpenses() {
        ExpensesPieChartList.addSource(HousingExpensesValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer housingExpenses) {
                int AvgFoodExpenses;
                int AvgClothingExpenses;
                int AvgHousingExpenses;
                int AvgTransportationExpenses;

                ArrayList<PieEntry> PieChartArrayList = new ArrayList<>();
                if (totalExpensesValue.getValue() != null && totalExpensesValue.getValue() != 0) {
                    if (FoodExpensesValue.getValue() != null && FoodExpensesValue.getValue() != 0) {
                        AvgFoodExpenses = FoodExpensesValue.getValue() * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgFoodExpenses, expensesType[0]));
                    }

                    if (ClothingExpensesValue.getValue() != null && ClothingExpensesValue.getValue() != 0) {
                        AvgClothingExpenses = ClothingExpensesValue.getValue() * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgClothingExpenses, expensesType[1]));
                    }

                    if (housingExpenses != null && housingExpenses != 0) {
                        AvgHousingExpenses = housingExpenses * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgHousingExpenses, expensesType[2]));
                    }

                    if (TransportationExpensesValue.getValue() != null && TransportationExpensesValue.getValue() != 0) {
                        AvgTransportationExpenses = TransportationExpensesValue.getValue() * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgTransportationExpenses, expensesType[3]));
                    }

                    ExpensesPieChartList.setValue(PieChartArrayList);
                } else {
                    PieChartArrayList.add(new PieEntry(100.0F, "尚未有紀錄"));

                    ExpensesPieChartList.setValue(PieChartArrayList);
                }
            }
        });

        VariousExpensesList.addSource(HousingExpensesValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer housingExpenses) {
                ArrayList<VariousExpensesData> VariousExpenseDatasList = new ArrayList<>();
                if (FoodExpensesValue.getValue() != null && FoodExpensesValue.getValue() != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[0], FoodExpensesValue.getValue()));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[0], 0));
                }

                if (ClothingExpensesValue.getValue() != null && ClothingExpensesValue.getValue() != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[1], ClothingExpensesValue.getValue()));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[1], 0));
                }

                if (housingExpenses != null && housingExpenses != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[2], housingExpenses));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[2], 0));
                }

                if (TransportationExpensesValue.getValue() != null && TransportationExpensesValue.getValue() != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[3], TransportationExpensesValue.getValue()));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[3], 0));
                }

                VariousExpensesList.setValue(VariousExpenseDatasList);
            }
        });
    }

    private void changeTransportationExpenses() {
        ExpensesPieChartList.addSource(TransportationExpensesValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer transportationExpenses) {
                int AvgFoodExpenses;
                int AvgClothingExpenses;
                int AvgHousingExpenses;
                int AvgTransportationExpenses;

                ArrayList<PieEntry> PieChartArrayList = new ArrayList<>();
                if (totalExpensesValue.getValue() != null && totalExpensesValue.getValue() != 0) {
                    if (FoodExpensesValue.getValue() != null && FoodExpensesValue.getValue() != 0) {
                        AvgFoodExpenses = FoodExpensesValue.getValue() * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgFoodExpenses, expensesType[0]));
                    }

                    if (ClothingExpensesValue.getValue() != null && ClothingExpensesValue.getValue() != 0) {
                        AvgClothingExpenses = ClothingExpensesValue.getValue() * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgClothingExpenses, expensesType[1]));
                    }

                    if (HousingExpensesValue.getValue() != null && HousingExpensesValue.getValue() != 0) {
                        AvgHousingExpenses = HousingExpensesValue.getValue() * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgHousingExpenses, expensesType[2]));
                    }

                    if (transportationExpenses != null && transportationExpenses != 0) {
                        AvgTransportationExpenses = transportationExpenses * 100 / totalExpensesValue.getValue();
                        PieChartArrayList.add(new PieEntry(AvgTransportationExpenses, expensesType[3]));
                    }

                    ExpensesPieChartList.setValue(PieChartArrayList);
                } else {
                    PieChartArrayList.add(new PieEntry(100.0F, "尚未有紀錄"));

                    ExpensesPieChartList.setValue(PieChartArrayList);
                }
            }
        });

        VariousExpensesList.addSource(TransportationExpensesValue, new Observer<Integer>() {
            @Override
            public void onChanged(Integer transportationExpenses) {
                ArrayList<VariousExpensesData> VariousExpenseDatasList = new ArrayList<>();
                if (FoodExpensesValue.getValue() != null && FoodExpensesValue.getValue() != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[0], FoodExpensesValue.getValue()));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[0], 0));
                }

                if (ClothingExpensesValue.getValue() != null && ClothingExpensesValue.getValue() != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[1], ClothingExpensesValue.getValue()));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[1], 0));
                }

                if (HousingExpensesValue.getValue() != null && HousingExpensesValue.getValue() != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[2], HousingExpensesValue.getValue()));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[2], 0));
                }

                if (transportationExpenses != null && transportationExpenses != 0) {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[3], transportationExpenses));
                } else {
                    VariousExpenseDatasList.add(new VariousExpensesData(expensesType[3], 0));
                }

                VariousExpensesList.setValue(VariousExpenseDatasList);
            }
        });
    }

    public LiveData<ArrayList<PieEntry>> getExpensesPieChartList() {
        return ExpensesPieChartList;
    }

    public LiveData<ArrayList<VariousExpensesData>> getVariousExpensesList() {
        return VariousExpensesList;
    }
}
