package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.data.promotion.ActivityMapper;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    HallServiceForBl hallService;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    ScheduleMapper scheduleMapper;
    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
        try {
            List<TicketVO> ticketVOList=new ArrayList<TicketVO>();
            for(int i=0;i<ticketForm.getSeats().size();i++){
                Ticket t=new Ticket();
                t.setUserId(ticketForm.getUserId());
                t.setScheduleId(ticketForm.getScheduleId());
                t.setColumnIndex(ticketForm.getSeats().get(i).getColumnIndex());
                t.setRowIndex(ticketForm.getSeats().get(i).getRowIndex());
                t.setState(0);
                ticketMapper.insertTicket(t);
                Ticket t1=ticketMapper.selectTicketByScheduleIdAndSeat(ticketForm.getScheduleId(),ticketForm.getSeats().get(i).getColumnIndex(),ticketForm.getSeats().get(i).getRowIndex());
                ticketVOList.add(t1.getVO());
            }

            List<Activity> activities=activityMapper.selectActivities();
            List<Coupon> coupons=couponMapper.selectCouponByUser(ticketForm.getUserId());
            TicketWithCouponVO ticketWithCouponVO=new TicketWithCouponVO();
            ticketWithCouponVO.setActivities(activities);
            ticketWithCouponVO.setCoupons(coupons);
            ticketWithCouponVO.setTicketVOList(ticketVOList);
            ScheduleItem scheduleItem=scheduleMapper.selectScheduleById(ticketForm.getScheduleId());
            ticketWithCouponVO.setTotal(scheduleItem.getFare()*ticketVOList.size());
            return ResponseVO.buildSuccess(ticketWithCouponVO);

            /*
            //ticketMapper.deleteTicket(70);
            int UserId=ticketForm.getUserId();
            int scheduleId=ticketForm.getScheduleId();
            List<SeatForm> seats=ticketForm.getSeats();
            List<Ticket> ticketList=new ArrayList<Ticket>();
            List<TicketVO> ticketVOList=new ArrayList<TicketVO>();
            for(int i=0;i<seats.size();i++){
                SeatForm seat=seats.get(i);
                Ticket ticket=new Ticket();
                ticket.setUserId(ticketForm.getUserId());
                ticket.setState(0);
                ticket.setScheduleId(ticketForm.getScheduleId());
                ticket.setRowIndex(seat.getRowIndex());
                ticket.setColumnIndex(seat.getColumnIndex());
                ticket.setTime( new Timestamp(System.currentTimeMillis()));
                ticket.setId(996);
                ticketList.add(ticket);
                ticketVOList.add(ticket.getVO());
            }
            ticketMapper.insertTickets(ticketList);
            List<Activity> activities=activityMapper.selectActivities();
            List<Coupon> coupons=couponMapper.selectCouponByUser(UserId);
            TicketWithCouponVO ticketWithCouponVO=new TicketWithCouponVO();
            ticketWithCouponVO.setActivities(activities);
            ticketWithCouponVO.setCoupons(coupons);
            ticketWithCouponVO.setTicketVOList(ticketVOList);
            ScheduleItem scheduleItem=scheduleMapper.selectScheduleById(scheduleId);
            ticketWithCouponVO.setTotal(scheduleItem.getFare()*ticketList.size());
            /*
            System.out.println("size:"+ticketVOList.size());
            List<Integer> id=new ArrayList<Integer>();
            for(int i=63;i<70;i++){
                id.add(i);
            }
            cancelTicket(id);
            return ResponseVO.buildSuccess(ticketWithCouponVO);*/
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    @Transactional
    //此处的id为ticketId
    public ResponseVO completeTicket(List<Integer> id, int couponId) {
        try{
            System.out.println(id.size()+"****");
            int userId=0;
            for(int i=0;i<id.size();i++){
                ticketMapper.updateTicketState(id.get(i),1);
                Ticket t=ticketMapper.selectTicketById(id.get(i));
                userId=t.getUserId();
                System.out.println(couponId);
            }
            couponMapper.deleteCouponUser(couponId,userId);//取消优惠券
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
            ScheduleItem schedule=scheduleService.getScheduleItemById(scheduleId);
            Hall hall=hallService.getHallById(schedule.getHallId());
            int[][] seats=new int[hall.getRow()][hall.getColumn()];
            tickets.stream().forEach(ticket -> {
                seats[ticket.getRowIndex()][ticket.getColumnIndex()]=1;
            });
            ScheduleWithSeatVO scheduleWithSeatVO=new ScheduleWithSeatVO();
            scheduleWithSeatVO.setScheduleItem(schedule);
            scheduleWithSeatVO.setSeats(seats);
            return ResponseVO.buildSuccess(scheduleWithSeatVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTicketByUser(int userId) {
        try{
            return ResponseVO.buildSuccess(ticket2TicketVOList(ticketMapper.selectTicketByUser(userId)));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    private List<TicketVO> ticket2TicketVOList(List<Ticket> ticketList){
        List<TicketVO> ticketVOList = new ArrayList<>();
        for(Ticket ticket : ticketList){
            ticketVOList.add(ticket.getVO());
        }
        return ticketVOList;
    }

    @Override
    @Transactional
    public ResponseVO completeByVIPCard(List<Integer> id, int couponId) {
        System.out.println("look!!!!!");
        try{
            System.out.println("look!!");
            int userId=0;
            for(int i=0;i<id.size();i++){
                ticketMapper.updateTicketState(id.get(i),1);
                Ticket t=ticketMapper.selectTicketById(id.get(i));
                userId=t.getUserId();
            }
            System.out.println("look");
            couponMapper.deleteCouponUser(couponId,userId);//取消优惠券
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO cancelTicket(List<Integer> id) {
        try {
            for(int i=0;i<id.size();i++){
                int oneid=id.get(i);
                ticketMapper.deleteTicket(oneid);
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }



}
