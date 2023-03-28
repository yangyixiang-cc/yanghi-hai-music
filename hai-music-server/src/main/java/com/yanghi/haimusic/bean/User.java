package com.yanghi.haimusic.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 用户简明类
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("user")
public class User implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private int id; //主键

    private String username; //用户名

    private String password; //密码

    private String nickName; //用户昵称

    private String pic; //用户头像地址

    @TableLogic(value = "1",delval = "0")
    private int status = 1; //用户状态

}
