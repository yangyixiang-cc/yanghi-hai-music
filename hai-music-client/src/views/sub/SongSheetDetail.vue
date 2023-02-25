<template>
  <div class="main_box">
    <div class="top">
      <div class="img"><img :src="songSheetInfo.pic" alt="歌单封面" /></div>
      <div class="introduce">
        <h1>{{ songSheetInfo.title }}</h1>
        <div class="gs">
          <img :src="userInfo.pic" :alt="userInfo.nickName" />
          <a>{{ userInfo.nickName }}</a>
          <span>{{ songSheetInfo.createTime | timeFormat }}</span>
        </div>
        <p>
          {{ songSheetInfo.introduction }}
        </p>
        <div class="imgblog">
          <a class="fa fa-play" @click="playMusic"></a>
          <a class="fa fa-heart-o"></a>
          <a class="fa fa-share"></a>
          <a class="fa fa-arrow-circle-down"></a>
          <a class="fa fa-commenting-o"></a>
        </div>
      </div>
    </div>
    <div class="bot">
      <div class="left">
        <div class="song_header"><span>歌曲列表</span></div>
        <song-list
          :songsPageInfo="songsPageInfo"
          eventName="obtainSongsPageInfo"
        ></song-list>
        <hai-ad :url="require('@/assets/images/ad2.jpg')"></hai-ad>
        <comment
          :commentsPageInfo="commentsPageInfo"
          eventName="obtainSongSheetPageInfo"
        ></comment>
      </div>
      <div class="right">
        <div class="header">相关歌单</div>
        <div class="box">
          <div class="hai-card" v-for="item in cardList" :key="item.id">
            <a><img :src="item.pic" /></a>
            <a>
              <p v-text="item.title"></p>
            </a>
            <p class="introduction" v-text="item.introduction"></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SongList from "@/components/common/SongList.vue";
import HaiAd from "@/components/common/HaiAd.vue";
import Comment from "@/components/common/Comment.vue";
import { mapState, mapMutations } from "vuex";
export default {
  components: { SongList, HaiAd, Comment },
  name: "SongSheetDetail",
  data() {
    return {
      //歌单信息
      songSheetInfo: {},
      //用户信息
      userInfo: {},
      //评论列表分页数据
      commentsPageInfo: [],
      //歌曲列表分页数据
      songsPageInfo: {},
      //推荐卡片列表
      cardList: [],
      style: "",
      requestNum: [],
    };
  },
  computed: {
    ...mapState("songSheetOptions", ["id", "userId"]),
  },
  methods: {
    ...mapMutations("songSheetOptions", {
      saveSongSheetId: "SAVESONGSHEETID",
      saveUserId: "SAVEUSERID",
    }),
    ...mapMutations("playOptions", {
      updateHistorySongs: "UPDATEHISTORYSONGS",
      updateFlog: "UPDATEFLOG",
    }),
    //根据id获取歌单信息
    async obtainSongSheetInfo() {
      const { data: res } = await this.axios.get("/song_sheet/" + this.id);
      if (res.code == 1) {
        this.songSheetInfo = res.data;
        this.style = res.data.style;
        this.saveUserId(res.data.userId);
      }
    },
    //根据歌单信息中的userId获取创建歌单的用户信息
    async obtainUserInfo() {
      const { data: res } = await this.axios.get("/user/" + this.userId);
      if (res.code == 1) {
        this.userInfo = res.data;
      }
    },
    //根据歌单id获取对应的歌曲分页数据
    async obtainSongsPage(args) {
      let pageNum = args.pageNum;
      const { data: res } = await this.axios.get("/song_sheet/songs", {
        params: {
          id: this.id,
          pageNum: pageNum,
          num: 6,
        },
      });
      if (res.code == 1) {
        this.songsPageInfo = res.data;
      }
    },
    //根据歌单id获取对应的评论信息
    async obtainCommentsPageBySongSheetId(args) {
      let pageNum = args.pageNum;
      let flog = args.flog;
      if (pageNum != -1 && this.requestNum.indexOf(pageNum) == -1 && !flog) {
        this.requestNum.push(pageNum);
        const { data: res } = await this.axios.get("/song_sheet/comments", {
          params: {
            id: this.id,
            pageNum: pageNum,
            num: 5,
          },
        });
        if (res.code == 1) {
          if (this.commentsPageInfo.length == 0) {
            this.commentsPageInfo = res.data;
          } else {
            let temp = this.commentsPageInfo.slice(
              0,
              this.commentsPageInfo.length - 1
            );
            this.commentsPageInfo = [...temp, ...res.data];
          }
        }
      } else if (
        pageNum != -1 &&
        this.requestNum.indexOf(pageNum) != -1 &&
        !flog
      ) {
        let obj = this.commentsPageInfo[this.commentsPageInfo.length - 1];
        this.$set(this.commentsPageInfo, this.commentsPageInfo.length - 1, {
          total: obj.total,
          current: pageNum,
          pages: obj.pages,
          size: obj.size,
        });
      } else if (pageNum == -1 && flog) {
        const { data: res } = await this.axios.get("/song_sheet/comments", {
          params: {
            id: this.id,
            pageNum: 1,
            num: 5,
          },
        });
        if (res.code == 1) {
          this.commentsPageInfo = res.data;
          this.requestNum = [];
        }
      }
    },
    playMusic() {
      this.updateHistorySongs(this.songsPageInfo.records[0]);
      this.updateFlog(true);
    },
    async obtainSimarSongSheet() {
      const { data: res } = await this.axios.get(
        "/song_sheet/recommend/style",
        {
          params: {
            num: 3,
            style: this.style,
          },
        }
      );
      if (res.code == 1) {
        this.cardList = res.data;
      }
    },
  },
  created() {
    this.saveSongSheetId(this.$route.params.id);
  },
  mounted() {
    this.obtainSongSheetInfo();
    this.obtainUserInfo();
    this.obtainSimarSongSheet();
    this.obtainSongsPage({
      pageNum: 1,
    });
    this.obtainCommentsPageBySongSheetId({
      pageNum: 1,
      flog: false,
    });
    this.$bus.$on("obtainSongsPageInfo", this.obtainSongsPage);
    this.$bus.$on(
      "obtainSongSheetPageInfo",
      this.obtainCommentsPageBySongSheetId
    );
  },
  beforeDestroy() {
    this.$bus.$off("obtainSongsPageInfo");
    this.$bus.$off("obtainSongSheetPageInfo");
  },
};
</script>

<style lang="scss" scoped>
.main_box {
  width: 19.5rem;
  margin: auto;
  padding-top: 0.25rem;
}
.main_box .top {
  display: flex;
  width: 98%;
  margin: auto;
  background-color: white;
  border-radius: 0.05rem;
  border-bottom: 0.0125rem solid #c20c0c;
}
.main_box .top .img {
  flex: 3;
  width: 2.5rem;
  height: 3.75rem;
  margin: 0.25rem;
}

.main_box .top .img img {
  width: 100%;
  height: 100%;
  border-radius: 0.05rem;
}

.main_box .top .introduce {
  flex: 7;
  width: 100%;
  height: 7.5rem;
  padding-bottom: 0.125rem;
  padding-left: 0.25rem;
}
.main_box .top .introduce p {
  margin-top: 0.25rem;
  font-size: 0.225rem;
  word-spacing: 0.125rem;
  padding-right: 0.625rem;
  text-indent: 1em;
  line-height: 0.5rem;
}
.main_box .top .introduce > h1 {
  font-size: 0.4rem;
  margin-top: 0.125rem;
  color: #000;
}
.main_box .top .introduce .gs {
  height: 0.3rem;
  line-height: 0.3rem;
  margin-top: 0.375rem;
  margin-bottom: 0.375rem;
}

.main_box .top .introduce .gs span {
  margin-left: 0.5rem;
}

.main_box .top .introduce .gs img {
  width: 0.375rem;
  height: 0.375rem;
  margin-right: 0.1875rem;
}

.main_box .top .introduce .gs a {
  padding-left: 0.125rem;
  padding-right: 0.125rem;
  font-weight: bolder;
}

.main_box .top .introduce p {
  height: 2.5rem;
  overflow: auto;
  border-left: 0.05rem solid #f7e8e9;
  padding-left: 0.25rem;
  overflow-y: scroll;
  cursor: pointer;
}

.main_box .top .introduce p::-webkit-scrollbar {
  width: 0.05rem;
  height: 0.05rem;
  scrollbar-arrow-color: red;
}
.main_box .top .introduce p::-webkit-scrollbar-thumb {
  border-radius: 0.0625rem;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: rgba(0, 0, 0, 0.2);
  scrollbar-arrow-color: red;
}
.main_box .top .introduce p::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 0.0625rem rgba(0, 0, 0, 0.2);
  border-radius: 0;
  background: #f7e8e9;
}

.main_box .top .introduce .imgblog {
  height: 0.5rem;
  line-height: 0.5rem;
  margin-top: 1.25rem;
}
.main_box .top .introduce .imgblog a {
  display: inline-block;
  width: 1.25rem;
  height: 0.625rem;
  line-height: 0.625rem;
  text-align: center;
  background-color: #f7e8e9;
  font-size: 0.225rem;
  margin-right: 0.0625rem;
  border-radius: 0.05rem;
  color: #000;
}

.main_box .top .introduce .imgblog a:first-child {
  background-color: #e35555;
}

.main_box .top .introduce .imgblog a:hover {
  color: #000;
  background-color: #e35555;
}

.main_box .song_header {
  width: 98%;
  height: 0.75rem;
  line-height: 0.75rem;
  margin: auto;
  margin-top: 0.45rem;
  font-size: 0.3rem;
  color: #000;
  font-weight: bolder;
}
.main_box .bot {
  display: flex;
  width: 98%;
  height: auto;
  margin: auto;
  margin-top: 0.0625rem;
}
.main_box .bot .left {
  flex: 8;
}

.main_box .bot .left > .song_header span {
  color: #000;
  font-size: 0.3rem;
  font-weight: bolder;
}

.main_box .bot .right {
  flex: 2;
  width: 98%;
  margin: auto;
  margin-top: 1.25rem;
}

.main_box .bot .right .header {
  width: 100%;
  height: 0.5rem;
  line-height: 0.5rem;
  border-bottom: 0.0125rem solid #c20c0c;
  font-size: 0.225rem;
  font-weight: bold;
  color: #000;
}

.main_box .bot .right .box {
  width: 100%;
  margin-top: 0.25rem;
}
.hai-card {
  width: 100%;
  margin-bottom: 0.25rem;
  background-color: white;
  padding: 0.0625rem;
  border-radius: .05rem;
  a:first-child {
    transition: all 0.3s ease-in;
  }

  a:first-child:hover {
    opacity: 0.8;
  }
  a > p {
    color: #000;
    font-weight: bolder;
  }
  .introduction {
    width: 3.75rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  a > img {
    width: 100%;
    height: 1.75rem;
  }

  > a {
    color: #000;
  }

  > a:hover {
    color: #c20c0c;
  }
}
</style>