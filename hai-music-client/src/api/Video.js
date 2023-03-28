import {
    // post,
    get,
    // put,
    // _delete
} from '@/utils/request';

export default class Video {

 
    /**
     * 根据ID获取MV信息
     * @param {Number} id 
     * @returns 
     */
    static async getVideoById(id) {
        return get('/video/' + id);
    }

    
    /**
     * 根据MV ID获取评论分页信息
     * @param {Number} id 
     * @param {Number} pageNum 
     * @param {Number} num 
     * @returns 
     */
    static async getCommentsPageByVideoId(id, pageNum, num = 5){
        return get("/video/comments",{
            id,
            pageNum,
            num
        });
    }

    /**
     * 根据MV风格获取指定条数推荐MV
     * @param {Number} num 
     * @param {String} style 
     * @returns 
     */
    static async getRecommendVideosByStyle(num = 3, style){
        return get("/video/recommend/style", {
            num,
            style
        })
    }

    /**
     * 获取随机推荐MV列表
     * @returns 
     */
    static async getRandomRecommendVideos(){
        return get("/video/recommend");
    }


    /**
     * 获取MV分页
     * @param {Number} pageNum 
     * @param {Number} num 
     * @param {String} region 
     * @param {String} edition 
     * @returns 
     */
    static async getVideoPage(pageNum, num = 16, region, edition){
        return get("/video/page", {
            pageNum,
            num,
            region,
            edition
        })
    }


    /**
     * 获取MV排行榜
     * @param {Number} num 
     * @param {String} region 
     */
    static async getVideoRankings(num = 12, region){
        return get("/video/video_rankings", {
            num,
            region
        })
    }
}