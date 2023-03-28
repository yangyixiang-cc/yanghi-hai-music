import {
    // post,
    get,
    // put,
    // _delete
} from '@/utils/request';

export default class Common {

    /**
     * 通用搜索
     * @param {String} keyword 
     * @returns 
     */
    static async getCommonSearch(keyword) {
        return get('/common/search', {
            keyword
        });
    }


}