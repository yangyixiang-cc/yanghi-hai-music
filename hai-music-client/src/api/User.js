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