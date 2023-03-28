package com.yanghi.haimusic.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SongSheetServiceTest {

    @Autowired
    SongSheetService songSheetService;

    //测试根据歌单id返回评论和及相对应的用户
    @Test
    public void testCommentsBySongSheetId(){
        List<Map<String, Object>> mapList = songSheetService.returnCommentsPageBySongSheetId(5,1,5);
        for(Map<String,Object> map: mapList){
            System.out.println(map);
        }
    }

    @Test
    void returnRecommendSongSheetInfo() {
        songSheetService.returnRecommendSongSheetInfo();
    }
}