package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanghi.haimusic.bean.Song;
import com.yanghi.haimusic.mapper.SongSheetAndSongMapper;
import com.yanghi.haimusic.service.SongSheetAndSongService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SongSheetAndSongServiceImpl implements SongSheetAndSongService {

    @Autowired
    private SongSheetAndSongMapper songSheetAndSongMapper;

    @Transactional(readOnly = true)
    @Override
    public Result selectSongPageBySongSheetId(Integer id, Integer pageNum, Integer num) {
        Page<Song> songPage = new Page<>(pageNum,num);
        Page<Song> page = songSheetAndSongMapper.selectSongPageBySongSheetId(songPage, id);
        if(page.getRecords().isEmpty()){
            return Result.failed("未查找到对应歌单包含的歌曲的信息");
        }
        return Result.ok(page);
    }
}
