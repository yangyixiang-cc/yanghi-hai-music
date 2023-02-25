package com.yanghi.haimusic.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户详情类
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("user_details")
public class UserDetails implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private int id; //用户详情id

    private int sex; //性别

    private int age; //年龄

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birth; //出生日期

    private String phone; //电话

    private String email; //邮箱

    private String introduce; //用户简介

    private String hobby; //用户爱好

    private String location; //用户所在地

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; //用户创建时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime; //用户上次登录时间

    private int supportVolume; //用户粉丝量

    private int followVolume; //用户关注量

    private Integer userId = null; //对应的用户id
}
