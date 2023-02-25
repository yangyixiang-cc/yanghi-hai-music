package com.yanghi.haimusic.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanghi.haimusic.bean.Song;
import com.yanghi.haimusic.service.SongService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/song")
public class SongController {

    @Autowired
    private SongService songService;

    //首页推荐返回数据 只返回8条数据
    @GetMapping("/recommend")
    public Result returnRecommendSongInfo(){
        QueryWrapper<Song> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.le("id",8);
        List<Song> songList = songService.list(singerQueryWrapper);
        if(songList.isEmpty()){
            return Result.failed("数据获取失败");
        }
        return Result.ok(songList);
    }

    //根据id返回歌曲的详情信息，包括对应的歌手信息，以map集合形式返回，song 歌曲信息 singer 歌手信息
    @GetMapping("/songInfo")
    public Result returnSongAndSingerInfoById(
            @RequestParam("id") Integer id
    ){
        Song songServiceById = songService.getById(id);
        if(songServiceById == null){
            return Result.failed("数据获取失败！");
        }
        return Result.ok(songServiceById);
    }

    //获取指定id的歌曲对应的评论分页 pageNum 默认为1 num 默认为5
    @GetMapping("/comments")
    public Result returnCommentsPageBySongSheetId(
            @RequestParam("id") Integer id,
            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
            @RequestParam(value = "num",defaultValue = "5") Integer num
    ){
        List<Map<String, Object>> commentsPageBySongId = songService.returnCommentsPageBySongId(id, pageNum, num);
        if(commentsPageBySongId.isEmpty()){
            return Result.failed("数据为空");
        }
        return Result.ok(commentsPageBySongId);
    }

    //返回指定id的歌单包含的歌曲集合的分页数据
    @GetMapping("/songs")
    public Result returnSongSheetSongs(
            @RequestParam("id") Integer id,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "num",defaultValue = "6") Integer num
    ){

        Page<Song> songPage = songService.selectSongPageBySingerId(id,pageNum,num);
        if(songPage.getRecords().isEmpty()){
            return Result.failed("未查找到对应歌手的歌曲信息");
        }
        return Result.ok(songPage);
    }

    //返回由区域region或style的歌曲排行的前20条数据
    @GetMapping("/song_rankings")
    public Result returnSongRankings(
            @RequestParam(value = "num",defaultValue = "20") Integer num,
            @RequestParam(value = "style",defaultValue = "") String style,
            @RequestParam(value = "region",defaultValue = "") String region
            ){
        Page<Song> page = null;
        if(style.isEmpty() && region.isEmpty()){
            page = songService.returnSongPagesByAllPlayVolume(num);
        }else if(!style.isEmpty() && region.isEmpty()){
            page = songService.returnSongPagesByStyle(num,style);
        }else if (style.isEmpty() && !region.isEmpty()){
            page = songService.returnSongPagesByRegion(num,region);
        }
        return Result.ok(page);
    }

}
