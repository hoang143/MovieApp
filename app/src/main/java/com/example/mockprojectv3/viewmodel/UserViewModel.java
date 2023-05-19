package com.example.mockprojectv3.viewmodel;

import android.net.ConnectivityManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mockprojectv3.R;
import com.example.mockprojectv3.service.FirebaseService;
import com.example.mockprojectv3.service.State;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserViewModel extends ViewModel {
    private FirebaseService firebaseService;
    private MutableLiveData<State<FirebaseUser>> currentUserState;

    public UserViewModel() {
        firebaseService = FirebaseService.getInstance();
        currentUserState = firebaseService.getCurrentUser();
    }


    public void signUp(String email, String password) {
        firebaseService.signUp(email, password);
    }

    public void signIn(String email, String password) {
        email = email.trim();
        password = password.trim();
        firebaseService.signIn(email, password);
    }

    public void upDateProfile(UserProfileChangeRequest userProfileChangeRequest){
        firebaseService.updateProfile(userProfileChangeRequest);
    }

    public void signOut() {
        firebaseService.signOut();
    }

    public MutableLiveData<State<FirebaseUser>> getCurrentUserState() {
        return currentUserState;
    }

    public void navigateTo(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.containerFragment, fragment)
                .commit();
    }
}
