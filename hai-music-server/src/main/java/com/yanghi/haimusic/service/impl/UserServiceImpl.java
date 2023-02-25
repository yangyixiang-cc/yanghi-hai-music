package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanghi.haimusic.bean.User;
import com.yanghi.haimusic.mapper.UserMapper;
import com.yanghi.haimusic.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
