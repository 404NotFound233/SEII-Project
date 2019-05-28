package com.example.cinema.data.promotion;

import com.example.cinema.po.VIPRecharge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lmt
 */
@Mapper
public interface VIPRechargeMapper {
    int insertOneRecharge(@Param("user_id") int user_id,@Param("before_balance") double before_balance,@Param("single_recharge") double single_recharge,@Param("after_balance") double after_balance);

    List<VIPRecharge> getRecordByUserId(int userId);
}
