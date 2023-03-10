package com.project.mywallet.fragment_dayly;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.mywallet.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DayFragment extends Fragment implements SpendingInterface, IClickItemListener {

    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.rvDay)
    RecyclerView rvDay;
    @BindView(R.id.tvStatus)
    TextView tvStatus;

    final Calendar calendar = Calendar.getInstance();
    String spinnerClassify;
    String spinnerCategory;
    boolean isIncome;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentAccount;
    private String dateCurrent;

    private ArrayList<ViewSpending> mListSpendingHistory;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private SpendingHistoryAdapter spendingAdapter;

    private AlertDialog alertDialog;
    private SpendingPresenter spendingPresenter;
    private String listClassify[] = {"Ti???n m???t", "T??i kho???n ng??n h??ng"};

    // V?? d??? ????y l?? m???ng data nh??
    // Th?? m??nh c?? gi?? tr??? c???a category trong userSpending
    // ????n gi???n nh???t l?? d??ng for ch???y so s??nh gi?? tr??? c???a category trong userSpending v???i v??? tr?? th??? i trong m???ng
    // Tr??ng c??i n??o th?? break h??m for lu??n
    // kh??ng tr??ng th?? s??? v??o case n??y "Kh??c" (ch???y ?????n l???n l???p cu???i c??ng th?? k c???n so s??nh n???a m?? return lu??n )
    private String listCategorySpending[] = {"??n u???ng", "Gi???i tr??", "Ph??t tri???n b???n th??n", "Giao th??ng v???n t???i", "S??? th??ch", "L??m ?????p", "Sinh ho???t", "Th???i trang", "S???c kh???e", "S??? ki???n", "Kh??c"};
    private String listCategoryIncome[] = {"Ti???n l????ng", "Ti???n th?????ng", "Ti???n l??m th??m", "Ti???n c???p", "Kh??c"};
    private String[] listCategory;

    public static DayFragment newInstance() {
        Bundle args = new Bundle();
        DayFragment fragment = new DayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        inData();
        initView();
    }

    private void initView() {
        spendingAdapter = new SpendingHistoryAdapter(mListSpendingHistory);
        spendingAdapter.setClickItemListener(this);
        rvDay.setAdapter(spendingAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

    }

    private void inData() {
        mListSpendingHistory = new ArrayList<>();
        getCurrentAccount();
        databaseReference = firebaseDatabase.getReference(currentAccount);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListSpendingHistory.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    ViewSpending viewSpending = child.getValue(ViewSpending.class);
                    mListSpendingHistory.add(viewSpending);
                }
                bindData(mListSpendingHistory);
                rvDay.scrollToPosition(mListSpendingHistory.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "onCancelled: Failed ", error.toException());
            }
        });

    }

    private void bindData(ArrayList<ViewSpending> mListSpendingHistory) {
        if (mListSpendingHistory.size() <= 0) {
            rvDay.setVisibility(View.GONE);
            tvStatus.setVisibility(View.VISIBLE);
        } else {
            spendingAdapter.updateData(mListSpendingHistory);
            rvDay.setVisibility(View.VISIBLE);
            tvStatus.setVisibility(View.GONE);
        }
    }


    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_spending, null);
        builder.setView(view);
        spendingPresenter = new SpendingPresenter(this);

        Spinner spinner = view.findViewById(R.id.spnAccount);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.layout_select_item, listClassify);
        adapter.setDropDownViewResource(R.layout.layout_dropdown_custom);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerClassify = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner spinner1 = view.findViewById(R.id.spnCategory);
        ArrayAdapter adapter1 = new ArrayAdapter<>(getActivity(), R.layout.layout_select_item, listCategoryIncome);
        adapter1.setDropDownViewResource(R.layout.layout_dropdown_custom);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        EditText edtAmount = view.findViewById(R.id.edtAmount);
        EditText edtDaySpending = view.findViewById(R.id.edtDaySpending);
        getToday();
        edtDaySpending.setText(dateCurrent);
        EditText edtNote = view.findViewById(R.id.edtNote);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvAddSpending = view.findViewById(R.id.tvAddSpending);
        Switch swChangeClassify = view.findViewById(R.id.swChangeClassify);
        isIncome = true;

        swChangeClassify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isIncome = true;
                    swChangeClassify.setText("Thu ");
                } else {
                    isIncome = false;
                    swChangeClassify.setText("Chi ");
                }
                String listCategory[];
                if (isIncome == true) {
                    listCategory = listCategoryIncome;
                } else {
                    listCategory = listCategorySpending;
                }
                ArrayAdapter adapter1 = new ArrayAdapter<>(getActivity(), R.layout.layout_select_item, listCategory);
                adapter1.setDropDownViewResource(R.layout.layout_dropdown_custom);
                spinner1.setAdapter(adapter1);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinnerCategory = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });
        Log.d("TAG", "showDialogAdd: " + isIncome);

        alertDialog = builder.create();

        //Pick khung edtDaySpending hi???n b???ng l???ch ng??y th??ng n??m.
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        tvAddSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spendingPresenter.pushSpending(spinnerClassify, spinnerCategory, edtAmount, edtDaySpending, edtNote, isIncome);
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
    public void onSucces(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        alertDialog.dismiss();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private void getCurrentAccount() {
        String email = mAuth.getCurrentUser().getEmail();
        currentAccount = email.substring(0, email.indexOf("@"));
    }

    private void getToday() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateCurrent = dateFormat.format(currentDate);
    }

    @Override
    public void onClickItemChange(ViewSpending viewSpending) {
//        Log.d("TAG", "onClickItemChange: " + viewSpending);
        showDialogEdit(viewSpending);
    }

    private int getIndexCategory(String category) {
        int index = -1;
        if (listCategory != null && listCategory.length > 0) {
            for (int i = 0; i < listCategory.length; i++) {
                if (i < listCategory.length - 1) {
                    if (category.equals(listCategory[i])) {
                        index = i;
                        break;
                    }
                } else {
                    return listCategory.length - 1;
                }
            }
        }
        return index;
    }

    private void showDialogEdit(ViewSpending viewSpending) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_spending, null);
        builder.setView(view);
        spendingPresenter = new SpendingPresenter(this);

        TextView tvTittle = view.findViewById(R.id.tvTittle);
        tvTittle.setText("S???a kho???n thu chi");

        Spinner spinner = view.findViewById(R.id.spnAccount);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.layout_select_item, listClassify);
        adapter.setDropDownViewResource(R.layout.layout_dropdown_custom);
        spinner.setAdapter(adapter);
        spinner.setSelection(getIndexClassify(viewSpending.getAccount()));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerClassify = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        EditText edtAmount = view.findViewById(R.id.edtAmount);
        edtAmount.setText(viewSpending.getAmount() + "");

        EditText edtDaySpending = view.findViewById(R.id.edtDaySpending);
        edtDaySpending.setText(viewSpending.getDaySpending());

        EditText edtNote = view.findViewById(R.id.edtNote);
        edtNote.setText(viewSpending.getNote());

        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvAddSpending = view.findViewById(R.id.tvAddSpending);

        Spinner spinner1 = view.findViewById(R.id.spnCategory);
        Switch swChangeClassify = view.findViewById(R.id.swChangeClassify);
        isIncome = viewSpending.isIncome();
        if (isIncome == true) {
            swChangeClassify.setChecked(true);
            swChangeClassify.setText("Thu");
            listCategory = listCategoryIncome;
        } else {
            swChangeClassify.setChecked(false);
            swChangeClassify.setText("Chi");
            listCategory = listCategorySpending;
        }
        swChangeClassify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isIncome = true;
                    swChangeClassify.setText("Thu ");
                } else {
                    isIncome = false;
                    swChangeClassify.setText("Chi ");
                }
                if (isIncome == true) {
                    listCategory = listCategoryIncome;
                } else {
                    listCategory = listCategorySpending;
                }
            }
        });
        ArrayAdapter adapter1 = new ArrayAdapter<>(getActivity(), R.layout.layout_select_item, listCategory);
        adapter1.setDropDownViewResource(R.layout.layout_dropdown_custom);
        spinner1.setAdapter(adapter1);
        spinner1.setSelection(getIndexCategory(viewSpending.getCategory()));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        alertDialog = builder.create();

        //Pick khung edtDaySpending hi???n b???ng l???ch ng??y th??ng n??m.
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        //Hai s??? ki???n n??y n?? ?????c l???p vs nhau n??n khi ng?????i d??ng ch??a push spending th?? s??? b??? null bi??n currentAcount
        // Gi???i quy???t th?? m??nh ch??? c???n g???i getCurrentAccount ??? b??n ngo??i th??i  k c???n ?????t trong h??m n???a

        tvAddSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spendingPresenter.actionSpending(viewSpending,spinnerClassify, spinnerCategory, edtAmount, edtDaySpending, edtNote, isIncome);
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

    private int getIndexClassify(String account) {
        int index = -1;
        if (listClassify != null && listClassify.length > 0) {
            for (int i = 0; i < listClassify.length; i++) {
                if (i < listClassify.length - 1) {
                    if (account.equals(listClassify[i])) {
                        index = i;
                        break;
                    }
                } else {
                    return listClassify.length - 1;
                }
            }
        }
        return index;
    }
}