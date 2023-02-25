package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanghi.haimusic.bean.*;
import com.yanghi.haimusic.mapper.CommentsMapper;
import com.yanghi.haimusic.mapper.SingerMapper;
import com.yanghi.haimusic.mapper.UserMapper;
import com.yanghi.haimusic.mapper.VideoMapper;
import com.yanghi.haimusic.service.FollowService;
import com.yanghi.haimusic.service.SingerService;
import com.yanghi.haimusic.service.VideoService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private SingerService singerService;

    @Autowired
    private FollowService followService;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SingerMapper singerMapper;

    @Override
    public Page<Video> returnVideoPage(Integer pageNum, Integer num,String region,String edition) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        if(!region.isEmpty() && edition.isEmpty()){
            videoQueryWrapper.like("region",region);

        }else if(region.isEmpty() && !edition.isEmpty()){
            videoQueryWrapper.like("edition",edition);
        }
        Page<Video> videoPage = new Page<>(pageNum, num);
        return this.page(videoPage, videoQueryWrapper);
    }

    @Override
    public List<Video> returnVideoRankingsByRegion(Integer num, String region) {
        Page<Video> videoPage = new Page<>(1,num);
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        if(region.isEmpty()){
            videoQueryWrapper.select("id","title","introduction","pic").orderByDesc("play_volume");
        }else{
            videoQueryWrapper.select("id","title","introduction","pic").like("region",region).orderByDesc("play_volume");
        }
        return this.page(videoPage,videoQueryWrapper).getRecords();
    }

    @Override
    public List<Video> returnRecommendVideoInfo() {
        Page<Video> videoPage = new Page<>(1,6);
        return this.page(videoPage).getRecords();
    }

    @Override
    public Map<String, Object> returnVideoInfoAndSingerInfoById(String id) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        Video video = this.getById(id);
        stringObjectHashMap.put("video", video);
        stringObjectHashMap.put("singer",singerService.getById(video.getSingerId()));
        boolean flog = true;
        Follow follow = followService.getById(video.getSingerId());
        if(follow == null || follow.getFlog() == 0){
            flog = false;
        }
        stringObjectHashMap.put("flog",flog);
        return stringObjectHashMap;
    }

    @Override
    public List<Map<String,Object>> returnCommentPageByVideoId(Integer id, Integer pageNum, Integer num) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Page<Comments> commentsPage = new Page<>(pageNum,num);
        QueryWrapper<Comments> commentsQueryWrapper = new QueryWrapper<>();
        commentsQueryWrapper.eq("video_id",id).isNotNull("video_id");
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
    public List<Video> returnRecommendVideoByStyle(Integer num, String style) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        if(!style.isEmpty()){
            videoQueryWrapper.like("style",style);
        }
        Page<Video> page = new Page<>(1,num);
        return this.page(page,videoQueryWrapper).getRecords();
    }

    @Override
    public List<Video> returnSearchVideosByKey(String keyword) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.select("id","title","pic").like("title",keyword);
        return this.list(videoQueryWrapper);
    }
}
