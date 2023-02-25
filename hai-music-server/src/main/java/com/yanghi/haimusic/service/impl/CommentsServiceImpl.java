package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanghi.haimusic.bean.Comments;
import com.yanghi.haimusic.mapper.CommentsMapper;
import com.yanghi.haimusic.service.CommentsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

}
