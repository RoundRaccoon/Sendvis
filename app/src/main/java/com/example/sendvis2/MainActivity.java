package com.example.sendvis2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
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
        View navigationGroupsView = (View) findViewById(R.id.navigation_groups);
        View navigationActivityView = (View) findViewById(R.id.navigation_activity);
        navigationHomeView.setBackgroundColor(getResources().getColor(R.color.red));
        navigationGroupsView.setBackgroundColor(getResources().getColor(R.color.orange));
        navigationActivityView.setBackgroundColor(getResources().getColor(R.color.orange));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification Channel Name";
            String description = "Notification Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("NEO STICKS 3+1 GRATIS")
                .setContentText("Available until 03:22 PM")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());
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
                            navigationHomeView.setBackgroundColor(getResources().getColor(R.color.red));
                            navigationGroupsView.setBackgroundColor(getResources().getColor(R.color.orange));
                            navigationActivityView.setBackgroundColor(getResources().getColor(R.color.orange));
                            break;
                        case R.id.navigation_groups:
                            selectedFragment = new GroupsFragment();
                            navigationHomeView.setBackgroundColor(getResources().getColor(R.color.orange));
                            navigationGroupsView.setBackgroundColor(getResources().getColor(R.color.red));
                            navigationActivityView.setBackgroundColor(getResources().getColor(R.color.orange));
                            break;
                        case R.id.navigation_activity:
                            selectedFragment = new LedgerFragment();
                            navigationHomeView.setBackgroundColor(getResources().getColor(R.color.orange));
                            navigationGroupsView.setBackgroundColor(getResources().getColor(R.color.orange));
                            navigationActivityView.setBackgroundColor(getResources().getColor(R.color.red));
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return false;
                }
            };
}