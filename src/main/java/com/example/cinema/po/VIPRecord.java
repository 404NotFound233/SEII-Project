package com.example.cinema.po;

import com.example.cinema.vo.VIPRecordVO;
import java.sql.Timestamp;

public class VIPRecord {
    /**
     * 会员卡id
     */
    private int id;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡变动的金额
     */
    private double amount;

    /**
     * 会员卡变动前的金额
     */
    private double before_Balance;

    /**
     * 会员卡余额变动原因
     */
    private int reason;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBefore_Balance() {
        return before_Balance;
    }

    public void setBefore_Balance(double before_Balance) {
        this.before_Balance = before_Balance;
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

    public VIPRecordVO getVO(){
        VIPRecordVO vipRecordVO=new VIPRecordVO();
        vipRecordVO.setId(this.id);
        vipRecordVO.setUserId(this.userId);
        vipRecordVO.setBefore_Balance(this.before_Balance);
        vipRecordVO.setJoin_time(this.join_time);
        double after_Balance;
        String amountString;
        String reasonString;
        switch (reason) {
            case 1:
                reasonString = "充值";
                amountString="+"+this.amount;
                after_Balance=this.before_Balance+this.amount;
                break;
            case 2:
                reasonString = "购买电影票";
                amountString="-"+this.amount;
                after_Balance=this.before_Balance-this.amount;
                break;
            case 3:
                reasonString = "电影票退款";
                amountString="+"+this.amount;
                after_Balance=this.before_Balance+this.amount;
                break;
            default:
                reasonString = "无";
                amountString="";
                after_Balance=0;
        }
        vipRecordVO.setReasonString(reasonString);
        vipRecordVO.setAmountString(amountString);
        vipRecordVO.setAfter_Balance(after_Balance);
        return vipRecordVO;
    }
}
