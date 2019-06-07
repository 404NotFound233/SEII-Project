package com.example.cinema.po;

import com.example.cinema.vo.NormalRecordVO;

import java.sql.Timestamp;

public class NormalRecord {
    private int id;

    private int userId;

    private double amount;

    private int reason;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public Timestamp getJoin_time() {
        return join_time;
    }

    public void setJoin_time(Timestamp join_time) {
        this.join_time = join_time;
    }

    public NormalRecordVO getVO(){
        NormalRecordVO normalRecordVO=new NormalRecordVO();
        normalRecordVO.setId(this.id);
        normalRecordVO.setUserId(this.userId);
        normalRecordVO.setJoin_time(this.join_time);
        String amountString;
        String reasonString;
        switch (reason) {
            case 1:
                reasonString = "充值会员卡";
                amountString="-"+this.amount;
                break;
            case 2:
                reasonString = "购买会员卡";
                amountString="-"+this.amount;
                break;
            case 3:
                reasonString = "购买电影票";
                amountString="-"+this.amount;
                break;
            case 4:
                reasonString = "电影票退款";
                amountString="+"+this.amount;
                break;
            default:
                reasonString = "无";
                amountString="";
        }
        normalRecordVO.setReasonString(reasonString);
        normalRecordVO.setAmountString(amountString);
        return normalRecordVO;
    }
}
