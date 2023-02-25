package com.yanghi.haimusic.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanghi.haimusic.bean.Singer;
import com.yanghi.haimusic.service.SingerService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/singer")
public class SingerController {

    @Autowired
    private SingerService  singerService;


    //通过歌手id返回歌手的详细信息
    @GetMapping("/{id}")
    public Result returnSingerInfo(@PathVariable("id") Integer id){
        Singer singer = singerService.getById(id);
        if (singer == null){
            return Result.failed("歌手信息获取失败！");
        }
        return Result.ok(singer);
    }

    //首页推荐返回数据 只返回6条数据
    @GetMapping("/recommend")
    public Result returnRecommendSingerInfo(){
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.le("id",6);
        List<Singer> singerList = singerService.list(singerQueryWrapper);
        if(singerList.isEmpty()){
            return Result.failed("数据获取失败");
        }
        return Result.ok(singerList);
    }

    //获取全部歌手分页数据 默认第一页，默认每次加载20条数据
    @GetMapping("/page")
    public Result returnSingerPageInfo(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "num",defaultValue = "20") Integer num
    ){
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.select("id","name","pic");
        Page<Singer> singerPage = new Page<>(pageNum,num);
        Page<Singer> page = singerService.page(singerPage,singerQueryWrapper);
        if(page.getRecords().isEmpty()){
            return Result.failed("数据获取失败！");
        }
        return Result.ok(page);
    }

    //根据关键词搜索相关的歌手，返回歌手列表
    @GetMapping("/search")
    public Result returnSearchResult(
            @RequestParam("keyword") String keyword
    ){
        return Result.ok(singerService.returnSearchSingersByKey(keyword));
    }

    //获取歌手推荐列表
    @GetMapping("/silmar_singer")
    public Result returnSlimarSingerResult(
            @RequestParam("location") String location,
            @RequestParam(value = "num",defaultValue = "6") Integer num
    ){
        return Result.ok(singerService.returnSilmarSingerByIntroduction(location,num));
    }
}
