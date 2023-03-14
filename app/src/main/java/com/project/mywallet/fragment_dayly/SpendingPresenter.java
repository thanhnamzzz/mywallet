package com.project.mywallet.fragment_dayly;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SpendingPresenter {
    private SpendingInterface spendingInterface;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public String currentAccount;

    public SpendingPresenter(SpendingInterface spendingInterface) {
        this.spendingInterface = spendingInterface;
        this.currentAccount = getCurrentAccount();
    }

    public void pushSpending(String accountSeletion, String categorySelection, EditText edtAmount, EditText edtDaySpending, EditText edtNote, boolean isIncome) {
        //Hàm getCurrentAccount này chỉ được gọi khi nào push nếu chưa gọi đến push thì nó sẽ null đúng k
        String checkAmount = edtAmount.getText().toString().trim();
        if (!TextUtils.isEmpty(checkAmount)) {
            UserSpending userSpending = new UserSpending();
            userSpending.setAccount(accountSeletion);
            userSpending.setCategory(categorySelection);
            userSpending.setAmount(Integer.parseInt(edtAmount.getText().toString()));
            userSpending.setDaySpending(edtDaySpending.getText().toString());
            userSpending.setNote(edtNote.getText().toString());
            userSpending.setIncome(isIncome);
            databaseReference = firebaseDatabase.getReference(currentAccount);
            databaseReference.push().setValue(userSpending);
            spendingInterface.onSucces("Thêm khoản chi tiêu thành công");
        } else {
            spendingInterface.onFailed("Nhập số tiền");
        }
    }

    @NonNull
    public String getCurrentAccount() {
        String email = mAuth.getCurrentUser().getEmail();
        currentAccount = email.substring(0, email.indexOf("@"));
        return currentAccount;
    }

    public void actionSpending(ViewSpending viewSpending, String accountSeletion, String categorySelection, EditText edtAmount, EditText edtDaySpending, EditText edtNote, boolean isIncome) {
        databaseReference = firebaseDatabase.getReference(currentAccount);
        String checkAmount = edtAmount.getText().toString().trim();

        String key = viewSpending.getId();

        UserSpending userSpending = new UserSpending();
        userSpending.setAccount(accountSeletion);
        userSpending.setCategory(categorySelection);
        userSpending.setAmount(Long.parseLong(edtAmount.getText().toString()));
        userSpending.setDaySpending(edtDaySpending.getText().toString());
        userSpending.setNote(edtNote.getText().toString());
        userSpending.setIncome(isIncome);
        HashMap<String, Object> map = userSpending.toMap();

        HashMap<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, map);
        databaseReference.updateChildren(childUpdates);
    }
}
