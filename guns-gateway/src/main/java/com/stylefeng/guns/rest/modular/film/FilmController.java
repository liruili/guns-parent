package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/film")
public class FilmController {

    private static final String IMG_PRE = "http://meetingshop.cn/";

    @Reference(interfaceClass = FilmServiceApi.class)
    private FilmServiceApi filmServiceApi;

    @GetMapping("getIndex")
    public ResponseVo<FilmIndexVO> getIndex() {
        FilmIndexVO filmIndexVO = new FilmIndexVO();
        filmIndexVO.setBanners(filmServiceApi.getBanners());
        filmIndexVO.setHotFilms(filmServiceApi.getHotFilms(true, 8));
        filmIndexVO.setSoonFilms(filmServiceApi.getsoonFilms(true,8));
        filmIndexVO.setExpectRanking(filmServiceApi.getExpectRanking());
        filmIndexVO.setBoxRanking(filmServiceApi.getBoxRanking());
        filmIndexVO.setTop100(filmServiceApi.getTop());
        return ResponseVo.success(filmIndexVO,IMG_PRE);
    }

    @GetMapping("getConditionList")
    public ResponseVo getConditionList(@RequestParam(value = "catId",required = false,defaultValue = "99")String catId,
                                       @RequestParam(value = "sourceId",required = false,defaultValue = "99")String sourceId,
                                       @RequestParam(value = "yearId",required = false,defaultValue = "99")String yearId){
        FilmConditionVO filmConditionVO = new FilmConditionVO();
        // 类别
        List<CatVO> cats = filmServiceApi.getCats();
        Map<String,CatVO> catsMap = cats.stream().collect(Collectors.toMap(CatVO::getCatId, Function.identity()));
        if(catsMap.containsKey(catId)){
            catsMap.get(catId).setActive(true);
        }else{
            catsMap.get("99").setActive(true);
        }
        filmConditionVO.setCatInfo(new ArrayList<>(catsMap.values()));

        // 来源
        List<SourceVO> sources = filmServiceApi.getSources();
        Map<String,SourceVO> sourcesMap = sources.stream().collect(Collectors.toMap(SourceVO::getSourceId, Function.identity()));
        if(sourcesMap.containsKey(sourceId)){
            sourcesMap.get(sourceId).setActive(true);
        }else{
            sourcesMap.get("99").setActive(true);
        }
        filmConditionVO.setSourceInfo(new ArrayList<>(sourcesMap.values()));

        // 年份
        List<YearVO> years = filmServiceApi.getYears();
        Map<String,YearVO> yearsMap = years.stream().collect(Collectors.toMap(YearVO::getYearId, Function.identity()));
        if(yearsMap.containsKey(yearId)){
            yearsMap.get(yearId).setActive(true);
        }else{
            yearsMap.get("99").setActive(true);
        }
        filmConditionVO.setYearInfo(new ArrayList<>(yearsMap.values()));

        return ResponseVo.success(filmConditionVO);
    }

}
