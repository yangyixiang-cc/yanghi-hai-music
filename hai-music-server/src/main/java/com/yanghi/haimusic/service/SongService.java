package com.yanghi.haimusic.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanghi.haimusic.bean.Song;
import com.yanghi.haimusic.utils.Result;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SongService extends IService<Song> {


    Result getSongById(Integer id);

    Result getRecommendSongInfo();


    //获取指定id对应的评论信息
    Result returnCommentsPageBySongId(Integer id, Integer pageNum, Integer num);

    //根据歌手id返回歌曲分页数据 默认 1  1  6
    Result selectSongPageBySingerId(Integer id, Integer pageNum, Integer num);

    //根据曲风 style 返回对应的歌曲排行榜 默认获取前20条
    Page<Song> returnSongPagesByStyle(Integer num,String style);

    //根据区域 region 返回对应的歌曲排行榜 默认获取前20条
    Page<Song> returnSongPagesByRegion(Integer num,String region);

    //返回歌曲总体的排行榜按照playVolume排序，返回前20条数据
    Page<Song> returnSongPagesByAllPlayVolume(Integer num);

    List<Song> returnSearchSongsByKey(String keyword);

    Result getSongRankings(Integer num, String style, String region);
}
