package com.example.cinema.vo;

import java.sql.Timestamp;


public class VIPRecordVO {
    /**
     * 会员卡id
     */
    private int id;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡变动金额
     */
    private String amountString;

    /**
     * 会员卡变动前的金额
     */
    private double before_Balance;

    /**
     * 会员卡变动后的金额
     */
    private double after_Balance;

    /**
     * 会员卡余额变动原因
     */
    private String reasonString;

    /**
     * 会员卡余额变动时间
     */
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
