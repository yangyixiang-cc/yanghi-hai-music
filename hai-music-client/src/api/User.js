import {
    post,
    get,
    // put,
    // _delete
} from '@/utils/request';

export default class User {
 
  
    /**
     * 根据ID获取用户信息
     * @param {Number} id 
     * @returns 
     */
    static async getUserById(id) {
        return get('/user/' + id);
    }


    /**
     * 根据用户ID获取详细信息
     * @param {Number} userId 
     * @returns 
     */
    static async getUserDetailsById(userId){
        return get("/user/userDetails",{
            userId
        });
    }

    /**
     * 登录
     * @param {String} username 
     * @param {String} password 
     */
    static async login(data){
        return post("/user/login", data);
    }


    /**
     * 注册
     * @param {String} username 
     * @param {String} password 
     * @param {String} nickName
     */
    static async register(data){
        return post("/user/register", data);
    }

}