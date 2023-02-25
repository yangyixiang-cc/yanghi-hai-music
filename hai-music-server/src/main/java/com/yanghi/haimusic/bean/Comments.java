package com.yanghi.haimusic.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.relational.core.sql.In;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论类
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("comments")
public class Comments implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private int id; //主键

    private Integer userId = null; //那个用户发布的评论

    private Integer singerId = null; //那个歌手发布的评论

    private String content; //评论内容

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; //评论发布时间

    @TableLogic(value = "1",delval = "0")
    private int status = 1; //评论状态，用于审核

    private int supportVolume; //评论点赞量

    private int replyVolume; //评论回复量

    private Integer subCommentId; //回应的那条评论

    private Integer songId; //评论的歌曲id

    private Integer songSheetId; //评论的歌单id

    private Integer videoId; //评论的MVid;

    private int type; //评论的类型 1 歌曲 2 歌单 3 MV
}
