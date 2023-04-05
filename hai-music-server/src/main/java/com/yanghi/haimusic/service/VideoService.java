package com.yanghi.haimusic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanghi.haimusic.bean.Video;
import com.yanghi.haimusic.utils.Result;

import java.util.List;

public interface VideoService extends IService<Video> {

    Result returnVideoPage(Integer pageNum,Integer num,String region,String edition);

    //根据region区域 获取MV对应的排行榜 默认返回12条数据
    Result returnVideoRankingsByRegion(Integer num, String region);


    Result returnRecommendVideoInfo();

    //根据videoId 返回对应的视频信息和歌手信息
    Result returnVideoInfoAndSingerInfoById(String id);


    //根据videoId 返回对应的评论分页 pageNum 默认为1 num 默认为5
    Result returnCommentPageByVideoId(Integer id,Integer pageNum,Integer num);

    Result returnRecommendVideoByStyle(Integer num,String style);

    List<Video> returnSearchVideosByKey(String keyword);
}
