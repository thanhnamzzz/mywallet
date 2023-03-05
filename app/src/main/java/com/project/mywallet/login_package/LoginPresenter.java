package com.project.mywallet.login_package;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
