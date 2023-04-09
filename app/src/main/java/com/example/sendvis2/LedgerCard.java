package com.example.sendvis2;

public class LedgerCard {
    private String target;
    private boolean vendor;
    private boolean sent;
    private int amount;
    private String date;

    public LedgerCard(String target, boolean vendor, boolean sent, int amount, String date) {
        this.target = target;
        this.vendor = vendor;
        this.sent = sent;
        this.amount = amount;
        this.date = date;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isVendor() {
        return vendor;
    }

    public void setVendor(boolean vendor) {
        this.vendor = vendor;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
