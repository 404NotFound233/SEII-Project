package com.example.cinema.blImpl.statistics;

import com.example.cinema.bl.statistics.StatisticsService;
import com.example.cinema.data.statistics.StatisticsMapper;
import com.example.cinema.po.AudiencePrice;
import com.example.cinema.po.MostPopularMovies;
import com.example.cinema.po.Hall;
import com.example.cinema.po.MovieScheduleTime;
import com.example.cinema.po.MovieTotalBoxOffice;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.vo.AudiencePriceVO;
import com.example.cinema.vo.MoviePlacingRateVO;
import com.example.cinema.vo.MovieScheduleTimeVO;
import com.example.cinema.vo.MovieTotalBoxOfficeVO;
import com.example.cinema.vo.MostPopularMoviesVO;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/16 1:34 PM
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
   
	@Autowired
    private StatisticsMapper statisticsMapper;
    @Autowired
    private HallServiceForBl hallservice;
    @Autowired
    private ScheduleServiceForBl scheduleservice;
    
    @Override
    public ResponseVO getScheduleRateByDate(Date date) {
        try{
            Date requireDate = date;
            if(requireDate == null){
                requireDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));

            Date nextDate = getNumDayAfterDate(requireDate, 1);
            return ResponseVO.buildSuccess(movieScheduleTimeList2MovieScheduleTimeVOList(statisticsMapper.selectMovieScheduleTimes(requireDate, nextDate)));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTotalBoxOffice() {
        try {
            return ResponseVO.buildSuccess(movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(statisticsMapper.selectMovieTotalBoxOffice()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAudiencePriceSevenDays() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date startDate = getNumDayAfterDate(today, -6);
            List<AudiencePriceVO> audiencePriceVOList = new ArrayList<>();
            for(int i = 0; i < 7; i++){
                AudiencePriceVO audiencePriceVO = new AudiencePriceVO();
                Date date = getNumDayAfterDate(startDate, i);
                audiencePriceVO.setDate(date);
                List<AudiencePrice> audiencePriceList = statisticsMapper.selectAudiencePrice(date, getNumDayAfterDate(date, 1));
                double totalPrice = audiencePriceList.stream().mapToDouble(item -> item.getTotalPrice()).sum();
                audiencePriceVO.setPrice(Double.parseDouble(String.format("%.2f", audiencePriceList.size() == 0 ? 0 : totalPrice / audiencePriceList.size())));
                audiencePriceVOList.add(audiencePriceVO);
            }
            return ResponseVO.buildSuccess(audiencePriceVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getMoviePlacingRateByDate(Date date) {
        try {
        	MoviePlacingRateVO moviePlacingVO = new MoviePlacingRateVO();
        	
        	Date requireDate = date;
            if(requireDate == null){
                requireDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));
            moviePlacingVO.setDate(requireDate);
            Date nextDate = getNumDayAfterDate(requireDate, 1);                              //以上处理date信息，生成requireDate和nextDate
        	
            List<Hall> halls = hallservice.getAllHall();
        	int totalschedule = 0;
        	int totalseat = 0;
        	for (Hall hall : halls) {
        		List<ScheduleItem> scheduleitem = scheduleservice.getScheduleByHall(hall.getId(), requireDate, nextDate);
        		totalschedule += scheduleitem.size();
        		totalseat += hall.getColumn() * hall.getRow();
        	}                                                                               //以上根据影厅来统计总的座位数和排片数
        	
        	List<AudiencePrice> audiencePriceList = statisticsMapper.selectAudiencePrice(requireDate, nextDate);
                                                                                               //以上统计当天的观影人数
        	moviePlacingVO.setPlacingRate(totalseat, totalschedule, audiencePriceList.size());
        	
        	List<MoviePlacingRateVO> moviePlacingVOList = new ArrayList<MoviePlacingRateVO>();
        	moviePlacingVOList.add(moviePlacingVO);
        	return ResponseVO.buildSuccess(moviePlacingVOList);
        	
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getPopularMovies(int days, int movieNum) {
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date endDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date date = getNumDayAfterDate(endDate, 1-days);
            return ResponseVO.buildSuccess(mostPopularMoviesList2MostPopularMovieVOList(statisticsMapper.selectPopularMovies(date,endDate,movieNum)));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("get fail");
        }
    }


    /**
     * 获得num天后的日期
     * @param oldDate
     * @param num
     * @return
     */
    Date getNumDayAfterDate(Date oldDate, int num){
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(oldDate);
        calendarTime.add(Calendar.DAY_OF_YEAR, num);
        return calendarTime.getTime();
    }



    private List<MovieScheduleTimeVO> movieScheduleTimeList2MovieScheduleTimeVOList(List<MovieScheduleTime> movieScheduleTimeList){
        List<MovieScheduleTimeVO> movieScheduleTimeVOList = new ArrayList<>();
        for(MovieScheduleTime movieScheduleTime : movieScheduleTimeList){
            movieScheduleTimeVOList.add(new MovieScheduleTimeVO(movieScheduleTime));
        }
        return movieScheduleTimeVOList;
    }


    private List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(List<MovieTotalBoxOffice> movieTotalBoxOfficeList){
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOList = new ArrayList<>();
        for(MovieTotalBoxOffice movieTotalBoxOffice : movieTotalBoxOfficeList){
            movieTotalBoxOfficeVOList.add(new MovieTotalBoxOfficeVO(movieTotalBoxOffice));
        }
        return movieTotalBoxOfficeVOList;
    }

    private List<MostPopularMoviesVO> mostPopularMoviesList2MostPopularMovieVOList(List<MostPopularMovies> mostPopularMoviesList){
        List<MostPopularMoviesVO> mostPopularMoviesVOList = new ArrayList<>();
        for(MostPopularMovies mostPopularMovies : mostPopularMoviesList){
            mostPopularMoviesVOList.add(new MostPopularMoviesVO(mostPopularMovies));
        }
        return mostPopularMoviesVOList;
    }
}
