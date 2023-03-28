import {
    post,
    // get,
    // put,
    // _delete
} from '@/utils/request';

export default class Comments {

    /**
     * 保存用户的评论
     */
    static async saveCommentsByUser(data){
        return post("/comments/user", data);
    }

}