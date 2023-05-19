package com.example.mockprojectv3.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FirebaseService {
    private static FirebaseService instance;
    public static FirebaseService getInstance() {
        if (instance == null) {
            instance = new FirebaseService();
        }
        return instance;
    }

    private FirebaseAuth mAuth;
    private MutableLiveData<State<FirebaseUser>> mCurrentUser;

    private FirebaseService() {
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = new MutableLiveData<>();
    }

    public void signUp(String email, String password) {
        mCurrentUser.setValue(State.loading());

        if (email.isEmpty() || password.isEmpty()) {
            mCurrentUser.setValue(State.error("Email and password cannot be empty."));
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mCurrentUser.setValue(State.error(e.getMessage()));
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            mCurrentUser.setValue(State.success(user));
                        } else {
                            String errorMessage = task.getException().getMessage();
                            if (errorMessage.contains("password is invalid")) {
                                mCurrentUser.setValue(State.error("Invalid email or password."));
                            } else {
                                mCurrentUser.setValue(State.error(errorMessage));
                            }
                        }
                    }
                });
    }

    public void updateProfile(UserProfileChangeRequest profileUpdates) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            return;
        }

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mCurrentUser.setValue(State.success(user));
                        } else {
                            String errorMessage = task.getException().getMessage();
                            mCurrentUser.setValue(State.error(errorMessage));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = e.getMessage();
                        mCurrentUser.setValue(State.error(errorMessage));
                    }
                });
    }



    public void signIn(String email, String password) {
        mCurrentUser.setValue(State.loading());

        if (email.isEmpty() || password.isEmpty()) {
            mCurrentUser.setValue(State.error("Email and password cannot be empty."));
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            mCurrentUser.setValue(State.success(user));
                        } else {
                            String errorMessage = task.getException().getMessage();
                            if (errorMessage.contains("password is invalid") ||
                                    errorMessage.contains("There is no user record corresponding to this identifier")) {
                                mCurrentUser.setValue(State.error("Invalid email or password."));
                            } else {
                                mCurrentUser.setValue(State.error(errorMessage));
                            }
                        }
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();
        mCurrentUser.setValue(State.success(null));
    }

    public MutableLiveData<State<FirebaseUser>> getCurrentUser() {
        return mCurrentUser;
    }
}
