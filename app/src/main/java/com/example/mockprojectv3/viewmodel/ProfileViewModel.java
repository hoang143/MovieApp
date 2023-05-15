package com.example.mockprojectv3.viewmodel;

import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel  {
    private String userName;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ProfileViewModel(String userName) {
        this.userName = userName;
    }
}
