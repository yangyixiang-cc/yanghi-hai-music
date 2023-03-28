package com.yanghi.haimusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanghi.haimusic.bean.Comments;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论映射类
 */
@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {

}
