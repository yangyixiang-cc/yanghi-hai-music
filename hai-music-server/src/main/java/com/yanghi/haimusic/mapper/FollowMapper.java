package com.yanghi.haimusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanghi.haimusic.bean.Follow;
import org.apache.ibatis.annotations.Mapper;

/**
 * 关注映射类
 */
@Mapper
public interface FollowMapper extends BaseMapper<Follow> {
}
