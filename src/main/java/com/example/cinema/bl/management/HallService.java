package com.example.cinema.bl.management;

import com.example.cinema.vo.HallDeleteForm;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.ResponseVO;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
public interface HallService {
    /**
     * 搜索所有影厅
     * @return
     */
    ResponseVO searchAllHall();

    /**
     * 新增影厅
     * @return
     */
    ResponseVO addHall(HallForm addHallForm);

    /**
     * 按影厅名搜索某一影厅
     * @return
     */
    ResponseVO searchHall(int hallId);

    /**
     * 按影厅id删除某一影厅
     * @return
     */
    ResponseVO deleteHall(HallDeleteForm hallDeleteForm);

    /**
     * 更新影厅信息
     * @return
     */
    ResponseVO updateHall(HallForm updateHallForm);
}
