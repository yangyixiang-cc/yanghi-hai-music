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
 * 歌手类
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("singer")
public class Singer implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    private String name; //歌手名

    private int sex; //歌手性别

    private String pic; //歌手头像

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birth; //歌手出生时间

    private String location; //歌手出生地

    private String introduction; //歌手简介

    private int supportVolume; //歌手粉丝数

    private int shareVolume; //歌手分享量

    private int songVolume; //单曲量

    private int mvVolume; //MV量

    @TableLogic(value = "1",delval = "0")
    private int status = 1; //歌手状态

}
