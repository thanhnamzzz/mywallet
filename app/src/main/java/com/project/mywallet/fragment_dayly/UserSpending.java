package com.project.mywallet.fragment_dayly;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserSpending {

    private String id;
    private String account;
    private String category;
    private long amount;
    private String daySpending;
    private String note;
    private boolean isIncome;

    {
        //Mình sẽ dùng id này để phân biệt các spending
        id = UUID.randomUUID().toString();
    }

    public UserSpending(String account, String category, int amount, String daySpending, String note, boolean isIncome) {
        this.account = account;
        this.category = category;
        this.amount = amount;
        this.daySpending = daySpending;
        this.note = note;
        this.isIncome = isIncome;
    }

    public UserSpending() {
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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserSpending{" + "account='" + account + '\'' + ", category='" + category + '\'' + ", amount=" + amount + ", daySpending='" + daySpending + '\'' + ", note='" + note + '\'' + ", isIncome=" + isIncome + '}';
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("account", account);
        result.put("category", category);
        result.put("amount", amount);
        result.put("daySpending", daySpending);
        result.put("note", note);
        result.put("isIncome", isIncome);

        return result;
    }
}
