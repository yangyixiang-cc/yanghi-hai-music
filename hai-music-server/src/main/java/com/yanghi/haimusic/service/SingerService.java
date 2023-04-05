package com.yanghi.haimusic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanghi.haimusic.bean.Singer;
import com.yanghi.haimusic.utils.Result;

import java.util.List;

public interface SingerService extends IService<Singer> {

    Result<List<Singer>> getSearchSingersByKeyword(String keyword);


    Result<List<Singer>> getSimilarSingerByIntroduction(String introduction, Integer num);


    Result getSingerById(Integer id);

    Result getRecommendSingerInfo();

    Result getSingerPageInfo(Integer pageNum, Integer num);

}
