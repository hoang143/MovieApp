package com.example.mockprojectv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mockprojectv3.ui.main.FavouriteFragment;
import com.example.mockprojectv3.ui.main.HomeFragment;
import com.example.mockprojectv3.ui.main.NotificationFragment;
import com.example.mockprojectv3.ui.main.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    ProfileFragment profileFragment = new ProfileFragment();
    HomeFragment homeFragment = new HomeFragment();
    FavouriteFragment favouriteFragment = new FavouriteFragment();
    NotificationFragment notificationFragment = new NotificationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, homeFragment).commit();
                return true;

            case R.id.favourite:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, favouriteFragment).commit();
                return true;

            case R.id.notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, notificationFragment).commit();
                return true;

            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, profileFragment).commit();
                return true;
        }
        return false;
    }
}