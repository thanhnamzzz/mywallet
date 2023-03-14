package com.project.mywallet.fragment_dayly;

import java.util.UUID;

public class ViewSpending {
    private String id;
    private String account;
    private String category;
    private int amount;
    private String daySpending;
    private String note;
    private boolean isIncome;

    {
        //Mình sẽ dùng id này để phân biệt các spending
        id = UUID.randomUUID().toString();
    }

    public ViewSpending() {
    }

    public ViewSpending(String account, String category, int amount, String daySpending, String note, boolean isIncome) {
        this.account = account;
        this.category = category;
        this.amount = amount;
        this.daySpending = daySpending;
        this.note = note;
        this.isIncome = isIncome;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDaySpending() {
        return daySpending;
    }

    public void setDaySpending(String daySpending) {
        this.daySpending = daySpending;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ViewSpending{" +
                "account='" + account + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", daySpending='" + daySpending + '\'' +
                ", note='" + note + '\'' +
                ", isIncome=" + isIncome +
                '}';
    }
}
