package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanghi.haimusic.bean.Singer;
import com.yanghi.haimusic.mapper.SingerMapper;
import com.yanghi.haimusic.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {


    @Override
    public List<Singer> returnSearchSingersByKey(String keyword) {
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.select("id","name","pic").like("name",keyword);
        return this.list(singerQueryWrapper);
    }

    @Override
    public List<Singer> returnSilmarSingerByIntroduction(String location, Integer num) {
        Page<Singer> singerPage = new Page<>(1,num);
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.select("id","name","pic").like("location",location);
        return this.page(singerPage,singerQueryWrapper).getRecords();
    }
}
