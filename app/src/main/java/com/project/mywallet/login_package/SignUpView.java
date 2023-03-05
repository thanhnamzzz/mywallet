package com.project.mywallet.login_package;

public interface SignUpView {
    void registerSuccess();

    void registerFailed();

    void onEmailError(String message);

    void onPasswordError1(String message);

    void onPasswordError2(String message);

    void onPasswordCheck(String message);
}
