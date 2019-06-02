package com.example.cinema.data.management;

import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 * @author fjj
 * @date 2019/4/11 3:46 PM
 */
@Mapper
public interface HallMapper {
    /**
     * 查询所有影厅信息
     * @return
     */
    List<Hall> selectAllHall();

    /**
     * 根据id查询影厅
     * @return
     */
    Hall selectHallById(@Param("hallId") int hallId);

    /**
     * 根据影厅名查找影厅
     * @return
     */
    Hall selectHallByName(@Param("name") String name);

    /**
     * 新建影厅
     * @return
     */
    int insertOneHall(HallForm addHallForm);

    /**
     * 更新影厅信息
     * @return
     */
    int updateHall(HallForm updateHallForm);

    /**
     * 删除影厅
     * @return
     */
    int deleteHall(List<Integer> hallIdList);

    /**
     *根据影厅id获取最后使用该影厅的日期。
     * @return
     */
    Date getEndDate(int hallId);
}
