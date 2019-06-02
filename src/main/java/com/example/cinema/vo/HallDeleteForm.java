package com.example.cinema.vo;
import java.util.List;
/**
 * @author lmt
 */

public class HallDeleteForm {
    /**
     * 要删除的排片信息id列表
     */
    private List<Integer> hallIdList;

    public List<Integer> getHallIdList() {
        return hallIdList;
    }

    public void setHallIdList(List<Integer> hallIdList) {
        this.hallIdList =hallIdList;
    }
}
