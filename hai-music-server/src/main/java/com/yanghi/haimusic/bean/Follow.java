package com.yanghi.haimusic.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("follow")
public class Follow implements Serializable {
    @TableId(value = "id",type= IdType.AUTO)
    private int id; //主键

    private String userId; //用户ID

    private String singerId; //歌手ID

    private String bSingerId; //关注的歌手ID

    private String bUserId; //关注的用户ID

    private int flog;  //是否关注 1关注 0 不关注
}
