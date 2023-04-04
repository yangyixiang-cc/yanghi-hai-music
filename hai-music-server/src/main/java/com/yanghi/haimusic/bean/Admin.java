package com.yanghi.haimusic.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 管理员类
 * @author 泗安
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("admin")
public class Admin implements Serializable {

    //主键

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    //用户名

    private String username;

    //密码

    private String password;


    //状态

    @TableLogic(value = "1",delval = "0")
    private int status = 1;

}
