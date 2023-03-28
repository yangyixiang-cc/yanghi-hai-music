package com.yanghi.haimusic.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanghi.haimusic.bean.Singer;
import com.yanghi.haimusic.bean.Song;
import com.yanghi.haimusic.bean.Video;
import com.yanghi.haimusic.service.VideoService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    //根据id返回对应的视频信息 包括视频信息和对应的歌手信息
    @GetMapping("/{id}")
    public Result returnVideoInfoById(@PathVariable("id") String id){
        return Result.ok(videoService.returnVideoInfoAndSingerInfoById(id));
    }
    //首页推荐返回数据 只返回8条数据
    @GetMapping("/recommend")
    public Result returnRecommendVideoInfo(){
        return Result.ok(videoService.returnRecommendVideoInfo());
    }

    //获取全部MV分页数据 默认第一页，默认每次加载16条数据
    @GetMapping("/page")
    public Result returnSingerPageInfo(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "num",defaultValue = "16") Integer num,
            @RequestParam(value = "region") String region,
            @RequestParam(value = "edition") String edition
    ){
        return Result.ok(videoService.returnVideoPage(pageNum,num,region,edition));
    }

    //返回由区域region的MV排行的前12条数据
    @GetMapping("/video_rankings")
    public Result returnSingerPageInfo(
            @RequestParam(value = "num",defaultValue = "12") Integer num,
            @RequestParam(value = "region") String region
    ){
        return Result.ok(videoService.returnVideoRankingsByRegion(num,region));
    }

    //获取指定id的视频对应的评论分页 pageNum 默认为1 num 默认为5
    @GetMapping("/comments")
    public Result returnCommentsPageByVideoId(
            @RequestParam("id") Integer id,
            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
            @RequestParam(value = "num",defaultValue = "5") Integer num
    ){
        List<Map<String, Object>> mapList = videoService.returnCommentPageByVideoId(id, pageNum, num);
        if(mapList.isEmpty()){
            return Result.failed("数据为空");
        }
        return Result.ok(mapList);
    }


    //根据style获取对应的mv推荐信息 默认5条
    @GetMapping("/recommend/style")
    public Result returnRecommendVideoByStyle(
            @RequestParam(value = "num",defaultValue = "5") Integer num,
            @RequestParam(value = "style") String style
    ){
        return Result.ok(videoService.returnRecommendVideoByStyle(num,style));
    }

}
