import VueRouter from "vue-router";
// 引入组件 引入跳转的路由组件
import Main from '../views/Main.vue'
import About from '../views/About.vue'
import MvList from '../views/Mv-List.vue'
import RankingList from '../views/Ranking-List.vue'
import SingerList from '../views/Singer-List.vue'
import SortList from '../views/Sort-List.vue'
import SongSheetDetail from '../views/sub/SongSheetDetail.vue'
import VideoDetail from "../views/sub/VideoDetail.vue"
import Singer from '../views/sub/Singer.vue'
import Song from '../views/sub/Song.vue'
import MyMusic from "../views/MyMusic.vue"
import store from "../store/index";
//创建并暴露一个路由器
const router = new VueRouter({
    routes: [{
            path: '/',
            redirect: "/main"
        },
        //主页面推荐
        {
            name: 'main',
            path: '/main',
            component: Main,
            meta: {
                title: "嗨.音乐"
            }
        },
        //mv列表页
        {
            name: 'mv_list',
            path: '/mv_list',
            component: MvList,
            meta: {
                title: "MV"
            }
        },
        //排行榜页
        {
            name: 'ranking_list',
            path: '/ranking_list',
            component: RankingList,
            meta: {
                title: "排行榜"
            }
        },
        //歌手列表页
        {
            name: 'singer_list',
            path: '/singer_list',
            component: SingerList,
            meta: {
                title: "歌手推荐"
            }
        },
        //歌单分类页
        {
            name: 'sort_list',
            path: '/sort_list',
            component: SortList,
            meta: {
                title: "分类歌单"
            }
        },
        //关于页
        {
            name: 'about',
            path: '/about',
            component: About,
            meta: {
                title: "关于作者"
            }
        },
        //歌单详情页
        {
            name: 'songSheet',
            path: '/song_sheet',
            component: SongSheetDetail,
            meta: {
                title: "歌单详情"
            }
        },
        //MV详情页
        {
            name: 'videoDetail',
            path: '/video_detail',
            component: VideoDetail,
            meta: {
                title: "MV详情"
            }
        },
        //歌手详情页
        {
            name: 'Singer',
            path: '/singer/:id',
            component: Singer,
            meta: {
                title: "歌手"
            }
        },
        //歌曲详情页
        {
            name: 'Song',
            path: '/song',
            component: Song,
            meta: {
                title: "歌曲"
            }
        },
        //我的音乐
        {
            name:"MyMusic",
            path: "/my_music",
            component: MyMusic,
            meta: {
                isAuth: true,
                title: "我的音乐"
            },
        },
        // //详情页
        // {
        //     path: "/detail",
        //     meta: {
        //         isAuth: false
        //     },
        //     children: [
        //         //歌单详情页
        //         {
        //             name: 'songSheet',
        //             path: 'song_sheet',
        //             component: SongSheetDetail,
        //             meta: {
        //                 title: "歌单详情"
        //             }
        //         },
        //     ]
        // }
    ]
});

//全局前置路由守卫
router.beforeEach((to, from, next) => {
    if (to.meta.isAuth) {
        if(JSON.stringify(store.state.userOptions.user) === '{}'){
            alert("你还未登录，请先登录！");
        }else{
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
    window.scrollTo(0,0);
});


export default router;