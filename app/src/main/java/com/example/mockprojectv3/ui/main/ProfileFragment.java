package com.example.mockprojectv3.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mockprojectv3.R;
import com.example.mockprojectv3.databinding.FragmentProfileBinding;
import com.example.mockprojectv3.viewmodel.ProfileViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    FragmentProfileBinding mFragmentProfileBinding;
    private ProfileViewModel mProfileViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(this.getContext(), "ini", Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        mFragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);

        mProfileViewModel = new ProfileViewModel("Mr.Sunday is here");

        mFragmentProfileBinding.setProfileViewModel(mProfileViewModel);

        return mFragmentProfileBinding.getRoot();
    }

}