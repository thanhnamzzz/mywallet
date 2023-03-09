package com.project.mywallet.fragment_home;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mywallet.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DayFragment extends Fragment implements SpendingInterface {

    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.rvDay)
    RecyclerView rvDay;

    final Calendar calendar = Calendar.getInstance();
    String spinnerSelection;
    boolean isIncome = true;

    private AlertDialog alertDialog;
    private SpendingPresenter spendingPresenter;
    private ActionBar actionBar;
    private String listClassify[] = {"Tiền mặt", "Tài khoản ngân hàng"};
    private static final String TAG = "DayFragment";

    public static DayFragment newInstance() {
        Bundle args = new Bundle();
        DayFragment fragment = new DayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DayFragment() {
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
        View view = inflater.inflate(R.layout.dialog_add_spending, null);
        builder.setView(view);
        spendingPresenter = new SpendingPresenter(this);

        Spinner spinner = view.findViewById(R.id.spnAccount);
        ArrayAdapter adapter =new ArrayAdapter(getActivity(),R.layout.layout_select_item, listClassify);
        adapter.setDropDownViewResource(R.layout.layout_dropdown_custom);
        spinner.setAdapter(adapter);

//        EditText edtClassify = view.findViewById(R.id.spnClassify);
        EditText edtContent = view.findViewById(R.id.edtContent);
        EditText edtAmount = view.findViewById(R.id.edtAmount);
        EditText edtDaySpending = view.findViewById(R.id.edtDaySpending);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvAddSpending = view.findViewById(R.id.tvAddSpending);
        Switch swChangeClassify = view.findViewById(R.id.swChangeClassify);

        swChangeClassify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isIncome = true;
                    swChangeClassify.setText("Thu ");
                } else {
                    isIncome = false;
                    swChangeClassify.setText("Chi ");
                }
            }
        });

        alertDialog = builder.create();

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

        tvAddSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!spinnerSelection.isEmpty() || !"".equals(spinnerSelection)){
                    spendingPresenter.pushSpending(spinnerSelection,edtContent,edtAmount,edtDaySpending,isIncome);
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                spinnerSelection = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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

    @Override
    public void onSucces() {
        alertDialog.dismiss();
    }

    @Override
    public void onFailed() {
        Toast.makeText(getActivity(),"Nhập số tiền", Toast.LENGTH_LONG).show();
    }
}