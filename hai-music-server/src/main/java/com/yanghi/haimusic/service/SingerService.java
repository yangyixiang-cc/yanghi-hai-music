package com.yanghi.haimusic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanghi.haimusic.bean.Singer;

import java.util.List;

public interface SingerService extends IService<Singer> {

    List<Singer> returnSearchSingersByKey(String keyword);


    List<Singer> returnSilmarSingerByIntroduction(String introduction, Integer num);

}
