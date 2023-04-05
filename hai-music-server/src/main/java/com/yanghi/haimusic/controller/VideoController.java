package com.yanghi.haimusic.controller;


import com.yanghi.haimusic.service.VideoService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author 泗安
 */
@RestController
@RequestMapping("/api/video")
@Validated
public class VideoController {

    @Resource(name = "videoServiceImpl")
    private VideoService videoService;

    /**
     * 根据id返回对应的视频信息 包括视频信息和对应的歌手信息
     * @param id 视频ID
     * @return Result
     */
    @GetMapping("/{id}")
    public Result returnVideoInfoById(@Min(value = 1, message = "id 必须大于等于 1") @PathVariable("id") String id){
        return videoService.returnVideoInfoAndSingerInfoById(id);
    }

    /**
     * 首页推荐返回数据 只返回8条数据
     * @return Result
     */
    @GetMapping("/recommend")
    public Result returnRecommendVideoInfo(){
        return videoService.returnRecommendVideoInfo();
    }

    /**
     * 获取全部MV分页数据
     * @param pageNum 页码
     * @param num 条数
     * @param region 区域
     * @param edition 版本
     * @return Result
     */
    @GetMapping("/page")
    public Result returnSingerPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1")
            @Min(value = 1, message = "页码必须大于等于 1") Integer pageNum,
            @RequestParam(value = "num", defaultValue = "16")
            @Min(value = 1, message = "每页条数必须大于等于 1")
            @Max(value = 100, message = "每页条数必须小于等于 100") Integer num,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "edition", required = false) String edition
    ){
        return videoService.returnVideoPage(pageNum,num,region,edition);
    }

    /**
     * 返回由区域region的MV排行的前12条数据
     * @param num 条数
     * @param region 区域
     * @return Result
     */
    @GetMapping("/video_rankings")
    public Result returnSingerPageInfo(
            @RequestParam(value = "num", defaultValue = "12")
            @Min(value = 1, message = "条数必须大于等于 1") Integer num,
            @RequestParam(value = "region", required = false) String region
    ){
        return videoService.returnVideoRankingsByRegion(num,region);
    }

    /**
     * 获取指定id的视频对应的评论分页
     * @param id 视频ID
     * @param pageNum 条数
     * @param num 页码
     * @return Result
     */
    @GetMapping("/comments")
    public Result returnCommentsPageByVideoId(
            @RequestParam("id")
            @Min(value = 1, message = "id 必须大于等于 1") Integer id,
            @RequestParam(value = "pageNum",defaultValue = "1")
            @Min(value = 1, message = "页码必须大于等于 1") Integer pageNum,
            @RequestParam(value = "num",defaultValue = "5")
            @Min(value = 1, message = "每页条数必须大于等于 1")
            @Max(value = 100, message = "每页条数必须小于等于 100") Integer num
    ){
       return videoService.returnCommentPageByVideoId(id, pageNum, num);
    }


    /**
     * 根据style获取对应的mv推荐信息
     * @param num 条数
     * @param style 风格
     * @return Result
     */
    @GetMapping("/recommend/style")
    public Result returnRecommendVideoByStyle(
            @RequestParam(value = "num",defaultValue = "5")
            @Min(value = 1, message = "条数必须大于等于 1") Integer num,
            @RequestParam(value = "style", required = false) String style
    ){
        return videoService.returnRecommendVideoByStyle(num,style);
    }

}
