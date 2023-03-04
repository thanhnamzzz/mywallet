package com.project.mywallet.login_packega;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class LoginPresenter {

    private LoginView loginView;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    public void login(String username, String password) {
        if (username == null || username.isEmpty() || username.trim().length() == 0 || password == null || password.isEmpty() || password.trim().length() == 0) {
            loginView.loginError("Email và Password không được bỏ trống.");
        } else {
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginView.loginSuccess();
                        } else {
                            loginView.loginFailed();
                        }
                    });
        }
    }
}
