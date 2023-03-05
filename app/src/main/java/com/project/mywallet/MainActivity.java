package com.project.mywallet;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.mywallet.login_package.LoginPresenter;
import com.project.mywallet.login_package.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoginView {
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogIn)
    Button btnLogIn;
    @BindView(R.id.btnSignUp)
    TextView btnSignUp;

    private FirebaseAuth mAuth;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        inView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.d("TAG", "onStart: currentUser: " + currentUser.getEmail());
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    private void inView() {
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Đăng nhập");
        }

        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);

        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                register();
                break;
            case R.id.btnLogIn:
                login();
        }
    }

    private void login() {
        String username = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        loginPresenter.login(username, password);
    }

    private void register() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void toastmessage(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginSuccess() {
        toastmessage("Đăng nhập thành công.");
        gotoHomeActivity();
    }

    private void gotoHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void loginFailed() {
        toastmessage("Đăng nhập thất bại.");
        edtEmail.setText("");
        edtPassword.setText("");
    }

    @Override
    public void loginError(String message) {
        toastmessage(message);
    }

}