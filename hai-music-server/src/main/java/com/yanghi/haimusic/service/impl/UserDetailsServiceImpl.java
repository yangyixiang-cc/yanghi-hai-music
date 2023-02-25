package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanghi.haimusic.bean.UserDetails;
import com.yanghi.haimusic.mapper.UserDetailsMapper;
import com.yanghi.haimusic.service.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl extends ServiceImpl<UserDetailsMapper, UserDetails> implements UserDetailsService {

}
