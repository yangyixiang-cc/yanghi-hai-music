import store from "../store/index";
import VueRouter from "vue-router";
import layoutRouters from './modules/layout';
const constantRoutes = [...layoutRouters];

const router = new VueRouter({
    routes: constantRoutes
});

//全局前置路由守卫
router.beforeEach((to, from, next) => {
    if (to.meta.isAuth) {
        if (JSON.stringify(store.state.userOptions.user) === '{}') {
            alert("你还未登录，请先登录！");
        } else {
            next();
        }
    } else {
        next();
    }
});


//全局后置路由守卫

router.afterEach((to) => {
    if (to.meta.title) {
        document.title = to.meta.title;
    } else {
        document.title = "嗨.音乐"
    }
    window.scrollTo(0, 0);
});


export default router;