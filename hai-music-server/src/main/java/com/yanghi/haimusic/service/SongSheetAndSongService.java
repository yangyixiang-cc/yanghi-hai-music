package com.yanghi.haimusic.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanghi.haimusic.bean.Song;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SongSheetAndSongService {

    //根据歌单id返回所有歌单中包含的歌曲分页数据
    Page<Song> selectSongPageBySongSheetId(Integer id,Integer pageNum, Integer num);
}
