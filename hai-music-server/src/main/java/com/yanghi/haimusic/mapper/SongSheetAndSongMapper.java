package com.yanghi.haimusic.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanghi.haimusic.bean.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 歌单关联歌曲映射类
 */

@Mapper
public interface SongSheetAndSongMapper{

    //根据歌单id返回所有歌单中包含的歌曲分页数据
    Page<Song> selectSongPageBySongSheetId(Page<Song> page, @Param("id") Integer id);

}
