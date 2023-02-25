package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanghi.haimusic.bean.*;
import com.yanghi.haimusic.mapper.CommentsMapper;
import com.yanghi.haimusic.mapper.SingerMapper;
import com.yanghi.haimusic.mapper.SongSheetMapper;
import com.yanghi.haimusic.mapper.UserMapper;
import com.yanghi.haimusic.service.SongSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class SongSheetServiceImpl extends ServiceImpl<SongSheetMapper, SongSheet> implements SongSheetService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SingerMapper singerMapper;

    @Override
    public List<Map<String,Object>> returnCommentsPageBySongSheetId(Integer id,Integer pageNum, Integer num) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Page<Comments> commentsPage = new Page<>(pageNum,num);
        QueryWrapper<Comments> commentsQueryWrapper = new QueryWrapper<>();
        commentsQueryWrapper.eq("song_sheet_id",id).isNotNull("song_sheet_id");
        Page<Comments> commentsPage1 = commentsMapper.selectPage(commentsPage, commentsQueryWrapper);
        for(Comments comments: commentsPage1.getRecords()){
            Map<String,Object> objectMap = new HashMap<>();
            objectMap.put("comment",comments);
            if(comments.getUserId() != null){
                objectMap.put("user",userMapper.selectOne(
                        new QueryWrapper<User>()
                                .select("id", "nick_name", "pic")
                                .eq("id",comments.getUserId())));
            }else{
                objectMap.put("singer",singerMapper.selectOne(
                        new QueryWrapper<Singer>()
                                .select("id", "name", "pic")
                                .eq("id",comments.getSingerId())));
            }
            mapList.add(objectMap);
        }
        Map<String,Object> pageMap = new HashMap<>();
        pageMap.put("total",commentsPage1.getTotal());
        pageMap.put("current",commentsPage1.getCurrent());
        pageMap.put("pages",commentsPage1.getPages());
        pageMap.put("size",commentsPage1.getSize());
        mapList.add(pageMap);
        return mapList;
    }

    @Override
    public List<SongSheet> returnRecommendSongSheetInfo() {
        //推荐歌单6条数据
        //推荐序列
        Set<Integer> recommendSet = new HashSet<>();
        int total = (int)this.count();
        Random random = new Random();
        int i = 0;
        while(i < 6){
            int anInt = random.nextInt(total);
            if(!recommendSet.contains(anInt)){
                recommendSet.add(anInt);
                i++;
            }
        }
        QueryWrapper<SongSheet> songSheetQueryWrapper = new QueryWrapper<>();
        songSheetQueryWrapper.select("id","title","pic","playback_volume").in("id",recommendSet);
        List<SongSheet> songSheets = this.list(songSheetQueryWrapper);
        return songSheets;
    }

    @Override
    public Page<SongSheet> returnSongSheetPageInfo(Integer pageNum, Integer num,String style) {
        QueryWrapper<SongSheet> songSheetQueryWrapper = new QueryWrapper<>();
        if(!style.isEmpty()){
            songSheetQueryWrapper.like("style",style);
        }
        Page<SongSheet> page = new Page<>(pageNum,num);
        return this.page(page,songSheetQueryWrapper);
    }

    @Override
    public List<SongSheet> returnSearchSongSheetsByKey(String keyword) {
        QueryWrapper<SongSheet> songSheetQueryWrapper = new QueryWrapper<>();
        songSheetQueryWrapper.select("id","title","pic").like("title",keyword);
        return this.list(songSheetQueryWrapper);
    }
}
