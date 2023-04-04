package com.yanghi.haimusic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanghi.haimusic.bean.Comments;
import com.yanghi.haimusic.utils.Result;

public interface CommentsService extends IService<Comments> {

    /**
     * 保存用户评论
     * @param comments
     * @return
     */
    Result saveUserOneComments(Comments comments);

}
