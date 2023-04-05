package com.yanghi.haimusic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanghi.haimusic.bean.SongSheet;
import com.yanghi.haimusic.utils.Result;

import java.util.List;

public interface SongSheetService extends IService<SongSheet>{


    Result getSongSheetById(Integer id);

    Result getAllSongSheet();

    Result getAllSongSheetTotal();

    Result addSongSheet(SongSheet songSheet);

    //获取指定id对应的评论信息
    Result returnCommentsPageBySongSheetId(Integer id,Integer pageNum, Integer num);


    //返回首页歌单推荐数据
    Result returnRecommendSongSheetInfo();

    Result returnSongSheetPageInfo(Integer pageNum, Integer num,String style);

    Result getCommentsCountBySongSheetId(Integer id);

    List<SongSheet> returnSearchSongSheetsByKey(String keyword);
}
