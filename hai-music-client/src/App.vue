<template>
  <div id="app">
    <div class="head">
      <div class="logo">
        <img src="@/assets/logo.png" alt="logo" />
      </div>
      <div class="head_nav">
        <a
          v-for="(item, index) in nav"
          :key="index"
          @click.prevent="changeTab(item.name, index)"
          :class="item.activeStyle"
          >{{ item.text }}</a
        >
      </div>
      <div class="search">
        <input
          type="search"
          name="search"
          placeholder="歌单/歌曲/歌手/MV"
          v-model="keyword"
        /><button class="fa fa-search" @click.prevent="toSearch"></button>
      </div>
      <div
        class="user_logon fa fa-user-o fa-2x fa-fw"
        @click="changeLogin"
        v-if="JSON.stringify(this.user) === '{}'"
      >
        &nbsp;登录
      </div>
      <div
        class="user_avator"
        v-if="JSON.stringify(this.user) !== '{}'"
        @click="showTab"
      >
        <img :src="this.user.pic" :alt="this.user.nickName" />
      </div>
      <!-- 登录后的头像菜单栏 -->
      <div class="login_tab" v-show="isShow">
        <a @click.prevent="goMyMusic">我的音乐</a>
        <a>个人主页</a>
        <a @click.prevent="exitLogin">退出登录</a>
        <div class="flog"></div>
      </div>
    </div>
    <Header></Header>
    <Search v-show="isSearch" :keyword="keyword" />
    <div class="content">
      <router-view></router-view>
    </div>
    <play-music
      :musicUrl="currentSong.url"
      status="play"
      :singer="currentSong.singerName"
      :songName="currentSong.name"
    ></play-music>
    <Footer></Footer>
    <Login v-show="isLogin"></Login>
    <div class="login-bg" v-show="isLogin"></div>
  </div>
</template>
<script>
import { mapState, mapMutations, mapGetters } from "vuex";
import Login from "./components/common/Login.vue";
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
import PlayMusic from "./components/common/PlayMusic.vue";
import Search from "./components/common/Search.vue";
export default {
  name: "App",
  components: {
    Login,
    Header,
    Footer,
    PlayMusic,
    Search,
  },
  data() {
    return {
      isShow: false,
      isLogin: false,
      keyword: "",
      currentSong: {},
      isSearch: false,
      nav: [
        {
          text: "发现音乐",
          name: "main",
          activeStyle: {
            change_nav: true,
          },
        },
        {
          text: "我的音乐",
          name: "MyMusic",
          activeStyle: {
            change_nav: false,
          },
        },
      ],
    };
  },
  methods: {
    ...mapMutations("userOptions", { removeUser: "REMOVEUSER" }),
    changeTab(name, index = 0) {
      if (name == "MyMusic" && !this.user.username) {
        if (this.$router.name !== name) {
          this.$router.push({
            name: name,
          });
        }
        return;
      }
      this.nav.forEach((item) => {
        item.activeStyle["change_nav"] = false;
      });
      this.nav[index].activeStyle["change_nav"] = true;
      if (this.$router.name !== name) {
        this.$router.push({
          name: name,
        });
      }
    },
    changeLogin() {
      this.isLogin = !this.isLogin;
    },
    showTab() {
      this.isShow = !this.isShow;
    },
    goMyMusic() {
      this.$router.push({
        name: "MyMusic",
      });
    },
    exitLogin() {
      this.removeUser();
      this.isShow = false;
    },
    toSearch() {
      if (this.keyword == "") return;
      this.isSearch = true;
    },
    closeSearch() {
      console.log(231123123123);
      this.isSearch = false;
      this.keyword = "";
    },
  },
  watch: {
    currentSongId() {
      this.currentSong = this.currentSongs;
    },
    keyword(nval) {
      if (nval == "") {
        this.isSearch = false;
      }
    },
  },
  computed: {
    ...mapState("userOptions", ["user"]),
    ...mapState("playOptions", ["currentSongId"]),
    ...mapGetters("playOptions", ["currentSongs"]),
  },
  created() {
    this.changeTab();
  },
  mounted() {
    this.$bus.$on("changeClose", this.changeLogin);
    this.$bus.$on("closeSearch", this.closeSearch);
  },
  beforeDestroy() {
    this.$bus.$off("changeClose");
    this.$bus.$off("closeSearch");
  },
};
</script>

<style lang="scss">
/* 把我们所有标签的内外边距清零 */
* {
  margin: 0;
  padding: 0;
  font-family: "poppin,Tahoma,Arial,\5FAE\8F6F\96C5\9ED1,sans-serif";
  /*css3 盒子模型*/
  box-sizing: border-box;
  font-size: 0.2rem;
}

/*rem自适应 1024-1920*/

@media screen and (max-width: 1024px) {
  html {
    font-size: 42.66px !important;
  }
}

@media screen and (min-width: 1920px) {
  html {
    font-size: 80px !important;
  }
}

/* em 和 i 斜体的文字不倾斜 */

em,
i {
  font-style: normal;
}

/* 去掉li 的小圆点 */

li {
  list-style: none;
}

img {
  /* border 0 照顾低版本浏览器 如果 图片外面包含了链接会有边框的问题 */
  border: 0;
  /* 取消图片底侧有空白缝隙的问题 */
  vertical-align: middle;
}

button {
  /* 当我们鼠标经过button 按钮的时候，鼠标变成小手 */
  cursor: pointer;
}

a {
  color: #666;
  text-decoration: none;
  cursor: pointer;
}

a:hover {
  text-decoration: none;
}
button,
input {
  font-family: Microsoft YaHei, Heiti SC, tahoma, arial, Hiragino Sans GB,
    "\5B8B\4F53", sans-serif;
  border: 0;
  outline: none;
}
.cursor {
  cursor: pointer;
}
//禁用按钮
.btn-disabled {
  pointer-events: none;
}

body {
  -webkit-font-smoothing: antialiased;
  background-color: #f9f9f9;
  font: 0.15rem/1.5 Microsoft YaHei, Heiti SC, tahoma, arial, Hiragino Sans GB,
    "\5B8B\4F53", sans-serif;
  color: #666;
}

body::-webkit-scrollbar {
  width: 0.1rem;
  height: 0.05rem;
  scrollbar-arrow-color: red;
}
body::-webkit-scrollbar-thumb {
  border-radius: 0.0625rem;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: rgba(0, 0, 0, 0.2);
  scrollbar-arrow-color: red;
}
body::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 0.0625rem rgba(0, 0, 0, 0.2);
  border-radius: 0;
  background: #f9f9f9;
}

.hide,
.none {
  display: none;
}

/* 清除浮动 */
.clear {
  clear: both;
}

.clearfix {
  *zoom: 1;
}

.clearfix:after {
  visibility: hidden;
  clear: both;
  display: block;
  content: ".";
  height: 0;
}

a:link,
:visited {
  text-decoration: none;
  color: #000;
}

a:hover {
  color: #c20c0c;
}

/* 注册页校验 */

.from_error {
  border: 0.025rem solid #c20c0c !important;
}

.from_right {
  border: 0.025rem solid greenyellow !important;
}

/*按钮变色*/

.btn_red {
  color: #c20c0c;
}

/* 导航栏激活后的颜色 */
.active-red {
  color: #c20c0c;
  border-bottom: 0.0375rem solid #c20c0c;
}

// 顶部导航条

.head {
  display: flex;
  width: 100%;
  height: 1.125rem;
  line-height: 1.125rem;
  background-color: #f9f9f9;
  border: none;
  border-bottom: 0.025rem solid #c20c0c;
  .logo {
    padding-left: 0.125rem;
    padding-bottom: 0.125rem;
    img {
      width: 3.5rem;
      height: 0.875rem;
    }
  }
  .head_nav {
    height: 1.125rem;
    margin-left: 0.25rem;
    margin-right: 0.25rem;
    a {
      display: inline-block;
      width: 1.375rem;
      height: 1.1125rem;
      text-align: center;
      font-size: 0.225rem;
      margin-left: 0.125rem;
      color: #000;
    }
    a:hover {
      background-color: #e35555;
    }
  }
  .search {
    margin-left: 5rem;
    color: #000;
    input {
      border: none;
      font-size: 0.225rem;
      width: 6.25rem;
      height: 0.6875rem;
      border-radius: 0.05rem 0rem 0rem 0.05rem;
      text-align: left;
      background-color: white;
      padding-left: 0.375rem;
      padding-right: 0.125rem;
    }

    button {
      width: 2rem;
      height: 0.6875rem;
      border-radius: 0rem 0.05rem 0.05rem 0rem;
      text-align: center;
      background-color: #f25b67;
      transition-duration: 0.4s;
    }
    button:hover {
      opacity: 0.8;
    }
  }

  .user_logon {
    width: 1rem;
    height: 0.45rem;
    line-height: 0.45rem;
    position: absolute;
    top: 0.3375rem;
    right: 0.25rem;
    font-size: 0.25rem;
    text-align: center;
    color: #000;
    cursor: pointer;
  }
  .user_logon:hover {
    color: #c20c0c;
  }

  .user_avator {
    position: absolute;
    top: 0.125rem;
    right: 0.375rem;
    width: 0.875rem;
    height: 0.875rem;
    line-height: 0.75rem;
    transition: all 0.4s ease;
    img {
      width: 0.875rem;
      height: 0.875rem;
      border-radius: 50%;
    }
    :hover {
      background-color: #ddd;
      opacity: 0.5;
    }
  }

  .login_tab {
    position: fixed;
    top: 1.1875rem;
    right: 0.125rem;
    width: 1.25rem;
    height: auto;
    background-color: white;
    opacity: 0.9;
    z-index: 999;
    border-radius: 0.05rem;
  }

  .login_tab .flog {
    position: absolute;
    width: 0;
    height: 0;
    border-width: 0 0.125rem 0.125rem 0.125rem;
    border-style: solid;
    border-color: transparent transparent white transparent;
    top: -0.125rem;
    left: 0.5rem;
  }

  .login_tab a {
    display: block;
    width: 100%;
    height: 0.625rem;
    line-height: 0.625rem;
    font-size: 0.2rem;
    font-weight: bold;
    text-align: center;
  }

  .login_tab a:first-child {
    border-bottom: 0.0125rem solid #c20c0c;
  }
}

.change_nav {
  background-color: #f25b67;
}

//遮罩层
.login-bg {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 8888;
}

/* 内容显示区域 */
.main_box {
  width: 80%;
  height: auto;
  margin: auto;
  background-color: #f9f9f9;
}
</style>
