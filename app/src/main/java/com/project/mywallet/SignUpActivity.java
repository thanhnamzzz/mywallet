package com.project.mywallet;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.project.mywallet.login_package.SignUpPresenter;
import com.project.mywallet.login_package.SignUpView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, SignUpView {

    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword1)
    EditText edtPassword1;
    @BindView(R.id.edtPassword2)
    EditText edtPassword2;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.btnCancel)
    Button btnCancel;

    private FirebaseAuth mAuth;
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle("Đăng ký");
        }
        mAuth = FirebaseAuth.getInstance();
        inView();
    }

    private void inView() {
        ButterKnife.bind(this);
        edtPassword1.setText(null);
        edtPassword2.setText(null);

        signUpPresenter = new SignUpPresenter(this);
        btnSignUp.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                signUp();
                break;
            case R.id.btnCancel:
                onBackPressed();
                break;
        }
    }

    private void signUp() {
        String email = edtEmail.getText().toString().trim();
        String password1 = edtPassword1.getText().toString().trim();
        String password2 = edtPassword2.getText().toString().trim();
        signUpPresenter.signUp(email, password1, password2);
    }

    @Override
    public void registerSuccess() {
        messageSignUp("Đăng ký thành công");
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void registerFailed() {
        messageSignUp("Đăng ký không thành công");
    }

    @Override
    public void onEmailError(String message) {
        edtEmail.setError(message);
    }

    @Override
    public void onPasswordError1(String message) {
        edtPassword1.setError(message);
    }

    @Override
    public void onPasswordError2(String message) {
        edtPassword2.setError(message);
    }

    @Override
    public void onPasswordCheck(String message) {
        edtPassword2.setError(message);
    }

    private void messageSignUp(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
}