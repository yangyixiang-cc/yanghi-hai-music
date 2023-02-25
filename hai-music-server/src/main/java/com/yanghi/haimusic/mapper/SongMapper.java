package com.yanghi.haimusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanghi.haimusic.bean.Song;
import org.apache.ibatis.annotations.Mapper;

/**
 * 歌曲映射类
 */

@Mapper
public interface SongMapper extends BaseMapper<Song> {

}
