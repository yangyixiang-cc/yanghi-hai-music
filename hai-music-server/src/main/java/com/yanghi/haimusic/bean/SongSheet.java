package com.yanghi.haimusic.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌单类
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("song_sheet")
public class SongSheet implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private int id; //主键

    private String title; //歌单标题

    private String pic; //歌单封面

    private String introduction; //歌单简介

    private String style; //歌单类型

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; //歌单创建时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime; //歌单更新时间

    private int playbackVolume; //歌单浏览量

    private int collectVolume; //歌单收藏量

    private int supportVolume; //歌单点赞量

    private int shareVolume; //歌单分享量

    @TableLogic(value = "1",delval = "0")
    private int status = 1; //歌单状态

    private Integer userId = null; //发布歌单的用户id

    private Integer singerId = null; //发布歌单的歌手id
}
