package com.example.mockprojectv3.ui.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mockprojectv3.R;
import com.example.mockprojectv3.databinding.FragmentSplashBinding;
import com.example.mockprojectv3.service.State;
import com.example.mockprojectv3.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashFragment extends Fragment {
    private static final long DELAY_TIME = 2000; // 2 giây
    private FragmentSplashBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = binding.ivSplash;
        Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom);
        imageView.startAnimation(animation);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToNextFragment();
//                navigateTo(new HomeFragment());
            }
        }, DELAY_TIME);
    }

    private void navigateToNextFragment() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       if(user == null){
           navigateTo(new LoginFragment());
       }else {
           navigateTo(new HomeFragment());
       }
    }

    private void navigateTo(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.containerFragment, fragment)
                .commit();
    }
}
