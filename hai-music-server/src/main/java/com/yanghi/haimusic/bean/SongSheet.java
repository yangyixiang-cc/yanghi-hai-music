package com.yanghi.haimusic.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.yanghi.haimusic.validators.OnlyOneField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
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
@OnlyOneField(fields = {"userId", "singerId"}, message = "userId singerId 必须提供一个字段，且只有一个")
public class SongSheet implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    //歌单标题

    @NotBlank(message = "歌单标题不能为空")
    private String title;

    //歌单封面

    @NotBlank(message = "歌单封面不能为空")
    private String pic;

    //歌单简介

    @NotBlank(message = "歌单简介不能为空")
    private String introduction;

    //歌单类型

    @NotBlank(message = "歌单类型不能为空")
    private String style;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    //歌单浏览量

    private int playbackVolume;

    //歌单收藏量

    private int collectVolume;

    //歌单点赞量

    private int supportVolume;

    //歌单分享量

    private int shareVolume;

    @TableLogic(value = "1",delval = "0")
    private int status = 1;

    //发布歌单的用户id

    private Integer userId;

    //发布歌单的歌手id

    private Integer singerId;
}
