package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.promotion.VIPRechargeMapper;
import com.example.cinema.po.VIPRecharge;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.po.VIPCard;
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
    VIPRechargeMapper vipRechargeMapper;

    @Override
    public ResponseVO addVIPCard(int userId) {
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setBalance(0);
        try {
            int id = vipCardMapper.insertOneCard(vipCard);
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
        VIPInfoVO vipInfoVO = new VIPInfoVO();
        vipInfoVO.setDescription(VIPCard.description);
        vipInfoVO.setPrice(VIPCard.price);
        return ResponseVO.buildSuccess(vipInfoVO);
    }

    @Override
    public ResponseVO charge(VIPCardForm vipCardForm) {

        VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }
        double single_recharge = vipCard.calculate(vipCardForm.getAmount());//本金加赠送金额
        double before_balance=vipCard.getBalance();
        vipCard.setBalance(before_balance + single_recharge);//原先金额加此次充值金额
        try {
            int vipId=vipCardForm.getVipId();
            double after_balance=vipCard.getBalance();//充值后总金额
            vipCardMapper.updateCardBalance(vipId, after_balance);
            int user_id=vipCardMapper.selectUserIdById(vipId);
            int id=vipRechargeMapper.insertOneRecharge(user_id, before_balance,single_recharge,after_balance);
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

    //自己定义的接口的实现
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

}
