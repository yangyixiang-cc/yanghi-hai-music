package com.yanghi.haimusic.controller;

import com.yanghi.haimusic.bean.Comments;
import com.yanghi.haimusic.service.CommentsService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 评论类
 * @author 泗安
 */
@RestController
@RequestMapping("/api/comments")
@Validated
public class CommentController {

    @Resource(name = "commentsServiceImpl")
    private CommentsService commentsService;

    /**
     * 接收用户发送的评论存储到数据库中
     * @param comments
     * @return
     */
    @PostMapping("/user")
    public Result saveCommentsByUser(@Valid Comments comments){
        return commentsService.saveUserOneComments(comments);
    }


}
