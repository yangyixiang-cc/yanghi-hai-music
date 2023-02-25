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
 * 管理员审核日志
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("admin_check_log")
public class AdminCheckLog implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    private Integer adminId = null; //管理员id

    private Integer userId = null; //管理员审核的用户id

    private Integer checkerId = null; //管理员审核的审核员id

    private String content; //审核内容

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime; //审核时间

    private int type; // 审核类型 1 用户 2 审核员
}
