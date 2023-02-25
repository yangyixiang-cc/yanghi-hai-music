package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanghi.haimusic.bean.Song;
import com.yanghi.haimusic.mapper.SongSheetAndSongMapper;
import com.yanghi.haimusic.service.SongSheetAndSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SongSheetAndSongServiceImpl implements SongSheetAndSongService {

    @Autowired
    SongSheetAndSongMapper songSheetAndSongMapper;

    @Override
    public Page<Song> selectSongPageBySongSheetId(Integer id, Integer pageNum, Integer num) {
        Page<Song> songPage = new Page<>(pageNum,num);
        return songSheetAndSongMapper.selectSongPageBySongSheetId(songPage,id);
    }
}
