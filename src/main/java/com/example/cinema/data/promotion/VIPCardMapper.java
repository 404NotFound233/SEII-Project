package com.example.cinema.data.promotion;

import com.example.cinema.po.VIPInfo;
import com.example.cinema.po.VIPCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liying on 2019/4/14.
 */
@Mapper
public interface VIPCardMapper {
    /**
     * 添加一张会员卡
     * @return
     */
    int insertOneCard(VIPCard vipCard);

    /**
     * 根据id获取会员卡
     * @return
     */
    VIPCard selectCardById(int id);

    /**
     * 更新会员卡余额
     * @return
     */
    void updateCardBalance(@Param("id") int id,@Param("balance") double balance);

    /**
     * 根据userId获取会员卡
     * @return
     */
    VIPCard selectCardByUserId(int userId);

    /**
     * 根据id获取userId
     * @return
     */
    int selectUserIdById(int id);

    /**
     * 根据userId更新会员卡余额
     * @return
     */
    void updateCardBalanceByUserId(@Param("user_id") int userId,@Param("balance")  double balance);

    /**
     * 添加会员卡优惠策略
     * @return
     */
    int insertVIPInfo(VIPInfo vip);

    /**
     * 获取会员卡优惠策略
     * @return
     */
    VIPInfo selectVIPInfo();

    /**
     * 更新会员卡优惠策略
     * @return
     */
    void updateVIPInfo(@Param("price") double price,@Param("description") String description,@Param("discount") double discount,@Param("reach") double reach,@Param("send") double send);

    /**
     * 获取会员卡优惠策略的条数
     * @return
     */
    int selectLength();
}
