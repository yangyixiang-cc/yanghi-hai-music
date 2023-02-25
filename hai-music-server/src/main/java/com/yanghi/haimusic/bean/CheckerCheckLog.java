package com.yanghi.haimusic.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 审核员审核日志
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("checker_check_log")
public class CheckerCheckLog implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    private Integer checkerId = null; //审核员id

    private Integer songId = null; //审核的歌曲id

    private Integer singerId = null; //审核的歌手id

    private Integer videoId = null; //审核的MVid

    private Integer songSheetId = null; //审核的歌单id

    private Integer commentsId = null; //审核的评论Id

    private String content;  //审核内容

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;  //审核时间

    private int type; // 核查类型 1 歌曲 2 歌单 3 歌手 4 MV 5 评论
}
