package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanghi.haimusic.bean.Comments;
import com.yanghi.haimusic.bean.Singer;
import com.yanghi.haimusic.bean.Song;
import com.yanghi.haimusic.bean.User;
import com.yanghi.haimusic.mapper.CommentsMapper;
import com.yanghi.haimusic.mapper.SingerMapper;
import com.yanghi.haimusic.mapper.SongMapper;
import com.yanghi.haimusic.mapper.UserMapper;
import com.yanghi.haimusic.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SingerMapper singerMapper;

    @Autowired
    private SongMapper songMapper;

    @Override
    public List<Map<String, Object>> returnCommentsPageBySongId(Integer id, Integer pageNum, Integer num) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Page<Comments> commentsPage = new Page<>(pageNum,num);
        QueryWrapper<Comments> commentsQueryWrapper = new QueryWrapper<>();
        commentsQueryWrapper.eq("song_id",id).isNotNull("song_id").eq("status","1");
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
    public Page<Song> selectSongPageBySingerId(Integer id, Integer pageNum, Integer num) {
        Page<Song> page = new Page<>(pageNum,num);
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.select("id","name","introduction","pic","singer_name","singer_id","url").eq("singer_id",id);
        Page<Song> songPage = songMapper.selectPage(page, songQueryWrapper);
        return songPage;
    }

    @Override
    public Page<Song> returnSongPagesByStyle(Integer num,String style) {
        Page<Song> page = new Page<>(1,num);
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.select("id","name","introduction","pic","singer_name","play_volume","singer_id","url").like("style",style).orderByDesc("play_volume");
        return this.page(page,songQueryWrapper);
    }

    @Override
    public Page<Song> returnSongPagesByRegion(Integer num, String region) {
        Page<Song> page = new Page<>(1,num);
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.select("id","name","introduction","pic","singer_name","play_volume","singer_id","url").like("region",region).orderByDesc("play_volume");
        return this.page(page,songQueryWrapper);
    }

    @Override
    public Page<Song> returnSongPagesByAllPlayVolume(Integer num) {
        Page<Song> page = new Page<>(1,num);
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.select("id","name","introduction","pic","singer_name","play_volume","singer_id","url").orderByDesc("play_volume");
        return this.page(page,songQueryWrapper);
    }

    @Override
    public List<Song> returnSearchSongsByKey(String keyword) {
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.select("id","name","pic").like("name",keyword);
        return this.list(songQueryWrapper);
    }
}
