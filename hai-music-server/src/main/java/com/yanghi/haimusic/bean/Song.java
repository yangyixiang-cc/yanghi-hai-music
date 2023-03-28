package com.yanghi.haimusic.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌曲类
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("song")
public class Song implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private int id; //主键

    private String name; //歌曲名称

    private String introduction; //歌曲简介

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; //歌曲发布时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime; //歌曲更新时间

    private String singerName; //歌手名称

    private String pic; //歌曲封面地址

    private String lyric; //歌词

    private String url; //歌曲音频地址

    private String style; //歌曲类型

    private String region; //区域

    private int playVolume; //歌曲播放量

    private int supportVolume; //歌曲点赞量

    private int shareVolume; //歌曲分享量

    @TableLogic(value = "1",delval = "0")
    private int status = 1; //歌曲状态

    private Integer singerId; //歌曲对应的歌手id
}
