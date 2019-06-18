package com.example.cinema.bl.promotion;

import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPInfoVO;


/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    /**
     * 增加会员卡
     * @return
     */
    ResponseVO addVIPCard(int userId, double price);

    /**
     * 根据id获取会员卡
     * @return
     */
    ResponseVO getCardById(int id);

    /**
     * 获取会员卡优惠策略
     * @return
     */
    ResponseVO getVIPInfo();

    /**
     * 充值会员卡
     * @return
     */
    ResponseVO charge(VIPCardForm vipCardForm);

    /**
     * 根据userId获取会员卡
     * @return
     */
    ResponseVO getCardByUserId(int userId);

    /**
     * 会员卡扣款
     * @return
     */
    ResponseVO payByCard(int userId,double balance);

    /**
     * 发布会员卡优惠策略
     * @return
     */
    ResponseVO publishVIP(VIPInfoVO vip);

    /**
     * 修改会员卡优惠策略
     * @return
     */
    ResponseVO modifyVIP(VIPInfoVO vip);

    /**
     * 获取会员卡优惠策略的条数
     * @return
     */
    ResponseVO getLength();

    /**
     * 获取会员卡充值记录
     * @return
     */
    ResponseVO getRecord(int userId);
}
