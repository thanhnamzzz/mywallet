package com.project.mywallet;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg_app)));
        if (actionBar != null) {
            SpannableString s =new SpannableString("????ng nh???p");
            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textapp)),0,s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            actionBar.setTitle(s);
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
        toastmessage("????ng nh???p th??nh c??ng.");
        gotoHomeActivity();
    }

    private void gotoHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void loginFailed() {
        toastmessage("????ng nh???p th???t b???i.");
        edtEmail.setText("");
        edtPassword.setText("");
    }

    @Override
    public void loginError(String message) {
        toastmessage(message);
    }

}