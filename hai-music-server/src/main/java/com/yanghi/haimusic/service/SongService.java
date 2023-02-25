package com.yanghi.haimusic.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanghi.haimusic.bean.Singer;
import com.yanghi.haimusic.bean.Song;

import java.util.List;
import java.util.Map;

public interface SongService extends IService<Song> {

    //获取指定id对应的评论信息
    List<Map<String,Object>> returnCommentsPageBySongId(Integer id, Integer pageNum, Integer num);

    //根据歌手id返回歌曲分页数据 默认 1  1  6
    Page<Song> selectSongPageBySingerId(Integer id, Integer pageNum, Integer num);

    //根据曲风 style 返回对应的歌曲排行榜 默认获取前20条
    Page<Song> returnSongPagesByStyle(Integer num,String style);

    //根据区域 region 返回对应的歌曲排行榜 默认获取前20条
    Page<Song> returnSongPagesByRegion(Integer num,String region);

    //返回歌曲总体的排行榜按照playVolume排序，返回前20条数据
    Page<Song> returnSongPagesByAllPlayVolume(Integer num);

    List<Song> returnSearchSongsByKey(String keyword);
}
