package com.yanghi.haimusic.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanghi.haimusic.bean.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SongServiceTest {

    @Autowired
    private SongService songService;

    @Test
    void selectSongPageBySingerId() {
        Page<Song> songPage = songService.selectSongPageBySingerId(1, 1, 6);
        List<Song> records = songPage.getRecords();
        System.out.println(records);
    }
}