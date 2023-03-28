import axios from 'axios';
import {
  getToken,
  removeToken
} from '@/utils/auth'
import router from '@/router';

// 创建请求实例
const instance = axios.create({
  baseURL: 'http://127.0.0.1:8081/api',
  // 指定请求超时的毫秒数
  timeout: 5000,
  // 表示跨域请求时是否需要使用凭证
  withCredentials: false,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded',
    Accept: 'application/json'
  }
});

// 前置拦截器（发起请求之前的拦截）
instance.interceptors.request.use(
  (config) => {
    /**
     * 在这里一般会携带前台的参数发送给后台，比如下面这段代码：
     * const token = getToken()
     * if (token) {
     *  config.headers.token = token
     * }
     */
    // 是否需要设置 token
    const isToken = (config.headers || {}).isToken === false
    // do something before request is sent
    if (getToken() && !isToken) {
      config.headers.token = getToken()
    }
    // get请求映射params参数
    if (config.method === 'get' && config.params) {
      let url = config.url + '?'
      for (const propName of Object.keys(config.params)) {
        const value = config.params[propName]
        var part = encodeURIComponent(propName) + '='
        if (value !== null && typeof (value) !== 'undefined') {
          if (typeof value === 'object') {
            for (const key of Object.keys(value)) {
              if (value[key] !== null && typeof (value[key]) !== 'undefined') {
                const params = propName + '[' + key + ']'
                const subPart = encodeURIComponent(params) + '='
                url += subPart + encodeURIComponent(value[key]) + '&'
              }
            }
          } else {
            url += part + encodeURIComponent(value) + '&'
          }
        }
      }
      url = url.slice(0, -1)
      config.params = {}
      config.url = url
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

// 后置拦截器（获取到响应时的拦截）
instance.interceptors.response.use(
  (response) => {
    /**
     * 根据你的项目实际情况来对 response 和 error 做处理
     * 这里对 response 和 error 不做任何处理，直接返回
     */
    if (response.data.code === 401) {
      // user.removeUserInfo();
      removeToken();
      router.push({
        name: 'login',
        replace: true
      });
    }
    return response;
  },
  (error) => {
    const {
      response
    } = error;
    if (response && response.data) {
      return Promise.reject(error);
    }
    const {
      message
    } = error;
    if (message.includes('Network Error')) {
      router.push({
        name: '500'
      });
      return Promise.reject(error);
    }
  },
);

// 导出常用函数

/**
 * @param {string} url
 * @param {object} data
 * @param {object} params
 */
export const post = (url, data = {}, params = {}, config = {}) => {
  return instance({
    method: 'post',
    url,
    data,
    params,
    config
  });
};

/**
 * @param {string} url
 * @param {object} params
 */
export const get = (url, params = {}) => {
  return instance({
    method: 'get',
    url,
    params,
  });
};

/**
 * @param {string} url
 * @param {object} data
 * @param {object} params
 */
export const put = (url, data = {}, params = {}) => {
  return instance({
    method: 'put',
    url,
    params,
    data,
  });
};

/**
 * @param {string} url
 * @param {object} params
 */
export const _delete = (url, params = {}) => {
  return instance({
    method: 'delete',
    url,
    params,
  });
};

export default instance;
