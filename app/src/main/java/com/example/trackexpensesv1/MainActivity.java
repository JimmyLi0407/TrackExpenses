package com.example.trackexpensesv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView main_bottom_navigation_view;
    Fragment homeFragment;
    Fragment pieChartFragment;
    Fragment individualListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        pieChartFragment = new PieChartFragment();
        individualListFragment = new IndividualListFragment();

        main_bottom_navigation_view = findViewById(R.id.main_Bottom_Navigation_View);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_Container, homeFragment).commit();
        main_bottom_navigation_view.setSelectedItemId(R.id.home);
        main_bottom_navigation_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.home:
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction homeTransaction = manager.beginTransaction();
                    homeTransaction.replace(R.id.main_Container, homeFragment).commit();
                    break;

                case R.id.piechart:
                    FragmentTransaction pieChartTransaction = getSupportFragmentManager().beginTransaction();
                    pieChartTransaction.replace(R.id.main_Container, pieChartFragment).commit();
                    break;

                case R.id.individualList:
                    FragmentTransaction individualListTransaction = getSupportFragmentManager().beginTransaction();
                    individualListTransaction.replace(R.id.main_Container, individualListFragment).commit();
                    break;
            }
            return true;
        }
    };
}
