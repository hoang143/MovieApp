package com.example.mockprojectv3.ui.main;

import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mockprojectv3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    private ProgressDialog progressDialog;
    Button btnSignup;
    EditText etMail, etPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_signup,container, false);
        // Inflate the layout for this fragment
        btnSignup = view.findViewById(R.id.buttonSignUp);
        etMail = view.findViewById(R.id.editTextMailSignup);
        etPassword = view.findViewById(R.id.editTextPasswordSignup);
        progressDialog = new ProgressDialog(getContext());
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUpClick();
            }
        });
        
        return view;
    }

    private void onSignUpClick() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email = etMail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Navigation.findNavController(getView()).navigate(SignupFragmentDirections.signup2home());
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Failed to auth", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}