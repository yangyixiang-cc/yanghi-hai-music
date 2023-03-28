import {
    // post,
    get,
    // put,
    // _delete
} from '@/utils/request';

export default class Song {


    /**
     * 根据ID获取歌手信息
     * @param {Number} id 
     * @returns 
     */
    static async getSongById(id) {
        return get('/song/songInfo', {
            id
        });
    }


    static async getCommentsBySongId(id, pageNum, num = 5) {
        return get("/song/comments", {
            id,
            pageNum,
            num
        });
    }

    /**
     * 根据歌手ID获取歌曲分页数据
     * @param {Number} id  歌手ID
     * @param {Number} pageNum 
     * @param {Number} num 
     */
    static async getSongPageBySingerId(id, pageNum, num = 6) {
        return get("/song/songs", {
            id,
            pageNum,
            num
        });
    }

    /**
     * 获取随机推荐歌曲列表
     * @returns 
     */
    static async getRandomRecommendSongs() {
        return get("/song/recommend");
    }

    /**
     * 获取歌曲排行榜
     * @param {Number} num 
     * @param {String} region 
     * @param {String} style 
     */
    static async getSongRankings(num = 4, region, style){
        return get("/song/song_rankings", {
            num,
            region,
            style
        })
    }


}