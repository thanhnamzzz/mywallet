package com.project.mywallet.fragment_dayly;

import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public void pushSpending(String accountSeletion,String categorySelection, EditText edtAmount, EditText edtDaySpending, EditText edtNote, boolean isIncome) {
        //Hàm getCurrentAccount này chỉ được gọi khi nào push nếu chưa gọi đến push thì nó sẽ null đúng k
        String checkAmount = edtAmount.getText().toString().trim();
        if(!TextUtils.isEmpty(checkAmount)) {
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
        }else {
            spendingInterface.onFailed("Nhập số tiền");
        }
    }

    @NonNull
    public String getCurrentAccount() {
        String email = mAuth.getCurrentUser().getEmail();
        currentAccount = email.substring(0,email.indexOf("@"));
        return currentAccount;
    }

    //Hàm này e dùng làm gì
    //để update dữ liệu mình chỉnh sửa đấy

    public void actionSpending(ViewSpending viewSpending, String accountSeletion, String categorySelection, EditText edtAmount, EditText edtDaySpending, EditText edtNote, boolean isIncome){
        //Ở đây nó đang bị null nè
        databaseReference = firebaseDatabase.getReference(currentAccount);
        String checkAmount = edtAmount.getText().toString().trim();

        databaseReference.child(viewSpending.getId());
        String key = databaseReference.push().getKey();
        //Mình có cách nào lấy được id của child k
        // :)))
        // Thôi cái này để sáng mai a check thử
        // có share được github k e
        //Cái này mới update chứ nhỉ
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                if(!TextUtils.isEmpty(checkAmount)) {
//                    UserSpending userSpending = snapshot.getValue(UserSpending.class);
//                    userSpending.setAccount(accountSeletion);
//                    userSpending.setCategory(categorySelection);
//                    userSpending.setAmount(Integer.parseInt(edtAmount.getText().toString()));
//                    userSpending.setDaySpending(edtDaySpending.getText().toString());
//                    userSpending.setNote(edtNote.getText().toString());
//                    userSpending.setIncome(isIncome);
//                    databaseReference.push().setValue(userSpending);
//                    spendingInterface.onSucces("Sửa khoản chi tiêu thành công");
//                }else {
//                    spendingInterface.onFailed("Nhập số tiền");
//                }
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(!TextUtils.isEmpty(checkAmount)) {
//                    UserSpending userSpending = snapshot.getValue(UserSpending.class);
//                    userSpending.setAccount(accountSeletion);
//                    userSpending.setCategory(categorySelection);
//                    userSpending.setAmount(Integer.parseInt(edtAmount.getText().toString()));
//                    userSpending.setDaySpending(edtDaySpending.getText().toString());
//                    userSpending.setNote(edtNote.getText().toString());
//                    userSpending.setIncome(isIncome);
//                    databaseReference.push().setValue(userSpending);
//                    spendingInterface.onSucces("Sửa khoản chi tiêu thành công");
//                }else {
//                    spendingInterface.onFailed("Nhập số tiền");
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}
