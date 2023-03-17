import {
    post,
    get,
    put,
    _delete
} from '@/utils/request';

export default class Singer {

    static async getRolesAndStatusIsNormal() {
        return get('/role/status_normal');
    }

}