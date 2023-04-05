package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanghi.haimusic.bean.Singer;
import com.yanghi.haimusic.mapper.SingerMapper;
import com.yanghi.haimusic.service.SingerService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {


    @Transactional(readOnly = true)
    @Override
    public Result<List<Singer>> getSearchSingersByKeyword(String keyword) {
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.select("id","name","pic").like("name",keyword);
        return Result.ok(this.list(singerQueryWrapper));
    }

    @Transactional(readOnly = true)
    @Override
    public Result<List<Singer>> getSimilarSingerByIntroduction(String location, Integer num) {
        Page<Singer> singerPage = new Page<>(1,num);
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.select("id","name","pic").like("location",location);
        return Result.ok(this.page(singerPage,singerQueryWrapper).getRecords());
    }

    @Transactional(readOnly = true)
    @Override
    public Result getSingerById(Integer id) {
        Singer singer = this.getById(id);
        if (singer == null){
            return Result.failed("歌手信息获取失败！");
        }
        return Result.ok(singer);
    }

    @Transactional(readOnly = true)
    @Override
    public Result getRecommendSingerInfo() {
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.le("id",6);
        List<Singer> singerList = this.list(singerQueryWrapper);
        if(singerList.isEmpty()){
            return Result.failed("数据获取失败");
        }
        return Result.ok(singerList);
    }

    @Transactional(readOnly = true)
    @Override
    public Result getSingerPageInfo(Integer pageNum, Integer num) {
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.select("id","name","pic");
        Page<Singer> singerPage = new Page<>(pageNum,num);
        Page<Singer> page = this.page(singerPage,singerQueryWrapper);
        if(page.getRecords().isEmpty()){
            return Result.failed("数据获取失败！");
        }
        return Result.ok(page);
    }
}
