package com.example.mockprojectv3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mockprojectv3.ui.main.FavouriteFragment;
import com.example.mockprojectv3.ui.main.HomeFragment;
import com.example.mockprojectv3.ui.main.NotificationFragment;
import com.example.mockprojectv3.ui.main.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    private ProfileFragment profileFragment = new ProfileFragment();
    public static final int MY_REQUEST_CODE = 10;
    HomeFragment homeFragment = new HomeFragment();
    FavouriteFragment favouriteFragment = new FavouriteFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                Intent intent = result.getData();
                if(intent == null){
                    return;
                }
                Uri uri = intent.getData();
                Log.v("Debug", uri.toString());
                profileFragment.setmUri(uri);
                Log.v("Debug", "in");
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    profileFragment.setBitMapImageView(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setVisibility(View.GONE);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0){

                openGallery();
            }else {
                Toast.makeText(this, "Please give us permission to continue", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }
}