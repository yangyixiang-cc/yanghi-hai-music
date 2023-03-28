import {
    // post,
    get,
    // put,
    // _delete
} from '@/utils/request';

export default class SongSheet {

    /**
     * 根据歌单ID获取歌曲信息
     * @param {Number} id 
     * @returns 
     */
    static async getSongSheetById(id) {
        return get('/song_sheet/' + id);
    }


    /**
     * 根据歌单ID获取歌曲分页
     * @param {Number} id 
     * @param {Number} pageNum 
     * @param {Number} num 
     * @returns 
     */
    static async getSongPageBySongSheetId(id, pageNum, num = 6){
        return get("/song_sheet/songs",{
            id,
            pageNum,
            num
        });
    }


    /**
     * 根据歌单ID获取评论分页
     * @param {Number} id 
     * @param {Number} pageNum 
     * @param {Number} num 
     * @returns 
     */
    static async getCommentsPageBySongSheetId(id, pageNum, num = 5){
        return get("/song_sheet/comments",{
            id,
            pageNum,
            num
        });
    }

    /**
     * 根据歌单风格返回指定条数的推荐歌单
     * @param {Number} num 返回条数
     * @param {String} style 歌单风格
     */
    static async getRecommendSongSheetByStyle(num = 3, style){
        return get("/song_sheet/recommend/style",{
            num,
            style
        });
    }

    /**
     * 获取随机推荐歌单列表
     * @returns 
     */
    static async getRandomRecommendSongSheets(){
        return get("/song_sheet/recommend");
    }


    /**
     * 获取歌单分页通过风格
     * @param {Number} pageNum 
     * @param {Number} num 
     * @param {String} style 
     */
    static async getSongSheetPageByStyle(pageNum, num = 12, style){
        return get("/song_sheet/page", {
            pageNum,
            num,
            style
        })
    }


}