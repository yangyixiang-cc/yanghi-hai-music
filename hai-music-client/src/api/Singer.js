import {
    // post,
    get,
    // put,
    // _delete
} from '@/utils/request';

export default class Singer {

    /**
     * 获取歌手分页数据
     * @param {Number} pageNum 页码 
     * @param {Number} num 每页条数
     * @returns 
     */
    static async getSingerPage(pageNum, num = 20) {
        return get('/singer/page', {
            pageNum,
            num
        });
    }

    /**
     * 通过关键词搜索歌手
     * @param {String} keyword 关键词
     * @returns 
     */
    static async searchSingerByKeyword(keyword) {
        return get("/singer/search", {
            keyword
        });
    }

    /**
     * 根据歌手ID获取歌手信息
     * @param {Number} id 
     * @returns 
     */
    static async getSingerById(id) {
        return get("/singer/" + id);
    }

    /**
     * 获取相似歌手信息
     * @param {Number} num 获取数量 
     * @param {String} location 匹配条件
     * @returns 
     */
    static async getSimilarSingers(num = 6, location) {
        return get("/singer/silmar_singer", {
            num,
            location
        });
    }


    /**
     * 获取随机推荐歌手列表
     * @returns 
     */
    static async getRandomRecommendSingers() {
        return get("/singer/recommend");
    }


}