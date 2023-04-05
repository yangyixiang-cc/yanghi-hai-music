package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanghi.haimusic.bean.Singer;
import com.yanghi.haimusic.bean.Song;
import com.yanghi.haimusic.bean.SongSheet;
import com.yanghi.haimusic.bean.Video;
import com.yanghi.haimusic.mapper.SingerMapper;
import com.yanghi.haimusic.mapper.SongMapper;
import com.yanghi.haimusic.mapper.SongSheetMapper;
import com.yanghi.haimusic.mapper.VideoMapper;
import com.yanghi.haimusic.service.CommonService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 泗安
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private SongMapper songMapper;


    @Resource
    private SongSheetMapper songSheetMapper;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private SingerMapper singerMapper;

    @Transactional(readOnly = true)
    @Override
    public Result<Map<String, Object>> getSearchSongAndSongSheetAndSingerAndVideoResultByKeyword(String keyword) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        QueryWrapper<SongSheet> songSheetQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.select("id","name","pic").like("name",keyword);
        songQueryWrapper.select("id","name","pic").like("name",keyword);
        songSheetQueryWrapper.select("id","title","pic").like("title",keyword);
        videoQueryWrapper.select("id","title","pic").like("title",keyword);
        List<Singer> singers = singerMapper.selectList(singerQueryWrapper);
        List<Song> songs = songMapper.selectList(songQueryWrapper);
        List<Video> videos = videoMapper.selectList(videoQueryWrapper);
        List<SongSheet> songSheets = songSheetMapper.selectList(songSheetQueryWrapper);
        map.put("songs",songs);
        map.put("songSheets",songSheets);
        map.put("singers",singers);
        map.put("videos",videos);
        return Result.ok(map);
    }
}
