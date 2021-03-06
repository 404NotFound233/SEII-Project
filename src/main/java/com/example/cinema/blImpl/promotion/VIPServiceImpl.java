package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardMapper;

import com.example.cinema.data.sales.TicketMapper;

import com.example.cinema.data.promotion.VIPRechargeMapper;
import com.example.cinema.po.VIPRecharge;

import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPInfo;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPInfoVO;
import com.example.cinema.vo.VIPRechargeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService {
    @Autowired
    VIPCardMapper vipCardMapper;
    @Autowired

    TicketMapper ticketMapper;
    @Autowired
    VIPRechargeMapper vipRechargeMapper;


    @Override
    public ResponseVO addVIPCard(int userId,double price) {
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setBalance(0);
        try {
            int id = vipCardMapper.insertOneCard(vipCard);
            ticketMapper.insertNormalRecord(userId,price,2);
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getVIPInfo() {
        try{
            VIPInfoVO vipInfoVO = new VIPInfoVO();
            VIPInfo vip=vipCardMapper.selectVIPInfo();
            if(vip!=null){
                vipInfoVO.setDescription(vip.getDescription());
                vipInfoVO.setPrice(vip.getPrice());
                vipInfoVO.setDiscount(vip.getDiscount());
                vipInfoVO.setReach(vip.getReach());
                vipInfoVO.setSend(vip.getSend());
                return ResponseVO.buildSuccess(vipInfoVO);
            }
            else{
                return ResponseVO.buildFailure("暂无会员卡");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO charge(VIPCardForm vipCardForm) {
        VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }

        double balance = vipCardForm.calculate(vipCardForm.getAmount());
        double before_balance=vipCard.getBalance();  
      
        vipCard.setBalance(before_balance + balance);//原先金额加此次充值金额
        try {
            ticketMapper.insertNormalRecord(vipCard.getUserId(),vipCardForm.getAmount(),1);
            ticketMapper.insertVIPRecord(vipCard.getUserId(),vipCard.getBalance()-before_balance,vipCard.getBalance()-balance,1);
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCard.getBalance());
            double after_balance=vipCard.getBalance();//充值后总金额
            int user_id=vipCardMapper.selectUserIdById(vipCardForm.getVipId());
            int id=vipRechargeMapper.insertOneRecharge(user_id, before_balance,balance,after_balance);

            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    @Override
    public ResponseVO getRecord(int userId){
        try{
            return ResponseVO.buildSuccess(VIPRecharge2VIPRechargeRecordList(vipRechargeMapper.getRecordByUserId(userId)));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    private List<VIPRechargeRecord> VIPRecharge2VIPRechargeRecordList(List<VIPRecharge> rechargeList){
        List<VIPRechargeRecord> VIPRechargeRecordList = new ArrayList<>();
        for(VIPRecharge recharge : rechargeList){
            VIPRechargeRecordList.add(recharge.getRecord());
        }
        return VIPRechargeRecordList;
    }

    @Override
    public ResponseVO payByCard(int userId,double balance){
        try{
            vipCardMapper.updateCardBalanceByUserId(userId,balance);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO publishVIP(VIPInfoVO vip_vo) {
        try {
            VIPInfo vip=new VIPInfo();
            vip.setPrice(vip_vo.getPrice());
            vip.setDescription(vip_vo.getDescription());
            vip.setDiscount(vip_vo.getDiscount());
            vip.setReach(vip_vo.getReach());
            vip.setSend(vip_vo.getSend());
            vipCardMapper.insertVIPInfo(vip);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO modifyVIP(VIPInfoVO vip_vo){
        try {
            double price=vip_vo.getPrice();
            String description=vip_vo.getDescription();
            double discount=vip_vo.getDiscount();
            double reach=vip_vo.getReach();
            double send=vip_vo.getSend();
            vipCardMapper.updateVIPInfo(price,description,discount, reach,send);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getLength(){
        try{
            return ResponseVO.buildSuccess(vipCardMapper.selectLength());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
