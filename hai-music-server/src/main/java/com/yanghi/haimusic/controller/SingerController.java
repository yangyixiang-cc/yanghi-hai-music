package com.yanghi.haimusic.controller;

import com.yanghi.haimusic.service.SingerService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 歌手控制器
 * @author 泗安
 */
@RestController
@RequestMapping("/api/singer")
@Validated
public class SingerController {

    @Resource(name = "singerServiceImpl")
    private SingerService  singerService;

    /**
     * 通过歌手id返回歌手的详细信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result returnSingerInfo(@Min(value = 1, message = "id 必须大于等于 1")
            @PathVariable("id") Integer id){
        return singerService.getSingerById(id);
    }

    /**
     * 首页推荐返回数据 只返回6条数据
     * @return
     */
    @GetMapping("/recommend")
    public Result returnRecommendSingerInfo(){
        return singerService.getRecommendSingerInfo();
    }

    /**
     * 获取全部歌手分页数据 默认第一页，默认每次加载20条数据
     * @param pageNum 页码
     * @param num 每页条数
     * @return
     */
    @GetMapping("/page")
    public Result returnSingerPageInfo(
            @RequestParam(value = "pageNum",defaultValue = "1")
            @Min(value = 1, message = "页码必须大于等于 1") Integer pageNum,
            @RequestParam(value = "num",defaultValue = "20")
            @Min(value = 1, message = "每页条数必须大于等于 1")
            @Max(value = 100, message = "每页条数必须小于等于 100") Integer num
    ){
       return singerService.getSingerPageInfo(pageNum, num);
    }

    /**
     * 根据关键词搜索相关的歌手，返回歌手列表
     * @param keyword
     * @return
     */
    @GetMapping("/search")
    public Result returnSearchResult(
            @NotBlank(message = "搜索关键词不能为空") @RequestParam("keyword") String keyword
    ){
        return singerService.getSearchSingersByKeyword(keyword);
    }

    /**
     * 获取歌手推荐列表
     * @param location
     * @param num
     * @return
     */
    @GetMapping("/silmar_singer")
    public Result returnSimilarSingerResult(
            @RequestParam(value = "location", required = false) String location,
            @Min(value = 1, message = "条数必须大于等于 1")
            @Max(value = 50, message = "每页条数必须小于等于 50")
            @RequestParam(value = "num",defaultValue = "6") Integer num
    ){
        return singerService.getSimilarSingerByIntroduction(location, num);
    }
}
