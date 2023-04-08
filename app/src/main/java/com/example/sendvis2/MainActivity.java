package com.example.sendvis2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment selectedFragment = null;
        selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        View navigationHomeView = (View) findViewById(R.id.navigation_home); // merge linia asta trust me
        navigationHomeView.setBackgroundColor(getResources().getColor(R.color.orange));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    View navigationHomeView = (View) findViewById(R.id.navigation_home);
                    View navigationGroupsView = (View) findViewById(R.id.navigation_groups);
                    View navigationActivityView = (View) findViewById(R.id.navigation_activity);

                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            navigationHomeView.setBackgroundColor(getResources().getColor(R.color.orange));
                            navigationGroupsView.setBackgroundColor(getResources().getColor(R.color.white));
                            navigationActivityView.setBackgroundColor(getResources().getColor(R.color.white));
                            break;
                        case R.id.navigation_groups:
                            selectedFragment = new GroupsFragment();
                            navigationHomeView.setBackgroundColor(getResources().getColor(R.color.white));
                            navigationGroupsView.setBackgroundColor(getResources().getColor(R.color.orange));
                            navigationActivityView.setBackgroundColor(getResources().getColor(R.color.white));
                            break;
                        case R.id.navigation_activity:
                            selectedFragment = new LedgerFragment();
                            navigationHomeView.setBackgroundColor(getResources().getColor(R.color.white));
                            navigationGroupsView.setBackgroundColor(getResources().getColor(R.color.white));
                            navigationActivityView.setBackgroundColor(getResources().getColor(R.color.orange));
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return false;
                }
            };
}