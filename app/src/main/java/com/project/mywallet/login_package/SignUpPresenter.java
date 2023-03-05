package com.project.mywallet.login_package;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPresenter {

    private SignUpView signUpView;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public SignUpPresenter(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    public void signUp(String email, String password1, String password2) {
        if (email == null || email.isEmpty() || email.trim().length() == 0) {
            signUpView.onEmailError("Email không được để trống");
            return;
        }
        if (!validate(email)) {
            signUpView.onEmailError("Điền đúng định dạng Email");
            return;
        }
        if (password1 == null || password1.isEmpty() || password1.trim().length() == 0) {
            signUpView.onPasswordError1("Mật khẩu không được để trống");
            return;
        }
        if (password2 == null || password2.isEmpty() || password2.trim().length() == 0) {
            signUpView.onPasswordError2("Mật khẩu không được để trống");
            return;
        }
        if (password1.length()<=5) {
            signUpView.onPasswordError1("Mật khẩu phải có ít nhất 6 ký tự");
        }
        if (password2.length()<=5) {
            signUpView.onPasswordError2("Mật khẩu phải có ít nhất 6 ký tự");
        }
        if (password1.equals(password2)) {
            createAccount(email, password1);
        } else {
            signUpView.onPasswordCheck("Mật khẩu nhắc lại không khớp");
        }
    }

    private void createAccount(String email, String password1) {
        mAuth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            signUpView.registerSuccess();
                        } else {
                            signUpView.registerFailed();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                        signUpView.registerFailed();
                    }
                });
    }

    private boolean validate(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
