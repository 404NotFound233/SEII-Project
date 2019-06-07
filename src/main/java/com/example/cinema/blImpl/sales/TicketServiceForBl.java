package com.example.cinema.blImpl.sales;

import java.util.*;

import com.example.cinema.po.NormalRecord;
import com.example.cinema.po.VIPRecord;

/**
 * 
 * @author gumingzheng on 2019/6/1
 *
 */
public interface TicketServiceForBl{
	/**
	 * 搜索全部VIP卡支付的票及价格
	 * @param userId
	 * @return
	 */
	List<NormalRecord> collectAllNormalRecord();
	
	/**
	 * 搜索全部银行卡支付的票及价格
	 * @param userId
	 * @return
	 */
	List<VIPRecord> collectAllVIPRecord();
}