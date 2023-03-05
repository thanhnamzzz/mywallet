package com.project.mywallet.fragment_home;

import static android.app.ProgressDialog.show;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.project.mywallet.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DayFragment extends Fragment {

    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.rvDay)
    RecyclerView rvDay;

    final Calendar calendar = Calendar.getInstance();
    private ActionBar actionBar;

    public static DayFragment newInstance() {

        Bundle args = new Bundle();

        DayFragment fragment = new DayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DayFragment() {
        // Required empty public constructor
    }

    public static DayFragment newInstance(String param1, String param2) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });
    }

    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Sử dụng 2 view khác nhau nên nó k nhận được sự kiện ở đây
        View view = inflater.inflate(R.layout.dialog_add_spending, null);
        builder.setView(view);
//        builder.setView(R.layout.dialog_add_spending);
        EditText edtClassify = view.findViewById(R.id.edtClassify);
        EditText edtContent = view.findViewById(R.id.edtContent);
        EditText edtAmount = view.findViewById(R.id.edtAmount);
        EditText edtDaySpending = view.findViewById(R.id.edtDaySpending);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvChoose = view.findViewById(R.id.tvChoose);
        AlertDialog alertDialog = builder.create();
        //Pick khung edtDaySpending hiện bảng lịch ngày tháng năm.
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCalendar();
            }
            private void updateCalendar() {
                String format = "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
                edtDaySpending.setText(simpleDateFormat.format((calendar.getTime())));
            }
        };
        edtDaySpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        tvChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserSpending(edtClassify,edtContent,edtAmount,edtDaySpending);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void addUserSpending(EditText edtClassify, EditText edtContent, EditText edtAmount, EditText edtDaySpending) {
        UserSpending userSpending = new UserSpending();
        userSpending.setClassify(edtClassify.getText().toString());
        userSpending.setContent(edtContent.getText().toString());
        userSpending.setAmount(Integer.parseInt(edtAmount.getText().toString()));
        userSpending.setDaySpending(edtDaySpending.getText().toString());
        Log.d("TAG", "addUserSpending: " + userSpending);
    }

}