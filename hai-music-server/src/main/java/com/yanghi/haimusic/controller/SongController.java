package com.yanghi.haimusic.controller;

import com.yanghi.haimusic.service.SongService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 歌曲控制器
 * @author 泗安
 */
@RestController
@RequestMapping("/api/song")
@Validated
public class SongController {

    @Resource(name = "songServiceImpl")
    private SongService songService;

    /**
     * 首页推荐返回数据 只返回8条数据
     * @return Result
     */
    @GetMapping("/recommend")
    public Result returnRecommendSongInfo(){
        return songService.getRecommendSongInfo();
    }

    /**
     * 根据id返回歌曲的详情信息
     * @param id 歌曲ID
     * @return Result
     */
    @GetMapping("/songInfo")
    public Result returnSongInfoById(
            @Min(value = 1, message = "id 必须大于等于 1") @RequestParam("id") Integer id
    ){
       return songService.getSongById(id);
    }

    /**
     * 获取指定id的歌曲对应的评论分页
     * @param id 歌曲ID
     * @param pageNum 页码
     * @param num 条数
     * @return Result
     */
    @GetMapping("/comments")
    public Result returnCommentsPageBySongId(
            @RequestParam("id")
            @Min(value = 1, message = "id 必须大于等于 1") Integer id,
            @RequestParam(value = "pageNum",defaultValue = "1")
            @Min(value = 1, message = "页码必须大于等于 1") Integer pageNum,
            @RequestParam(value = "num",defaultValue = "5")
            @Min(value = 1, message = "每页条数必须大于等于 1")
            @Max(value = 100, message = "每页条数必须小于等于 100") Integer num
    ){
        return songService.returnCommentsPageBySongId(id, pageNum, num);
    }

    /**
     * 根据歌手ID返回对应的歌曲分页数据
     * @param id 歌手ID
     * @param pageNum 页码
     * @param num 条数
     * @return Result
     */
    @GetMapping("/songs")
    public Result returnSongsPageBySingerId(
            @RequestParam("id")
            @Min(value = 1, message = "id 必须大于等于 1") Integer id,
            @RequestParam(value = "pageNum",defaultValue = "1")
            @Min(value = 1, message = "页码必须大于等于 1") Integer pageNum,
            @RequestParam(value = "num",defaultValue = "6")
            @Min(value = 1, message = "每页条数必须大于等于 1")
            @Max(value = 100, message = "每页条数必须小于等于 100") Integer num
    ){
        return songService.selectSongPageBySingerId(id,pageNum,num);
    }

    /**
     * 返回由区域 region 或 style 的歌曲排行的前20条数据
     * @param num 条数
     * @param style 歌曲风格
     * @param region 歌曲所属区域
     * @return Result
     */
    @GetMapping("/song_rankings")
    public Result returnSongRankings(
            @RequestParam(value = "num",defaultValue = "20") Integer num,
            @RequestParam(value = "style",defaultValue = "") String style,
            @RequestParam(value = "region",defaultValue = "") String region
            ){
        return songService.getSongRankings(num, style, region);
    }

}
