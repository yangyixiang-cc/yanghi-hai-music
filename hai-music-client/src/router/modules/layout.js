export default [{
    path: '/',
    redirect: "/main"
  },
  //主页面推荐
  {
    name: 'main',
    path: '/main',
    component: () => import('@/views/Main.vue'),
    meta: {
      title: "嗨.音乐"
    }
  },
  //mv列表页
  {
    name: 'mv_list',
    path: '/mv_list',
    component: () => import('@/views/Mv-List.vue'),
    meta: {
      title: "MV"
    }
  },
  //排行榜页
  {
    name: 'ranking_list',
    path: '/ranking_list',
    component: () => import('@/views/Ranking-List.vue'),
    meta: {
      title: "排行榜"
    }
  },
  //歌手列表页
  {
    name: 'singer_list',
    path: '/singer_list',
    component: () => import('@/views/Singer-List.vue'),
    meta: {
      title: "歌手推荐"
    }
  },
  //歌单分类页
  {
    name: 'sort_list',
    path: '/sort_list',
    component: () => import('@/views/Sort-List.vue'),
    meta: {
      title: "分类歌单"
    }
  },
  //关于页
  {
    name: 'about',
    path: '/about',
    component: () => import('@/views/About.vue'),
    meta: {
      title: "关于作者"
    }
  },
  //歌单详情页
  {
    name: 'songSheet',
    path: '/song_sheet',
    component: () => import('@/views/sub/SongSheetDetail.vue'),
    meta: {
      title: "歌单详情"
    }
  },
  //MV详情页
  {
    name: 'videoDetail',
    path: '/video_detail',
    component: () => import('@/views/sub/VideoDetail.vue'),
    meta: {
      title: "MV详情"
    }
  },
  //歌手详情页
  {
    name: 'Singer',
    path: '/singer/:id',
    component: () => import('@/views/sub/Singer.vue'),
    meta: {
      title: "歌手"
    }
  },
  //歌曲详情页
  {
    name: 'Song',
    path: '/song',
    component: () => import('@/views/sub/Song.vue'),
    meta: {
      title: "歌曲"
    }
  },
  //我的音乐
  {
    name: "MyMusic",
    path: "/my_music",
    component: () => import('@/views/MyMusic.vue'),
    meta: {
      isAuth: true,
      title: "我的音乐"
    },
  },
]