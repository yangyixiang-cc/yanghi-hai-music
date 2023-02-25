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
 * MV类
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("video")
public class Video implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private int id; //主键

    private String title; //MV标题

    private String introduction; //MV简介

    private String pic; //MV封面地址

    private String url; //MV视频地址

    private String style; //MV类型

    private String region; //区域

    private String edition; //版本


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; //发布时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime; //更新时间

    private int playVolume; //播放量

    private int shareVolume; //分享量

    private int collectVolume; //收藏量

    private int supportVolume; //点赞量


    @TableLogic(value = "1",delval = "0")
    private int status = 1; //MV状态

    private Integer singerId = null; //对应发布MV的歌手id

}
