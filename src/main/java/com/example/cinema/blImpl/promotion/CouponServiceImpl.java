package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.sales.TicketServiceForBl;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.po.Coupon;
import com.example.cinema.po.NormalRecord;
import com.example.cinema.po.VIPRecord;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.GiveCouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by liying on 2019/4/17.
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponMapper couponMapper;
    @Autowired
    private TicketServiceForBl ticketservice;

    @Override
    public ResponseVO getCouponsByUser(int userId) {
        try {
            return ResponseVO.buildSuccess(couponMapper.selectCouponByUser(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addCoupon(CouponForm couponForm) {
        try {
            Coupon coupon=new Coupon();
            coupon.setName(couponForm.getName());
            coupon.setDescription(couponForm.getDescription());
            coupon.setTargetAmount(couponForm.getTargetAmount());
            coupon.setDiscountAmount(couponForm.getDiscountAmount());
            coupon.setStartTime(couponForm.getStartTime());
            coupon.setEndTime(couponForm.getEndTime());
            couponMapper.insertCoupon(coupon);
            return ResponseVO.buildSuccess(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO issueCoupon(int couponId, int userId) {
        try {
            couponMapper.insertCouponUser(couponId,userId);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    //批量赠送
    @Override
    public ResponseVO giveCoupon(GiveCouponVO giveCoupon) {
    	try{
    		 int payTarget = giveCoupon.getPayTarget();
    		 int couponId = giveCoupon.getCouponId();
    	     List<NormalRecord> normalRecordList = ticketservice.collectAllNormalRecord();
             List<VIPRecord> VIPRecordList = ticketservice.collectAllVIPRecord();
             List<NormalRecord> totalRecordList = new ArrayList<NormalRecord>();
             List<Integer> userIdList = new ArrayList<Integer>();
             double sum;
             int idi;
             int idj;
             for (int i=0;i<normalRecordList.size();++i) {
             	sum = 0;
             	idi = normalRecordList.get(i).getUserId();
             	for (int j=0;j<normalRecordList.size();++j) {
             		idj = normalRecordList.get(j).getUserId();
             		if (idj == idi) {
             			sum += normalRecordList.get(j).getAmount();
             		}
             	}
             	for (int j=0;j<VIPRecordList.size();++j) {
             		idj = VIPRecordList.get(j).getUserId();
             		if (idj == idi) {
             			sum += VIPRecordList.get(j).getAmount();
             		}
             	}
             	NormalRecord totalRecord = new NormalRecord();
             	totalRecord.setUserId(idi);
             	totalRecord.setAmount(sum);
             	totalRecordList.add(totalRecord);
             }

             for (int i=0;i<totalRecordList.size();++i) {
             	boolean listready = userIdList.isEmpty() || userIdList.indexOf(totalRecordList.get(i).getUserId()) == -1;
             	if (totalRecordList.get(i).getAmount() >= payTarget && listready) {
             		userIdList.add(totalRecordList.get(i).getUserId());
             	}
             }
            //以上根据payTarget拉取用户名单
            for (int i=0;i<userIdList.size();++i) {
        		couponMapper.insertCouponUser(couponId,userIdList.get(i));
        	}
            
            return ResponseVO.buildSuccess();
            //以上根据用户名单发放优惠券

            
        }catch(Exception e){
            e.printStackTrace();;
            return ResponseVO.buildFailure("失败");
        }

    }
    
    //自己写的
    @Override
    public ResponseVO deleteCouponUser(int couponId,int userId){
        try{
            couponMapper.deleteCouponUser(couponId,userId);
            return ResponseVO.buildSuccess();
        }catch(Exception e){
            e.printStackTrace();;
            return ResponseVO.buildFailure("失败");
        }
    }
    
}
