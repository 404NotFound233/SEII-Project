package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.GiveCouponVO;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by liying on 2019/4/16.
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @GetMapping("{userId}/get")
    public ResponseVO getCoupons(@PathVariable int userId){
        return couponService.getCouponsByUser(userId);
    }

    @PostMapping("/add")
    public ResponseVO add_Coupon(@RequestBody CouponForm couponForm){
        return couponService.addCoupon(couponForm);
    }

    @PostMapping("/issue/{couponId}/{userId}")
    public ResponseVO issue_Coupon(@PathVariable int couponId,@PathVariable int userId){
        return couponService.issueCoupon(couponId,userId);
    }

    @PostMapping("/delete/{couponId}/{userId}")
    public ResponseVO delete_Coupon(@PathVariable int couponId,@PathVariable int userId){
        return couponService.deleteCouponUser(couponId,userId);
    }
    
    //批量赠送优惠券
    @PostMapping(value = "/give")
    public ResponseVO giveCoupon(@RequestBody GiveCouponVO giveCoupon){
        return couponService.giveCoupon(giveCoupon);
    }

}
