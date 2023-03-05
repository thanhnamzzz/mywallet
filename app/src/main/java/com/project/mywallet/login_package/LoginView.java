package com.project.mywallet.login_package;

public interface LoginView {
    void loginSuccess();

    void loginFailed();

    void loginError(String message);

}
