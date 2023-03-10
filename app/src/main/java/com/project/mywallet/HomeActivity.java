package com.project.mywallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.project.mywallet.fragment_account.AccountFragment;
import com.project.mywallet.fragment_chart.ChartFragment;
import com.project.mywallet.fragment_dayly.DayFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";
    @BindView(R.id.bnv_Home)
    BottomNavigationView bnv_Home;
    private Fragment mFragment;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        inView();
    }

    private void inView() {
        ButterKnife.bind(this);
        bnv_Home.setOnItemSelectedListener(this::onNavigationItemSelected);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg_app)));
        mFragment = DayFragment.newInstance();
        if (actionBar != null) {
            SpannableString s =new SpannableString("Hàng ngày");
            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textapp)),0,s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            actionBar.setTitle(s);
        }
        loadFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            default:
            case R.id.navigation_day:
                Log.d(TAG, "onNavigationItemSelected: navigation_home");
                mFragment = null;
                mFragment = DayFragment.newInstance();
                if (actionBar != null) {
                    SpannableString s =new SpannableString("Hàng ngày");
                    s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textapp)),0,s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    actionBar.setTitle(s);
                }
                loadFragment();
                return true;
            case R.id.navigation_chart:
                Log.d(TAG, "onNavigationItemSelected: navigation_chart");
                mFragment = null;
                mFragment = ChartFragment.newInstance();
                if (actionBar != null) {
                    SpannableString s =new SpannableString("Thống kê");
                    s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textapp)),0,s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    actionBar.setTitle(s);
                }
                loadFragment();
                return true;
            case R.id.navigation_account:
                Log.d(TAG, "onNavigationItemSelected: navigation_account");
                mFragment = null;
                mFragment = AccountFragment.newInstance();
                if (actionBar != null) {
                    SpannableString s =new SpannableString("Tài khoản");
                    s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textapp)),0,s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    actionBar.setTitle(s);
                }
                loadFragment();
                return true;
        }
    }

    private void loadFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_home, mFragment)
                .addToBackStack(null)
                .commit();
    }
}