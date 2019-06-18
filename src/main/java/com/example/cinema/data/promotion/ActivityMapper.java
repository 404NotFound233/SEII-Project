package com.example.cinema.data.promotion;

import com.example.cinema.po.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liying on 2019/4/20.
 */
@Mapper
public interface ActivityMapper {
    /**
     * 添加活动
     * @return
     */
    int insertActivity(Activity activity);

    /**
     * 添加对应于电影的活动
     * @return
     */
    int insertActivityAndMovie(@Param("activityId") int activityId,@Param("movieId") List<Integer> movieId);

    /**
     * 获取所有活动
     * @return
     */
    List<Activity> selectActivities();

    /**
     * 根据movieId获取活动
     * @return
     */
    List<Activity> selectActivitiesByMovie(int movieId);

    /**
     * 根据id获取活动
     * @return
     */
    Activity selectById(int id);

    /**
     * 获取不对应于电影的活动
     * @return
     */
    List<Activity> selectActivitiesWithoutMovie();
}
