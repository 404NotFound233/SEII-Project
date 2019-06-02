package com.example.cinema.controller.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @PostMapping("/vip/buy/{ticketId}/{couponId}")
    public ResponseVO buyTicketByVIPCard(@PathVariable List<Integer> ticketId, @PathVariable int couponId){
        return ticketService.completeByVIPCard(ticketId,couponId);
    }

    @PostMapping("/lockSeat")
    public ResponseVO lockSeat(@RequestBody TicketForm ticketForm){
        return ticketService.addTicket(ticketForm);
    }
    @PostMapping("/buy/{ticketId}/{couponId}")
    public ResponseVO buyTicket(@PathVariable List<Integer> ticketId,@PathVariable int couponId){
        return ticketService.completeTicket(ticketId,couponId);
    }
    @GetMapping("/get/{userId}")
    public ResponseVO getTicketByUserId(@PathVariable int userId){
        return ticketService.getTicketByUser(userId);
    }

    @GetMapping("/get/occupiedSeats")
    public ResponseVO getOccupiedSeats(@RequestParam int scheduleId){
        return ticketService.getBySchedule(scheduleId);
    }
    @PostMapping("/cancel")
    public ResponseVO cancelTicket(@RequestParam List<Integer> ticketId) {
        return ticketService.cancelTicket(ticketId);
    }

    @PostMapping("/VIPRecord/{userId}/{amount}/{before_Balance}/{reason}")
    public ResponseVO VIPRecord(@PathVariable int userId,@PathVariable double amount,@PathVariable double before_Balance,@PathVariable int reason){
        return ticketService.VIPRecord(userId,amount,before_Balance,reason);
    }

    @PostMapping("/normalRecord/{userId}/{amount}/{reason}")
    public ResponseVO normalRecord(@PathVariable int userId,@PathVariable double amount,@PathVariable int reason){
        return ticketService.normalRecord(userId,amount,reason);
    }

    @GetMapping("/get/normalRecord/{userId}")
    public ResponseVO getNormalRecord(@PathVariable int userId){
        return ticketService.getNormalRecord(userId);
    }

    @GetMapping("/get/VIPRecord/{userId}")
    public ResponseVO getVIPRecord(@PathVariable int userId){
        return ticketService.getVIPRecord(userId);
    }
}
