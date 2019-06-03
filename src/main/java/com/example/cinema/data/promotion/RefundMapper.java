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

    int insertRefund(Refund refund);

    //清除refund和refund_movie数据库中所有内容
    void clearRefund();
    void clearRefundAndMovie();
	//不知道下面这个方法有啥子用
	//我现在就是不太想实现它
    int insertRefundAndMovie(@Param("refundId") int refundId,@Param("movieId") List<Integer> movieId);

    List<Refund> selectRefunds();
	//突然发现这个方法贼有用
	//突然意识到上面的方法贼有用
    //助教nb
    List<Refund> selectRefundsByMovie(int movieId);

    Refund selectById(int id);
	//突然又觉得这个方法没啥子用
    List<Refund> selectRefundsWithoutMovie();






}
