package com.project.mywallet.fragment_home;

public class UserSpending {

    private String account;
    private String content;
    private int amount;
    private String daySpending;
    private boolean isIncome;

    public UserSpending( String account, String content, int amount, String daySpending, boolean isIncome) {
        this.account = account;
        this.content = content;
        this.amount = amount;
        this.daySpending = daySpending;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "UserSpending{" +
                "account='" + account + '\'' +
                ", content='" + content + '\'' +
                ", amount=" + amount +
                ", daySpending='" + daySpending + '\'' +
                ", isIncome=" + isIncome +
                '}';
    }
}
