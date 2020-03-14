package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

public interface FilmServiceApi {
    // 获取banner
    List<BannerVO> getBanners();
    // 获取热映影片
    FilmVO getHotFilms(boolean isLimit,int nums);
    // 获取即将上映影片
    FilmVO getsoonFilms(boolean isLimit,int nums);
    // 获取票房排行
    List<FilmInfo> getBoxRanking();
    // 获取人气排行
    List<FilmInfo> getExpectRanking();
    // 获取top100
    List<FilmInfo> getTop();

    // 获取影片类别
    List<CatVO> getCats();
    // 获取影片来源
    List<SourceVO> getSources();
    // 获取影片年代
    List<YearVO> getYears();
}
