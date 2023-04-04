package com.yanghi.haimusic.validators.constant;

/**
 * 评论类型枚举
 * @author 泗安
 */

public enum CommentTypeEnum {
    // 歌曲

    SONG_TYPE(1),

    // 歌单

    SONG_SHEET_TYPE(2),

    // MV

    MV_TYPE(3);

    private final int value;

    CommentTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
