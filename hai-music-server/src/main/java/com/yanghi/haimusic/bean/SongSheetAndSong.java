package com.yanghi.haimusic.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌单和歌曲的联系类
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@TableName("song_sheet_and_song")
public class SongSheetAndSong implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    private Integer songSheetId = null; //对应的歌单id

    private Integer songId = null; //对应的歌曲id

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date joinTime; //歌曲加入歌单的时间

    @TableLogic(value = "1",delval = "0")
    private int status; //关联状态，用于删除歌单中的歌曲
}
