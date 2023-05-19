package com.example.mockprojectv3.viewmodel;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.mockprojectv3.R;
import com.example.mockprojectv3.ui.main.HomeFragment;
import com.example.mockprojectv3.ui.main.LoginFragment;
import com.example.mockprojectv3.ui.main.SplashFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashViewModel extends ViewModel {
    public void AutoNavigateFragment(FragmentManager fragmentManager){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                NextFragment(fragmentManager);
            }
        }, 2000);
    }

    private void NextFragment(FragmentManager fragmentManager) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            fragmentManager.beginTransaction()
                    .replace(R.id.containerFragment, new LoginFragment())
                    .commit();
        }else {
            fragmentManager.beginTransaction()
                    .replace(R.id.containerFragment, new HomeFragment())
                    .commit();
        }
    }

}
