package com.yanghi.haimusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanghi.haimusic.bean.UserDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户详细信息映射类
 */
@Mapper
public interface UserDetailsMapper extends BaseMapper<UserDetails> {

}
