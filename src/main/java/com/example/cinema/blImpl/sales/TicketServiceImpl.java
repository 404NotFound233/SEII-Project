package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.data.promotion.ActivityMapper;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.data.promotion.RefundMapper;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.*;

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
    @Autowired
    VIPCardMapper VIPCardMapper;
    @Autowired
    RefundMapper refundMapper;
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
    public ResponseVO completeTicket(List<Integer> id, int couponId,double actualTotal) {
        try{
            int userId=0;
            double average=actualTotal/id.size();
            for(int i=0;i<id.size();i++){
                ticketMapper.updateTicketState(id.get(i),1);
                Ticket t=ticketMapper.selectTicketById(id.get(i));
                userId=t.getUserId();
                ticketMapper.updateTicketLocation(id.get(i),1);
                ticketMapper.updateTicketTotal(id.get(i),average);
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

    @Override
    public ResponseVO changeTicket(int ticketId) {
        try{
            ticketMapper.updateTicketState(ticketId,3);
            return ResponseVO.buildSuccess();
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
    public ResponseVO completeByVIPCard(List<Integer> id, int couponId,double actualTotal) {
        try{
            int userId=0;
            double average=actualTotal/id.size();
            for(int i=0;i<id.size();i++){
                ticketMapper.updateTicketState(id.get(i),1);
                Ticket t=ticketMapper.selectTicketById(id.get(i));
                userId=t.getUserId();
                ticketMapper.updateTicketTotal(id.get(i),average);
                ticketMapper.updateTicketLocation(id.get(i),0);
            }
            couponMapper.deleteCouponUser(couponId,userId);//取消优惠券
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    //传入的id参数是ticketid
    public ResponseVO cancelTicket(int id) {
        try {
            double[] list=new double[4];
            //电影票 ticket
            Ticket ticket=ticketMapper.selectTicketById(id);
            int location=ticket.getLocation();
            int userId=ticket.getUserId();
            VIPCard vipCard=VIPCardMapper.selectCardByUserId(userId);
            //排片情况 scheduleItem
            ScheduleItem scheduleItem=scheduleMapper.selectScheduleById(ticket.getScheduleId());
            int movieId=scheduleItem.getMovieId();
            Date startTime=scheduleItem.getStartTime();
            Date moment=new Date();
            //时间差 day
            long day=(startTime.getTime()-moment.getTime())/(24*60*60*1000);
            //不知道要不要加一
            day+=1;
            System.out.println("时间差："+day);
            int len=refundMapper.selectRefundsByMovie(movieId).size();
            Refund refund=new Refund();
            if(len!=0){refund=refundMapper.selectRefundsByMovie(movieId).get(0);}
            else{
                return ResponseVO.buildFailure("该电影不在退票策略中，不能退票");}
            double money=0;//退款金额
            int fulltime=refund.getFulltime();//min
            //int discounttime=refund.getDiscounttime();
            int freetime=refund.getFreetime();//max
            //System.out.println("全款时间："+freetime);
            //System.out.println("拒绝时间："+fulltime);
            if(day>freetime){money=ticket.getActualTotal();}
            else{if(day<=fulltime){
                    return ResponseVO.buildFailure("距离电影开始时间过近，不能退票");}
                else{money=ticket.getActualTotal()*refund.getDiscount()/100.0;}}

            //System.out.println(money);
            double balancewithrefund=vipCard.getBalance()+money;
            //System.out.println("票价："+ticket.getActualTotal());
            //System.out.println("退票金额："+ticket.getActualTotal()*refund.getDiscount()/100.0);
            if(location==0){
                VIPCardMapper.updateCardBalanceByUserId(userId,balancewithrefund);
                ticketMapper.deleteTicket(id);
                list[0]=vipCard.getBalance();
                list[1]=balancewithrefund;
                list[2]=ticket.getActualTotal();
                list[3]=money;
            }
            else{
                ticketMapper.deleteTicket(id);
                list[0]=-1;
                list[1]=-1;
                list[2]=ticket.getActualTotal();
                list[3]=money;
            }

            return ResponseVO.buildSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //wqy
    @Override
    public ResponseVO VIPRecord(int userId, double amount, double before_Balance, int reason){
        try{
            ticketMapper.insertVIPRecord(userId,amount,before_Balance,reason);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //wqy
    @Override
    public ResponseVO normalRecord(int userId, double amount, int reason){
        try{
            ticketMapper.insertNormalRecord(userId,amount,reason);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //wqy
    @Override
    public ResponseVO getNormalRecord(int userId){
        try{
            return ResponseVO.buildSuccess(normalRecord2NormalRecordVOList(ticketMapper.selectNormalRecord(userId)));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //wqy
    private List<NormalRecordVO> normalRecord2NormalRecordVOList(List<NormalRecord> normalRecordList){
        List<NormalRecordVO> normalRecordVOList = new ArrayList<>();
        for(NormalRecord normalRecord : normalRecordList){
            normalRecordVOList.add(normalRecord.getVO());
        }
        return normalRecordVOList;
    }

    //wqy
    @Override
    public ResponseVO getVIPRecord(int userId){
        try{
            return ResponseVO.buildSuccess(vipRecord2VIPRecordVOList(ticketMapper.selectVIPRecord(userId)));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    //wqy
    private List<VIPRecordVO> vipRecord2VIPRecordVOList(List<VIPRecord> vipRecordList){
        List<VIPRecordVO> vipRecordVOList = new ArrayList<>();
        for(VIPRecord vipRecord : vipRecordList){
            vipRecordVOList.add(vipRecord.getVO());
        }
        return vipRecordVOList;
    }
}
