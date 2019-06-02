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

    int insertOneCard(VIPCard vipCard);

    VIPCard selectCardById(int id);

    void updateCardBalance(@Param("id") int id,@Param("balance") double balance);

    VIPCard selectCardByUserId(int userId);


    void updateCardBalanceByUserId(@Param("user_id") int userId,@Param("balance")  double balance);

    int insertVIPInfo(VIPInfo vip);

    VIPInfo selectVIPInfo();

    void updateVIPInfo(@Param("price") double price,@Param("description") String description,@Param("discount") double discount,@Param("reach") double reach,@Param("send") double send);

    int selectLength();
}
