package com.yanghi.haimusic.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanghi.haimusic.bean.Song;
import com.yanghi.haimusic.bean.SongSheet;
import com.yanghi.haimusic.service.SongSheetAndSongService;
import com.yanghi.haimusic.service.SongSheetService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/song_sheet")
public class SongSheetController {

    @Autowired
    SongSheetService songSheetService;

    @Autowired
    SongSheetAndSongService songSheetAndSongService;

    //返回指定id的歌单信息
    @GetMapping("/{id}")
    public Result<SongSheet> returnSongSheetInfoById(@PathVariable("id") int id){
        SongSheet songSheet = songSheetService.getById(id);
        if(songSheet == null){
            return Result.failed("查询为空");
        }
        return Result.ok(songSheet);
    }

    //返回全部的歌单信息
    @GetMapping("/")
    public Result<List<SongSheet>> returnAllSongSheet(){
        Map<String,Object> map = new HashMap<>();
        map.put("status",1);
        List<SongSheet> songSheets = songSheetService.listByMap(map);
        Result<List<SongSheet>> listResult = Result.ok(songSheets);
        return listResult;
    }

    //返回歌单的总数量
    @GetMapping("/total")
    public Result<Long> returnAllSongSheetTotal(){
        long count = songSheetService.count();
        Result<Long> longResult = Result.ok(count);
        return longResult;
    }

    //添加一条歌单信息
    @PostMapping("/add")
    public Result<Object> addOneSongSheet(SongSheet songSheet){
        boolean b = songSheetService.save(songSheet);
        if(b){
            return Result.ok();
        }
        return Result.failed("添加失败！");
    }


    //首页歌单推荐
    @GetMapping("/recommend")
    public Result returnRecommendSongSheetInfo(){
        List<SongSheet> songSheets = songSheetService.returnRecommendSongSheetInfo();
        if(songSheets.isEmpty()){
            return Result.failed("数据获取失败！");
        }
        return Result.ok(songSheets);
    }


    //返回指定页码的分页数据
    @GetMapping("/page")
    public Result<Page<SongSheet>> returnSongSheetByPage(@RequestParam("pageNum") int pageNum, @RequestParam(value = "num",defaultValue = "6") int num,@RequestParam("style") String style){
        Result<Page<SongSheet>> pageResult = Result.ok(songSheetService.returnSongSheetPageInfo(pageNum,num,style));
        return pageResult;
    }

    //返回指定id的歌单包含的歌曲集合的分页数据
    @GetMapping("/songs")
    public Result returnSongSheetSongs(
            @RequestParam("id") Integer id,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "num",defaultValue = "6") Integer num
    ){

        Page<Song> songPage = songSheetAndSongService.selectSongPageBySongSheetId(id, pageNum, num);
        if(songPage.getRecords().isEmpty()){
            return Result.failed("未查找到对应歌单包含的歌曲的信息");
        }
        return Result.ok(songPage);
    }


    //获取指定id的歌单对应的评论分页 pageNum 默认为1 num 默认为5
    @GetMapping("/comments")
    public Result returnCommentsPageBySongSheetId(
            @RequestParam("id") Integer id,
            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
            @RequestParam(value = "num",defaultValue = "5") Integer num
            ){
        List<Map<String, Object>> commentsBySongSheetId = songSheetService.returnCommentsPageBySongSheetId(id,pageNum,num);
        if(commentsBySongSheetId.isEmpty()){
            return Result.failed("数据为空");
        }
        return Result.ok(commentsBySongSheetId);
    }

    //获取指定id的歌单的评论数量
    @GetMapping("/comments/{id}")
    public Result returnCommentsCountBySongSheetId(
            @PathVariable("id") Integer id
    ){
        QueryWrapper<SongSheet> songSheetQueryWrapper = new QueryWrapper<>();
        songSheetQueryWrapper.eq("song_sheet_id",id).isNotNull("song_sheet_id");
        long count = songSheetService.count(songSheetQueryWrapper);
        return Result.ok(count);
    }


    //根据歌单类型style返回推荐数据
    @GetMapping("/recommend/style")
    public Result returnRecommendSongSheetByStyle(
            @RequestParam(value = "num",defaultValue = "2") Integer num,
            @RequestParam("style") String style
    ){
        Page<SongSheet> page = songSheetService.returnSongSheetPageInfo(1, num, style);
        return Result.ok(page.getRecords());
    }
}
