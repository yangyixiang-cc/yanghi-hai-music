package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanghi.haimusic.bean.Follow;
import com.yanghi.haimusic.mapper.FollowMapper;
import com.yanghi.haimusic.service.FollowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FollowServiceImpl  extends ServiceImpl<FollowMapper, Follow> implements FollowService {

}
