package com.yanghi.haimusic.controller;

import com.yanghi.haimusic.bean.SongSheet;
import com.yanghi.haimusic.service.SongSheetAndSongService;
import com.yanghi.haimusic.service.SongSheetService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 歌单控制器
 * @author 泗安
 */
@RestController
@RequestMapping("/api/song_sheet")
@Validated
public class SongSheetController {

    @Resource(name = "songSheetServiceImpl")
    private SongSheetService songSheetService;

    @Resource(name = "songSheetAndSongServiceImpl")
    private SongSheetAndSongService songSheetAndSongService;

    /**
     * 返回指定id的歌单信息
     * @param id 歌单ID
     * @return Result
     */
    @GetMapping("/{id}")
    public Result<SongSheet> returnSongSheetInfoById(@Min(value = 1, message = "id 必须大于等于 1") @PathVariable("id") Integer id){
       return songSheetService.getSongSheetById(id);
    }

    /**
     * 返回全部的歌单信息
     * @return Result
     */
    @GetMapping("/")
    public Result<List<SongSheet>> returnAllSongSheet(){
       return songSheetService.getAllSongSheet();
    }

    /**
     * 返回歌单的总数量
     * @return Result
     */
    @GetMapping("/total")
    public Result<Long> returnAllSongSheetTotal(){
       return songSheetService.getAllSongSheetTotal();
    }

    /**
     * 添加一条歌单信息
     * @param songSheet
     * @return Result
     */
    @PostMapping("/add")
    public Result<Object> addOneSongSheet(@Valid SongSheet songSheet){
        return songSheetService.addSongSheet(songSheet);
    }


    /**
     * 首页歌单推荐
     * @return Result
     */
    @GetMapping("/recommend")
    public Result returnRecommendSongSheetInfo(){
        return songSheetService.returnRecommendSongSheetInfo();
    }

    /**
     * 返回指定页码的分页数据
     * @param pageNum 页码
     * @param num 条数
     * @param style 风格
     * @return
     */
    @GetMapping("/page")
    public Result returnSongSheetByPage(@RequestParam("pageNum")
                                        @Min(value = 1, message = "页码必须大于等于 1") Integer pageNum,
                                        @RequestParam(value = "num", defaultValue = "6")
                                        @Min(value = 1, message = "每页条数必须大于等于 1")
                                        @Max(value = 100, message = "每页条数必须小于等于 100") Integer num,
                                        @RequestParam(value = "style", required = false) String style){
        return songSheetService.returnSongSheetPageInfo(pageNum,num,style);
    }

    /**
     * 返回指定id的歌单包含的歌曲集合的分页数据
     * @param id 歌单ID
     * @param pageNum 页码
     * @param num 条数
     * @return Result
     */
    @GetMapping("/songs")
    public Result returnSongSheetSongs(
            @RequestParam("id")
            @Min(value = 1, message = "id 必须大于等于 1") Integer id,
            @RequestParam(value = "pageNum",defaultValue = "1")
            @Min(value = 1, message = "页码必须大于等于 1") Integer pageNum,
            @RequestParam(value = "num",defaultValue = "6")
            @Min(value = 1, message = "每页条数必须大于等于 1")
            @Max(value = 100, message = "每页条数必须小于等于 100") Integer num
    ){

        return songSheetAndSongService.selectSongPageBySongSheetId(id, pageNum, num);
    }


    /**
     * 获取指定id的歌单对应的评论分页
     * @param id 歌单ID
     * @param pageNum 页码
     * @param num 条数
     * @return Result
     */
    @GetMapping("/comments")
    public Result returnCommentsPageBySongSheetId(
            @RequestParam("id")
            @Min(value = 1, message = "id 必须大于等于 1") Integer id,
            @RequestParam(value = "pageNum",defaultValue = "1")
            @Min(value = 1, message = "页码必须大于等于 1") Integer pageNum,
            @RequestParam(value = "num",defaultValue = "5")
            @Min(value = 1, message = "每页条数必须大于等于 1")
            @Max(value = 100, message = "每页条数必须小于等于 100") Integer num
            ){
        return songSheetService.returnCommentsPageBySongSheetId(id,pageNum,num);
    }

    /**
     * 获取指定id的歌单的评论数量
     * @param id 歌单ID
     * @return Result
     */
    @GetMapping("/comments/{id}")
    public Result returnCommentsCountBySongSheetId(
            @Min(value = 1, message = "id 必须大于等于 1") @PathVariable("id") Integer id
    ){
       return songSheetService.getCommentsCountBySongSheetId(id);
    }


    /**
     * 根据歌单类型style返回推荐数据
     * @param num 条数
     * @param style 歌单类型
     * @return Result
     */
    @GetMapping("/recommend/style")
    public Result returnRecommendSongSheetByStyle(
            @RequestParam(value = "num",defaultValue = "2")
            @Min(value = 1, message = "条数必须大于等于 1") Integer num,
            @RequestParam("style") String style
    ){
        return songSheetService.returnSongSheetPageInfo(1, num, style);
    }
}
