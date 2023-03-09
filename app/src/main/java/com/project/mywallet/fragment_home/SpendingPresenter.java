package com.project.mywallet.fragment_home;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SpendingPresenter {
    private SpendingInterface spendingInterface;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public SpendingPresenter(SpendingInterface spendingInterface) {
        this.spendingInterface = spendingInterface;
    }

    public void pushSpending(String accountSeletion, EditText edtContent, EditText edtAmount, EditText edtDaySpending, boolean isIncome) {
        String email = mAuth.getCurrentUser().getEmail();
        String currentAccount = email.substring(0,email.indexOf("@"));
        String checkAmount = edtAmount.getText().toString().trim();
        if(!TextUtils.isEmpty(checkAmount)) {
            UserSpending userSpending = new UserSpending();
            userSpending.setAccount(accountSeletion);
            userSpending.setContent(edtContent.getText().toString());
            userSpending.setAmount(Integer.parseInt(edtAmount.getText().toString()));
            userSpending.setDaySpending(edtDaySpending.getText().toString());
            userSpending.setIncome(isIncome);
            databaseReference = firebaseDatabase.getReference(currentAccount);
            databaseReference.push().setValue(userSpending);
            spendingInterface.onSucces();
        }else {
            spendingInterface.onFailed();
        }
    }
}
