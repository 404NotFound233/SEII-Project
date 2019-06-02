package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallDeleteForm;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    @Autowired
    private HallMapper hallMapper;


    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addHall(HallForm addHallForm) {
        try{
            hallMapper.insertOneHall(addHallForm);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateHall(HallForm updateHallForm) {
        try{
            hallMapper.updateHall(updateHallForm);
            return ResponseVO.buildSuccess();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO searchHall(int hallId) {
        try{
            Hall hall=hallMapper.selectHallById(hallId);
            return ResponseVO.buildSuccess(new HallVO(hall));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ResponseVO deleteHall(HallDeleteForm hallDeleteForm){
        try{
            List<Integer> hallIdList = hallDeleteForm.getHallIdList();
            if(hallIdList.size() == 0){
                return ResponseVO.buildFailure("id列表不可为空");
            }
            if(isUsed(hallIdList)){
                return ResponseVO.buildFailure("该影厅已被使用不可修改");
            }
            hallMapper.deleteHall(hallIdList);
            return ResponseVO.buildSuccess();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    boolean isUsed(List<Integer> hallIdList) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        Date lastDay;
        for(int id: hallIdList) {
            //System.out.println("id:"+id+hallMapper.getEndDate(id));
            Date endDate=hallMapper.getEndDate(id);
            if (endDate==null){
                return false;//影厅未被占用
            }
            else {
                lastDay = simpleDateFormat.parse(simpleDateFormat.format(endDate));
                if (lastDay.before(today)) {
                    return false;//影厅未被占用
                }
            }
        }
        return true;//影厅现存排片，不可修改
    }

    @Override
    public Hall getHallById(int id) {
        try {
            return hallMapper.selectHallById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    
    @Override
    public List<Hall> getAllHall() {
    	try {
    		return hallMapper.selectAllHall();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    private List<HallVO> hallList2HallVOList(List<Hall> hallList){
        List<HallVO> hallVOList = new ArrayList<>();
        for(Hall hall : hallList){
            hallVOList.add(new HallVO(hall));
        }
        return hallVOList;
    }
}
