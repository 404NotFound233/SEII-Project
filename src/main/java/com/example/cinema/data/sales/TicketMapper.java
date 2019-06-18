package com.example.cinema.data.sales;

import com.example.cinema.po.Ticket;
import com.example.cinema.po.NormalRecord;
import com.example.cinema.po.VIPRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Mapper
public interface TicketMapper {
    /**
     * 添加电影票
     * @return
     */
    int insertTicket(Ticket ticket);

    /**
     * 添加多张电影票
     * @return
     */
    int insertTickets(List<Ticket> tickets);

    /**
     * 删除电影票
     * @return
     */
    void deleteTicket(int ticketId);

    /**
     * 更新电影票的状态
     * @return
     */
    void updateTicketState(@Param("ticketId") int ticketId, @Param("state") int state);

    /**
     * 更新电影票的总价
     * @return
     */
    void updateTicketTotal(@Param("ticketId") int ticketId, @Param("actualTotal") double actualTotal);

    /**
     * 更新电影票的付款方式
     * @return
     */
    void updateTicketLocation(@Param("ticketId") int ticketId, @Param("location") int location);

    /**
     * 根据scheduleId获取电影票
     * @return
     */
    List<Ticket> selectTicketsBySchedule(int scheduleId);

    /**
     * 根据scheduleId和座位获取电影票
     * @return
     */
    Ticket selectTicketByScheduleIdAndSeat(@Param("scheduleId") int scheduleId, @Param("column") int columnIndex, @Param("row") int rowIndex);

    /**
     * 根据id获取电影票
     * @return
     */
    Ticket selectTicketById(int id);

    /**
     * 根据userId获取电影票
     * @return
     */
    List<Ticket> selectTicketByUser(int userId);

    /**
     * 清除过期未支付的票
     * @return
     */
    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredTicket();

    /**
     * 添加会员卡消费记录
     * @return
     */
    void insertVIPRecord(@Param("userId") int userId, @Param("amount") double amount, @Param("before_Balance") double before_Balance, @Param("reason") int reason);

    /**
     * 添加银行卡消费记录
     * @return
     */
    void insertNormalRecord(@Param("userId") int userId, @Param("amount") double amount, @Param("reason") int reason);

    /**
     * 根据userId获取银行卡消费记录
     * @return
     */
    List<NormalRecord> selectNormalRecord(int userId);

    /**
     * 根据userId获取会员卡消费记录
     * @return
     */
    List<VIPRecord> selectVIPRecord(int userId);

    /**
     * 获取所有的银行卡消费记录
     * @return
     */
    List<NormalRecord> selectAllNormalRecord();

    /**
     * 获取所有的会员卡消费记录
     * @return
     */
    List<VIPRecord> selectAllVIPRecord();
}

