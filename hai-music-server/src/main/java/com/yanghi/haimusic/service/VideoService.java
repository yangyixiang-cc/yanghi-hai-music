package com.yanghi.haimusic.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanghi.haimusic.bean.Singer;
import com.yanghi.haimusic.bean.Video;

import java.util.List;
import java.util.Map;

public interface VideoService extends IService<Video> {

    Page<Video> returnVideoPage(Integer pageNum,Integer num,String region,String edition);

    //根据region区域 获取MV对应的排行榜 默认返回12条数据
    List<Video> returnVideoRankingsByRegion(Integer num, String region);


    List<Video> returnRecommendVideoInfo();

    //根据videoId 返回对应的视频信息和歌手信息
    Map<String,Object> returnVideoInfoAndSingerInfoById(String id);


    //根据videoId 返回对应的评论分页 pageNum 默认为1 num 默认为5
    List<Map<String,Object>> returnCommentPageByVideoId(Integer id,Integer pageNum,Integer num);

    List<Video> returnRecommendVideoByStyle(Integer num,String style);

    List<Video> returnSearchVideosByKey(String keyword);
}
