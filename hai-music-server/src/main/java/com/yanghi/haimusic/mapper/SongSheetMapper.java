package com.yanghi.haimusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanghi.haimusic.bean.Song;
import com.yanghi.haimusic.bean.SongSheet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 歌单映射类
 */
@Mapper
public interface SongSheetMapper extends BaseMapper<SongSheet> {

}
