package com.yanghi.haimusic.service;

import com.yanghi.haimusic.utils.Result;

public interface SongSheetAndSongService {

    //根据歌单id返回所有歌单中包含的歌曲分页数据
    Result selectSongPageBySongSheetId(Integer id, Integer pageNum, Integer num);
}
