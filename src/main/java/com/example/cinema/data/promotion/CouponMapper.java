package com.example.cinema.data.promotion;

import com.example.cinema.po.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/17.
 */
@Mapper
public interface CouponMapper {
    /**
     * 添加优惠券
     * @return
     */
    int insertCoupon(Coupon coupon);

    /**
     * 根据userId获取优惠券
     * @return
     */
    List<Coupon> selectCouponByUser(int userId);

    /**
     * 根据id获取优惠券
     * @return
     */
    Coupon selectById(int id);

    /**
     * 给用户添加优惠券
     * @return
     */
    void insertCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);

    /**
     * 删除用户的优惠券
     * @return
     */
    void deleteCouponUser(@Param("couponId") int couponId,@Param("userId")int userId);

    /**
     * 根据用户和金额获取优惠券
     * @return
     */
    List<Coupon> selectCouponByUserAndAmount(@Param("userId") int userId,@Param("amount") double amount);
}
