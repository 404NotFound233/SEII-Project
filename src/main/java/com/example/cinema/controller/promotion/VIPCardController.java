package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;


    @PostMapping("/add/{userId}/{price}")
    public ResponseVO addVIP(@PathVariable int userId,@PathVariable double price){
        return vipService.addVIPCard(userId,price);
    }
    @GetMapping("/{userId}/get")

    public ResponseVO getVIP(@PathVariable int userId){
        return vipService.getCardByUserId(userId);
    }

    @GetMapping("/get/record/{userId}")
    public ResponseVO getRecord(@PathVariable int userId){
        return vipService.getRecord(userId);
    }

    @GetMapping("/getVIPInfo")
    public ResponseVO getVIPInfo(){
        return vipService.getVIPInfo();
    }

    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPCardForm vipCardForm){
        return vipService.charge(vipCardForm);
    }

    @PostMapping("/pay/{userId}/{balance}")
    public ResponseVO pay(@PathVariable int userId,@PathVariable double balance){
        return vipService.payByCard(userId,balance);
    }

    //wqy
    @PostMapping("/publish")
    public ResponseVO publishVip(@RequestBody VIPInfoVO vip){
        return vipService.publishVIP(vip);
    }

    //wqy
    @PostMapping("/modify")
    public ResponseVO modifyVip(@RequestBody VIPInfoVO vip){
        return vipService.modifyVIP(vip);
    }

    //wqy
    @GetMapping("/length")
    public ResponseVO getLength(){
        return vipService.getLength();
    }
}
