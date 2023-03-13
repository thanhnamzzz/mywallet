package com.project.mywallet.fragment_account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.mywallet.MainActivity;
import com.project.mywallet.R;
import com.project.mywallet.fragment_dayly.UserSpending;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountFragment extends Fragment {

    @BindView(R.id.btnSignOut)
    Button btnSignOut;
    @BindView(R.id.tvCurrentAccount)
    TextView tvCurrentAccount;
    @BindView(R.id.rvAllAccount)
    RecyclerView rvAllAccount;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentAccount;
    private String currentAccountFull;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private ArrayList<String> mListAccountView;

    public AccountFragment() {
    }

    public static AccountFragment newInstance() {

        Bundle args = new Bundle();

        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inData();
        inView(view);
    }

    private void inData() {
        mListAccountView = new ArrayList<>();
        getCurrentAccount();
        databaseReference = firebaseDatabase.getReference(currentAccount);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListAccountView.clear();
                for (DataSnapshot child : snapshot.getChildren()){
                    UserSpending userSpending = child.getValue(UserSpending.class);
                    mListAccountView.add(userSpending.getAccount());
                }
                HashSet<String> hashSet = new HashSet<>(mListAccountView);
                mListAccountView.clear();
                mListAccountView.addAll(hashSet);
                Log.d("TAG", "onDataChange: " + mListAccountView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void inView(View view) {
        ButterKnife.bind(this,view);
        getCurrentAccount();
        tvCurrentAccount.setText(currentAccountFull);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(),"Đăng xuất thành công!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getCurrentAccount() {
        String email = mAuth.getCurrentUser().getEmail();
        currentAccount = email.substring(0, email.indexOf("@"));
        currentAccountFull = email;
    }
}