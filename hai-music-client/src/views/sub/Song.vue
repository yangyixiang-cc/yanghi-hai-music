<template>
  <div class="main_box">
    <div class="top">
      <div class="left">
        <img :src="songInfo.pic" :alt="songInfo.name" />
      </div>
      <div class="right">
        <div class="song_name">{{ songInfo.name }}</div>
        <div class="singer_name">
          <a>{{ songInfo.singerName }}</a>
        </div>
        <div class="time">{{ songInfo.createTime | timeFormat }}</div>
        <div class="bot">
          <button><a class="fa fa-play" @click="playMusic()"></a></button>
          <button><a class="fa fa-heart-o"></a></button>
          <button><a class="fa fa-share"></a></button>
          <button><a class="fa fa-commenting-o"></a></button>
        </div>
      </div>
    </div>
    <div class="box" id="box">
      <div class="left">
        <div class="song_text">
          <div class="header">歌词</div>
          <p v-html="songInfo.lyric"></p>
        </div>
        <!-- 评论 -->
        <comment
          :commentsPageInfo="commentsPageInfo"
          eventName="obtainSongPageInfo"
        ></comment>
      </div>
      <div class="right">
        <div class="header">相关歌单</div>
        <div class="about_gd">
          <div v-for="item in recommendSongSheetList" :key="item.id"  @click.prevent="goMore('songSheet', item.id)">
            <hai-list :list="item"></hai-list>
          </div>
        </div>
        <div class="header">相关MV</div>
        <div class="about_mv">
          <div v-for="item in recommendMvList" :key="item.id"  @click.prevent="goMore('videoDetail',item.id)">
            <hai-list :list="item"></hai-list>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Comment from "@/components/common/Comment.vue";
import HaiList from "@/components/common/HaiList.vue";
import { mapState, mapMutations } from "vuex";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Song",
  components: { Comment, HaiList},
  data() {
    return {
      songInfo: {},
      //评论列表
      commentsPageInfo: [],
      //推荐歌单列表
      recommendSongSheetList: [
       
      ],
      //推荐MV列表
      recommendMvList: [
       
      ],
      requestNum: [],
    };
  },
  methods: {
    ...mapMutations("songOptions", {
      saveSongId: "SAVESONGID",
      saveSingerId: "SAVESINGERID",
    }),
    ...mapMutations("playOptions", {
      updateHistorySongs: "UPDATEHISTORYSONGS",
      updateFlog: "UPDATEFLOG",
    }),
    goMore(value, id = "") {
      if (id == "") {
        this.$router.push({
          name: value,
        });
      } else {
        this.$router.push({
          name: value,
          params: {
            id: id,
          },
        });
      }
    },
    //根据id获取歌曲信息
    async obtainSongInfo() {
      var patt = /(\[.*\])/g;
      const { data: songInfo } = await this.axios.get("/song/songInfo", {
        params: {
          id: this.songId,
        },
      });
      if (songInfo.code == 1) {
        songInfo.data.lyric = songInfo.data.lyric
          .replace(patt, "<br><br>")
          .slice(31);
        this.saveSingerId(songInfo.data.singerId);
        this.songInfo = songInfo.data;
      }
    },
     async obtainSimarSongSheet() {
      let temp = ["粤语","乐器","日韩","轻音乐","华语"];
      let index = parseInt(Math.random(temp.length-1));
      let style = temp[index];
      const { data: res } = await this.axios.get(
        "/song_sheet/recommend/style",
        {
          params: {
            num: 2,
            style: style,
          },
        }
      );
      if (res.code == 1) {
        this.recommendSongSheetList = res.data;
      }
    },
    async obtainRecommendMV() {
      const { data: res } = await this.axios.get("/video/recommend/style", {
        params: {
          num: 2,
          style: "现代",
        },
      });
      if (res.code == 1) {
        this.recommendMvList = res.data;
      }
    },
    //根据歌曲id获取对应的评论信息
    async obtainCommentsPageBySongId(args) {
      let pageNum = args.pageNum;
      let flog = args.flog;
      if (pageNum != -1 && this.requestNum.indexOf(pageNum) == -1 && !flog) {
        this.requestNum.push(pageNum);
        const { data: res } = await this.axios.get("/song/comments", {
          params: {
            id: this.songId,
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
        const { data: res } = await this.axios.get("/song/comments", {
          params: {
            id: this.songId,
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
      this.updateHistorySongs(this.songInfo);
      this.updateFlog(true);
    },
  },
  computed: {
    ...mapState("songOptions", {
      songId: "id",
      singerId: "singerId",
    }),
  },
  created() {
    this.saveSongId(this.$route.params.id);
  },
  mounted() {
    this.obtainSongInfo();
    this.obtainCommentsPageBySongId({
      pageNum: 1,
      flog: false,
    });
    this.obtainSimarSongSheet();
    this.obtainRecommendMV();
    this.$bus.$on("obtainSongPageInfo", this.obtainCommentsPageBySongId);
  },
  beforeDestroy() {
    this.$bus.$off("obtainSongPageInfo");
  },
};
</script>

<style lang="scss" scoped>
.main_box .top {
  display: flex;
  width: 98%;
  height: 5rem;
  margin: auto;
  margin-top: 0.25rem;
  padding-top: 0.5rem;
  padding-left: 0.25rem;
  border-bottom: 0.0125rem solid #c20c0c;
  border-radius: 0.05rem;
  background-color: white;
}

.main_box .top .left {
  flex: 2;
}
.main_box .top .left img {
  width: 3.75rem;
  height: 3.75rem;
  border-radius: 0.05rem;
}

.main_box .top .right {
  flex: 8;
  padding-left: 0.625rem;
}
.main_box .top .right div {
  margin-bottom: 0.125rem;
}

.main_box .top .right div:last-child {
  margin-top: 1.25rem;
}
.main_box .top .right .song_name {
  width: 100%;
  height: 0.5rem;
  line-height: 0.5rem;
  margin-top: 0.25rem;
  font-size: 0.45rem;
  font-weight: bolder;
  color: #000;
}

.main_box .top .right .singer_name {
  margin-top: 0.25rem;
  margin-bottom: 0.125rem;
}

.main_box .top .right .bot button {
  width: 1.75rem;
  height: 0.75rem;
  line-height: 0.75rem;
  margin: auto 0.125rem auto 0.125rem;
  background-color: #f7e8e9;
  border-radius: 0.05rem;
  font-size: 0.225rem;
  text-align: center;
  transition: all 0.3s ease-in;
}
.main_box .top .right .bot button a {
  display: inline-block;
  width: 100%;
  height: 100%;
  color: #000;
}

.main_box .top .right .bot button:first-child {
  background-color: #e35555;
}
.main_box .top .right .bot button:hover {
  background-color: #e35555;
  color: #000;
}

.main_box .top .right .bot button a:hover {
  color: #000;
}

.main_box .box {
  display: flex;
  width: 98%;
  height: auto;
  margin: auto;
  padding-top: 0.5rem;
}

.main_box .box .left {
  flex: 8;
}
.main_box .box .left .song_text .header {
  font-size: 0.3rem;
  font-weight: bolder;
  margin-bottom: 0.25rem;
  color: #000;
}
.main_box .box .left .song_text .flog {
  cursor: pointer;
}

.main_box .box .right {
  flex: 2;
}

.main_box .box .right .header {
  font-size: 0.225rem;
  width: 98%;
  border-bottom: 0.0125rem solid #c20c0c;
  margin-bottom: 0.125rem;
  color: #000;
}

.main_box .box .right .about_gd {
  margin-bottom: 0.5rem;
}

.main_box .box .right .about_gd > div {
  width: 100%;
  background-color: white;
  padding: 0.0625rem;
  margin: auto;
  margin-bottom: .125rem;
  cursor: pointer;
}

.main_box .box .right .about_mv {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
}

.main_box .box .right .about_mv > div {
  flex: 100%;
  width: 98%;
  margin: auto;
  margin-bottom: 0.125rem;
  background-color: white;
  padding: 0.0625rem;
  border-radius: 0.05rem;
  overflow: hidden;
  cursor: pointer;
}
</style>