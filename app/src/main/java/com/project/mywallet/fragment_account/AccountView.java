package com.project.mywallet.fragment_account;

public class AccountView {
    private String account;
    private int amount;
    private boolean isIncome;

    public AccountView() {
    }

    public AccountView(String account, int amount, boolean isIncome) {
        this.account = account;
        this.amount = amount;
        this.isIncome = isIncome;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    @Override
    public String toString() {
        return "AccountView{" +
                "account='" + account + '\'' +
                ", amount=" + amount +
                ", isIncome=" + isIncome +
                '}';
    }
}
