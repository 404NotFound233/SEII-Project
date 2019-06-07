package com.example.cinema.vo;

import java.sql.Timestamp;


public class VIPRecordVO {
    private int id;

    private int userId;

    private String amountString;

    private double before_Balance;

    private double after_Balance;

    private String reasonString;

    private Timestamp join_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAmountString() {
        return amountString;
    }

    public void setAmountString(String amountString) {
        this.amountString = amountString;
    }

    public double getBefore_Balance() {
        return before_Balance;
    }

    public void setBefore_Balance(double before_Balance) {
        this.before_Balance = before_Balance;
    }

    public double getAfter_Balance() {
        return after_Balance;
    }

    public void setAfter_Balance(double after_Balance) {
        this.after_Balance = after_Balance;
    }

    public String getReasonString() {
        return reasonString;
    }

    public void setReasonString(String reasonString) {
        this.reasonString = reasonString;
    }

    public Timestamp getJoin_time() {
        return join_time;
    }

    public void setJoin_time(Timestamp join_time) {
        this.join_time = join_time;
    }
}
