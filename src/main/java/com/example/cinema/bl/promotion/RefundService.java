package com.example.cinema.bl.promotion;

import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;

/**
 * Created by liying on 2019/4/20.
 */
public interface RefundService {
    
    ResponseVO publishRefund(RefundForm refundForm);

    ResponseVO getRefunds();

    //以下为我自己写的
    ResponseVO getRefundsByMovieId(int movieId);


}
