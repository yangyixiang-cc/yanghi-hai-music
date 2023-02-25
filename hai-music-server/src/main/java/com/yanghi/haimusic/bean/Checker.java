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
 * 审核员类
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("checker")
public class Checker implements Serializable {

    @TableId(value = "id",type= IdType.AUTO)
    private int id; //主键

    private String name; //名称

    private String password; //密码

    private int grade = 2; //等级

    @TableLogic(value = "1",delval = "0")
    private int status = 1; //状态
}
