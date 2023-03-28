package com.yanghi.haimusic.controller;

import com.yanghi.haimusic.bean.Comments;
import com.yanghi.haimusic.service.CommentsService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentsService commentsService;

    //接收用户发送的评论存储到数据库中
    @PostMapping("/user")
    public Result saveCommentsByUser(Comments comments){
        boolean b = commentsService.save(comments);
        if (!b){
            return Result.failed("数据存储失败！");
        }
        return Result.ok();
    }
}
