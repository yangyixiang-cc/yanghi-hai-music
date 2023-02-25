package com.yanghi.haimusic.controller;

import com.yanghi.haimusic.bean.Singer;
import com.yanghi.haimusic.bean.Song;
import com.yanghi.haimusic.bean.SongSheet;
import com.yanghi.haimusic.bean.Video;
import com.yanghi.haimusic.service.SingerService;
import com.yanghi.haimusic.service.SongService;
import com.yanghi.haimusic.service.SongSheetService;
import com.yanghi.haimusic.service.VideoService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    SongService songService;

    @Autowired
    SongSheetService songSheetService;

    @Autowired
    VideoService videoService;

    @Autowired
    SingerService singerService;

    @GetMapping("/search")
    public Result returnSearchResult(
            @RequestParam("keyword") String keyword
    ){
        Map<String,Object> map = new HashMap<>();
        List<Song> songs = songService.returnSearchSongsByKey(keyword);
        List<SongSheet> songSheets = songSheetService.returnSearchSongSheetsByKey(keyword);
        List<Singer> singers = singerService.returnSearchSingersByKey(keyword);
        List<Video> videos = videoService.returnSearchVideosByKey(keyword);
        map.put("songs",songs);
        map.put("songSheets",songSheets);
        map.put("singers",singers);
        map.put("videos",videos);
        return Result.ok(map);
    }
}
