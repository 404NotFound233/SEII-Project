package com.example.cinema.bl.promotion;

import com.example.cinema.vo.ActivityForm;
import com.example.cinema.vo.ResponseVO;

/**
 * Created by liying on 2019/4/20.
 */
public interface ActivityService {
    /**
     * 发布优惠活动
     * @return
     */
    ResponseVO publishActivity(ActivityForm activityForm);

    /**
     * 获取所有的优惠活动
     * @return
     */
    ResponseVO getActivities();

    /**
     * 根据movieId获取优惠活动
     * @return
     */
    ResponseVO getActivitiesByMovieId(int movieId);


}
