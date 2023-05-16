package com.example.mockprojectv3.ui.main;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mockprojectv3.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    Button btnLogIn;
    EditText etMail, etPassword;
    TextView tvSignup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_login,container, false);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.GONE);
        // Inflate the layout for this fragment
//        btnLogIn = view.findViewById(R.id.btnLogin);
        initUI(view);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(LoginFragmentDirections.login2home());
                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(LoginFragmentDirections.login2signup());
                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
                bottomNavigationView.setVisibility(View.GONE);
            }
        });




        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void initUI(View view){
        btnLogIn = view.findViewById(R.id.btnLogin);
        tvSignup = view.findViewById(R.id.tvSignUp);
        etMail = view.findViewById(R.id.etUserNameLogin);
        etPassword = view.findViewById(R.id.etPasswordLogin);
    }
}