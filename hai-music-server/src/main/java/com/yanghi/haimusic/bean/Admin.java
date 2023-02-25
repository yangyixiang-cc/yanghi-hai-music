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

    @TableId(value = "id",type = IdType.AUTO)
    private int id; //主键

    private String username; //用户名

    private String password; //密码

    @TableLogic(value = "1",delval = "0")
    private int status = 1; //状态
}
