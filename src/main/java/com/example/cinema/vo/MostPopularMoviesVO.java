package com.example.cinema.vo;

import com.example.cinema.po.MostPopularMovies;

public class MostPopularMoviesVO {
    private Integer movieId;
    private String name;
    /**
     * 票房(单位：元)，(PS:如果后续数据量大，可自行处理单位，如改成单位：万元)
     */
    private Integer boxOffice;

    public MostPopularMoviesVO(MostPopularMovies mostPopularMovies){
        this.movieId = mostPopularMovies.getMovieId();
        this.name = mostPopularMovies.getName();
        this.boxOffice=mostPopularMovies.getBoxOffice();
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBoxOffice(){ return boxOffice;}

    public void setBoxOffice(Integer boxOffice){
        this.boxOffice=boxOffice;
    }
}
