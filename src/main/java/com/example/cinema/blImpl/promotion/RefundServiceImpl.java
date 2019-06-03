package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.RefundService;
import com.example.cinema.data.promotion.RefundMapper;
import com.example.cinema.po.Refund;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 /*
 * Created by zl on 2019/5/24.
 */
@Service
public class RefundServiceImpl implements RefundService {

    @Autowired
    RefundMapper refundMapper;

    @Transactional
    public ResponseVO publishRefund(RefundForm refundForm) {
        try {
            System.out.println("publishrefund:"+refundForm.getMovieList().size());
            System.out.println("publishrefund:"+refundForm.getFulltime());
            refundMapper.clearRefund();
            refundMapper.clearRefundAndMovie();
            Refund refund = new Refund();
            refund.setDiscount(refundForm.getDiscount());
            refund.setDiscounttime(refundForm.getDiscounttime());
            refund.setFreetime(refundForm.getFreetime());
            refund.setFulltime(refundForm.getFulltime());
            refundMapper.insertRefund(refund);
            if(refundForm.getMovieList()!=null&&refundForm.getMovieList().size()!=0) {
                refundMapper.insertRefundAndMovie(refund.getId(), refundForm.getMovieList());
            }
            return ResponseVO.buildSuccess(refundMapper.selectById(refund.getId()).getVO());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getRefunds() {
        try {
            return ResponseVO.buildSuccess(refundMapper.selectRefunds());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //以下为自己写的
    @Override
    public ResponseVO getRefundsByMovieId(int movieId){
        try {
            return ResponseVO.buildSuccess(refundMapper.selectRefundsByMovie(movieId).stream().map(Refund::getVO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
