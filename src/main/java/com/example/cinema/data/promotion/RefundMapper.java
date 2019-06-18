package com.example.cinema.data.promotion;

import com.example.cinema.po.Refund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/20.
 */
@Mapper
public interface RefundMapper {
    /**
     * 添加退票策略
     * @return
     */
    int insertRefund(Refund refund);

    /**
     * 清除refund数据库中的所有内容
     * @return
     */
    void clearRefund();

    /**
     * 清除refund_movie数据库中的所有内容
     * @return
     */
    void clearRefundAndMovie();

    /**
     * 添加对应于电影的退票策略
     * @return
     */
    int insertRefundAndMovie(@Param("refundId") int refundId,@Param("movieId") List<Integer> movieId);

    /**
     * 获取全部退票策略
     * @return
     */
    List<Refund> selectRefunds();

    /**
     * 根据movieId获取退票策略
     * @return
     */
    List<Refund> selectRefundsByMovie(int movieId);

    /**
     * 根据id获取退票策略
     * @return
     */
    Refund selectById(int id);

    /**
     * 获取不对应于电影的退票策略
     * @return
     */
    List<Refund> selectRefundsWithoutMovie();






}
