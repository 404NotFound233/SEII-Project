package com.example.cinema.vo;

import java.sql.Timestamp;

public class NormalRecordVO {
    /**
     * id
     */
    private int id;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 退款或付款金额
     */
    private String amountString;

    /**
     * 退款或付款原因
     */
    private String reasonString;

    /**
     * 金额变动时间
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
