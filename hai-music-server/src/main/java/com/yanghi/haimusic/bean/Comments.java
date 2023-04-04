package com.yanghi.haimusic.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yanghi.haimusic.validators.AllowedValuesConstraint;
import com.yanghi.haimusic.validators.OnlyOneField;
import com.yanghi.haimusic.validators.constant.CommentTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
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
@OnlyOneField(fields = {"songId", "songSheetId", "videoId"},message = "songId songSheetId videoId 只能提供一个字段")
public class Comments implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //那个用户发布的评论

    @NotNull(message = "用户ID不能为空")
    private Integer userId = null;

    //那个歌手发布的评论

    private Integer singerId = null;

    //评论内容

    @Size(min = 1, max = 200, message = "评论字数必须在1-200个字符之间")
    private String content;

    //评论发布时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //评论状态，用于审核

    @TableLogic(value = "1",delval = "0")
    private Integer status = 1;

    //评论点赞量

    private Integer supportVolume;

    //评论回复量

    private Integer replyVolume;

    //回应的那条评论

    private Integer subCommentId;

    //评论的歌曲id

    private Integer songId;

    //评论的歌单id

    private Integer songSheetId;

    //评论的MVid;

    private Integer videoId;

    //评论的类型 1 歌曲 2 歌单 3 MV

    @AllowedValuesConstraint
    private int type;
}
