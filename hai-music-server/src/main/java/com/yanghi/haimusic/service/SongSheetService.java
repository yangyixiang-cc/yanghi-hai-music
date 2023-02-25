package com.yanghi.haimusic.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanghi.haimusic.bean.SongSheet;

import java.util.List;
import java.util.Map;

public interface SongSheetService extends IService<SongSheet>{


    //获取指定id对应的评论信息
    List<Map<String,Object>> returnCommentsPageBySongSheetId(Integer id,Integer pageNum, Integer num);


    //返回首页歌单推荐数据
    List<SongSheet> returnRecommendSongSheetInfo();

    Page<SongSheet> returnSongSheetPageInfo(Integer pageNum, Integer num,String style);

    List<SongSheet> returnSearchSongSheetsByKey(String keyword);
}
