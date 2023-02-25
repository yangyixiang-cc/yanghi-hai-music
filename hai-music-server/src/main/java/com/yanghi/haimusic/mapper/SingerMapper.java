package com.yanghi.haimusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanghi.haimusic.bean.Singer;
import org.apache.ibatis.annotations.Mapper;

/**
 * 歌手映射类
 */

@Mapper
public interface SingerMapper extends BaseMapper<Singer> {

}
