package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.RefundService;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by liying on 2019/4/20.
 */
@RestController
@RequestMapping("/refund")
public class RefundController {

    @Autowired
    RefundService refundService;

    @PostMapping("/publish")
    public ResponseVO publishRefund(@RequestBody RefundForm refundForm){
        return refundService.publishRefund(refundForm);
    }
    @GetMapping("/get")
    //getRefunds返回值是List<Refund>
    public ResponseVO getRefunds(){
        return refundService.getRefunds();
    }

    //以下为自己写的
    @GetMapping("/get/{movieId}")
    public ResponseVO getRefundsByMovieId(@PathVariable int movieId){
        return refundService.getRefundsByMovieId(movieId);
    }
}
