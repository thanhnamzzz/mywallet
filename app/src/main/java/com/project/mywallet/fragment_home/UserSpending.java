package com.project.mywallet.fragment_home;

public class UserSpending {
    private String classify;
    private String content;
    private int amount;
    private String daySpending;

    public UserSpending(String classify, String content, int amount, String daySpending) {
        this.classify = classify;
        this.content = content;
        this.amount = amount;
        this.daySpending = daySpending;
    }

    public UserSpending() {
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
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

    @Override
    public String toString() {
        return "UserSpending{" +
                "classify='" + classify + '\'' +
                ", content='" + content + '\'' +
                ", amount=" + amount +
                ", daySpending='" + daySpending + '\'' +
                '}';
    }
}
