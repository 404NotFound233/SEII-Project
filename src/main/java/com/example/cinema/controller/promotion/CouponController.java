package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

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

    //以下为自己写的
    @PostMapping("/add")
    public ResponseVO add_Coupon(@RequestBody CouponForm couponForm){
        return couponService.addCoupon(couponForm);
    }

    //以下为自己写的
    @PostMapping("/issue/{couponId}/{userId}")
    public ResponseVO issue_Coupon(@PathVariable int couponId,@PathVariable int userId){
        return couponService.issueCoupon(couponId,userId);
    }

    //以下为自己写的
    @PostMapping("/delete/{couponId}/{userId}")
    public ResponseVO delete_Coupon(@PathVariable int couponId,@PathVariable int userId){
        return couponService.deleteCouponUser(couponId,userId);
    }
}
