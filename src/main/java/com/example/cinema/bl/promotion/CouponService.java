package com.example.cinema.bl.promotion;

import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.GiveCouponVO;
import com.example.cinema.vo.ResponseVO;

/**
 * Created by liying on 2019/4/17.
 */
public interface CouponService {
    /**
     * 根据userId获取优惠券
     * @return
     */
    ResponseVO getCouponsByUser(int userId);

    /**
     * 添加优惠券
     * @return
     */
    ResponseVO addCoupon(CouponForm couponForm);

    /**
     * 根据userId给用户增加优惠券
     * @return
     */
    ResponseVO issueCoupon(int couponId,int userId);

    /**
     * 删除用户的优惠券
     * @return
     */
    ResponseVO deleteCouponUser(int couponId,int userId);

    /**
     * 赠送优惠券
     * @return
     */
    ResponseVO giveCoupon(GiveCouponVO giveCoupon);
    

}
