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

    int insertTicket(Ticket ticket);

    int insertTickets(List<Ticket> tickets);

    void deleteTicket(int ticketId);

    void updateTicketState(@Param("ticketId") int ticketId, @Param("state") int state);

    void updateTicketTotal(@Param("ticketId") int ticketId, @Param("actualTotal") double actualTotal);

    void updateTicketLocation(@Param("ticketId") int ticketId, @Param("location") int location);

    List<Ticket> selectTicketsBySchedule(int scheduleId);

    Ticket selectTicketByScheduleIdAndSeat(@Param("scheduleId") int scheduleId, @Param("column") int columnIndex, @Param("row") int rowIndex);

    Ticket selectTicketById(int id);

    List<Ticket> selectTicketByUser(int userId);

    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredTicket();

    void insertVIPRecord(int userId, double amount, double before_Balance, int reason);

    void insertNormalRecord(int userId, double amount, int reason);

    List<NormalRecord> selectNormalRecord(int userId);

    List<VIPRecord> selectVIPRecord(int userId);
}

