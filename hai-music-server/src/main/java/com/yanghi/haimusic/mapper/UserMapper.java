package com.yanghi.haimusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanghi.haimusic.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息映射类
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
