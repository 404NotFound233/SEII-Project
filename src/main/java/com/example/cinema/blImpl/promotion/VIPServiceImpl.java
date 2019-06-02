package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPInfo;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService {
    @Autowired
    VIPCardMapper vipCardMapper;
    @Autowired
    TicketMapper ticketMapper;

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
            vipInfoVO.setDescription(vip.getDescription());
            vipInfoVO.setPrice(vip.getPrice());
            vipInfoVO.setDiscount(vip.getDiscount());
            vipInfoVO.setReach(vip.getReach());
            vipInfoVO.setSend(vip.getSend());
            return ResponseVO.buildSuccess(vipInfoVO);
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
        vipCard.setBalance(vipCard.getBalance() + balance);
        try {
            ticketMapper.insertNormalRecord(vipCard.getUserId(),vipCardForm.getAmount(),1);
            ticketMapper.insertVIPRecord(vipCard.getUserId(),vipCardForm.getAmount(),vipCard.getBalance()-balance,1);
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCard.getBalance());
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

    //wqy
    //如何限制数据库里面只有一条记录？
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

    //wqy
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

    //wqy
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
