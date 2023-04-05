package com.yanghi.haimusic.service;


import com.yanghi.haimusic.utils.Result;
import java.util.Map;

/**
 * 复合服务
 * @author 泗安
 */
public interface CommonService {

    /**
     * 聚合搜索，根据关键词，搜索歌曲、歌单、歌手、MV
     * @param keyword
     * @return
     */
    Result<Map<String, Object>> getSearchSongAndSongSheetAndSingerAndVideoResultByKeyword(String keyword);

}
