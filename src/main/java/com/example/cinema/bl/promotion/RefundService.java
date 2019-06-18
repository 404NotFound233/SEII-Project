package com.example.cinema.bl.promotion;

import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;


public interface RefundService {
    /**
     * 发布退票策略
     * @return
     */
    ResponseVO publishRefund(RefundForm refundForm);

    /**
     * 获取退票策略
     * @return
     */
    ResponseVO getRefunds();

    /**
     * 根据movieId获取退票策略
     * @return
     */
    ResponseVO getRefundsByMovieId(int movieId);


}
