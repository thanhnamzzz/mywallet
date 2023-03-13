package com.project.mywallet.fragment_dayly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mywallet.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SpendingHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ViewSpending> viewSpendings;
    private final int INCOME = 1;
    private final int SPENDING = 0;
    private IClickItemListener iClickItemListener;

    public SpendingHistoryAdapter(ArrayList<ViewSpending> viewSpendings) {
        this.viewSpendings = new ArrayList<>();
        this.viewSpendings.addAll(viewSpendings);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == INCOME) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lineview_income, parent, false);
            return new InComeViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lineview_spending, parent, false);
            return new SpendingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewSpending viewSpending = viewSpendings.get(position);
        if (holder instanceof InComeViewHolder) {
            ((InComeViewHolder) holder).tvAccount.setText(viewSpending.getAccount());
            ((InComeViewHolder) holder).tvCategory.setText(viewSpending.getCategory());
            ((InComeViewHolder) holder).tvAmount.setText(numberformat(viewSpending.getAmount()) + " ₫");
            ((InComeViewHolder) holder).tvDay.setText(viewSpending.getDaySpending());
            ((InComeViewHolder) holder).tvNote.setText(viewSpending.getNote());
        } else {
            ((SpendingViewHolder) holder).tvAccount.setText(viewSpending.getAccount());
            ((SpendingViewHolder) holder).tvCategory.setText(viewSpending.getCategory());
            ((SpendingViewHolder) holder).tvAmount.setText("-" + numberformat(viewSpending.getAmount()) + " ₫");
            ((SpendingViewHolder) holder).tvDay.setText(viewSpending.getDaySpending());
            ((SpendingViewHolder) holder).tvNote.setText(viewSpending.getNote());
        }
    }

    private CharSequence numberformat(int amount) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String formattedNumber = numberFormat.format(amount);
        return formattedNumber;
    }

    @Override
    public int getItemViewType(int position) {
        if (viewSpendings.get(position).isIncome() == true) {
            return INCOME;
        } else {
            return SPENDING;
        }
    }

    @Override
    public int getItemCount() {
        return viewSpendings.size();
    }

    public void updateData(ArrayList<ViewSpending> mListSpendingHistory) {
        this.viewSpendings.clear();
        this.viewSpendings.addAll(mListSpendingHistory);
        notifyDataSetChanged();
    }

    public void setClickItemListener(IClickItemListener iClickItemListener) {
        this.iClickItemListener = iClickItemListener;
    }

    public class InComeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvAccount;
        public TextView tvCategory;
        public TextView tvAmount;
        public TextView tvDay;
        public TextView tvNote;
        public ConstraintLayout clIncome;

        public InComeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAccount = itemView.findViewById(R.id.tvAccount);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvNote = itemView.findViewById(R.id.tvNote);
            clIncome = itemView.findViewById(R.id.clIncome);
            clIncome.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.clIncome) {
                iClickItemListener.onClickItemChange(viewSpendings.get(getAdapterPosition()));
            }
        }
    }

    public class SpendingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvAccount;
        public TextView tvCategory;
        public TextView tvAmount;
        public TextView tvDay;
        public TextView tvNote;
        public ConstraintLayout clSpending;

        public SpendingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAccount = itemView.findViewById(R.id.tvAccount);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvNote = itemView.findViewById(R.id.tvNote);
            clSpending = itemView.findViewById(R.id.clSpending);
            clSpending.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.clSpending) {
                iClickItemListener.onClickItemChange(viewSpendings.get(getAdapterPosition()));
            }
        }
    }
}
