<template>
  <div class="main_box">
    <div class="left">
      <div class="top">
        <div class="header">
          <h2 class="fa fa-play-circle">：{{ videoInfo.title }}</h2>
          <p>
            {{ videoInfo.introduction }} 播放量：{{
              videoInfo.playVolume | formatNumber
            }}
          </p>
        </div>
        <div class="top_video">
          <div class="video">
            <video :src="videoInfo.url" controls></video>
          </div>
        </div>
        <div class="collect">
          <a class="fa fa-thumbs-o-up"
            ><span class="num" v-show="videoInfo.supportVolume != 0">{{
              videoInfo.supportVolume | formatNumber
            }}</span></a
          >
          <a class="fa fa-heart-o"
            ><span class="num" v-show="videoInfo.collectVolume != 0">{{
              videoInfo.collectVolume | formatNumber
            }}</span></a
          >
          <a class="fa fa-share"
            ><span class="num" v-show="videoInfo.shareVolume != 0">{{
              videoInfo.shareVolume | formatNumber
            }}</span></a
          >
        </div>
      </div>
      <!-- 评论 -->
      <comment
        :commentsPageInfo="commentsPageInfo"
        eventName="obtainVideoPageInfo"
      ></comment>
    </div>
    <div class="right">
      <div class="information_bar">
        <div class="l">
          <div class="t">
            <a @click.prevent="goSinger(singerInfo.id)"
              ><img :src="singerInfo.pic" alt="歌手"
            /></a>
            <div>
              <a @click.prevent="goSinger(singerInfo.id)">{{
                singerInfo.name
              }}</a>
              <p>{{ singerInfo.supportVolume | formatNumber }}获赞</p>
            </div>
          </div>
          <div class="b">
            <p>{{ singerInfo.introduction }}</p>
          </div>
        </div>
        <div class="r">
          <button>{{ flog ? "已关注" : "关注" }}</button>
        </div>
      </div>
      <div class="recommendation_list">
        <div class="relative">相关推荐</div>
        <ul>
          <li v-for="item in recommendList" :key="item.id"  @click="goVideoDetail(item.id)">
            <hai-list :list="item"></hai-list>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import Comment from "@/components/common/Comment.vue";
import HaiList from "@/components/common/HaiList.vue";
import { mapState, mapMutations } from "vuex";
export default {
  name: "VideoDetail",
  components: { Comment, HaiList },
  data() {
    return {
      videoId: "",
      //评论列表
      commentList: [],
      //相关推荐列表
      recommendList: [],
      flog: false,
      style: "",
      singerInfo: {},
      videoInfo: {},
      //评论列表分页数据
      commentsPageInfo: [],
      requestNum: [],
    };
  },
  computed: {
    ...mapState("videoOptions", ["id", "userId"]),
  },
  methods: {
    ...mapMutations("videoOptions", {
      saveVideoId: "SAVEVIDEOID",
      saveUserId: "SAVEUSERID",
    }),
    goSinger(id) {
      this.$router.push({
        name: "Singer",
        params: {
          id: id,
        },
      });
    },
    async obtainVideoInfo() {
      const { data: res } = await this.axios.get("/video/" + this.videoId);
      if (res.code == 1) {
        this.singerInfo = res.data.singer;
        this.videoInfo = res.data.video;
        this.style = res.data.video.style;
        this.flog = res.data.flog;
      }
    },
    //根据Videoid获取对应的评论信息
    async obtainCommentsPageByVideoId(args) {
      let pageNum = args.pageNum;
      let flog = args.flog;
      if (pageNum != -1 && this.requestNum.indexOf(pageNum) == -1 && !flog) {
        this.requestNum.push(pageNum);
        const { data: res } = await this.axios.get("/video/comments", {
          params: {
            id: this.videoId,
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
        const { data: res } = await this.axios.get("/video/comments", {
          params: {
            id: this.videoId,
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
    async recommendVideo() {
      const { data: res } = await this.axios.get("/video/recommend/style", {
        params: {
          num: 3,
          style: this.style,
        },
      });
      if (res.code == 1) {
        this.recommendList = res.data;
      }
    },
    goVideoDetail(id){
      this.$router.replace({
          name: "videoDetail",
          params: {
            id: id,
            num:+new Date()
          },
        });
    }
  },
  created() {
    this.saveVideoId(this.$route.params.id);
    this.videoId = this.$route.params.id;
  },
  mounted() {
    this.obtainVideoInfo();
    this.obtainCommentsPageByVideoId({
      pageNum: 1,
      flog: false,
    });
    this.recommendVideo();
    this.$bus.$on("obtainVideoPageInfo", this.obtainCommentsPageByVideoId);
  },
  beforeDestroy() {
    this.$bus.$off("obtainVideoPageInfo");
  },
};
</script>

<style lang="scss" scoped>
.main_box {
  display: flex;
  width: 19.5rem;
  height: auto;
  margin: auto;
}

.main_box .left {
  flex: 7;
  height: auto;
}

.main_box .right {
  flex: 3;
  width: 0;
  height: auto;
  overflow: hidden;
}

.main_box .left .top {
  width: 100%;
  height: auto;
  overflow: hidden;
}

.main_box .left .top > .header {
  width: 98%;
  height: 1.5rem;
  margin-top: 0.25rem;
  background-color: white;
  padding: 0.125rem;
  padding-left: 0.25rem;
  border-radius: 0.05rem;
  color: #c20c0c;
  border-bottom: 1px solid #c20c0c;
}

.main_box .left .top > .header h2 {
  margin-top: 0.125rem;
  margin-bottom: 0.375rem;
  font-size: 0.4rem;
}

.main_box .left .top > .header p {
  font-size: 0.225rem;
}

.main_box .left .top .collect {
  width: 100%;
  margin: auto;
  height: 1rem;
  line-height: 1rem;
  background-color: white;
  margin-top: 0.25rem;
  border-radius: 0.05rem;
  border-bottom: 0.0125rem solid #c20c0c;
}

.main_box .left .top .collect a {
  display: inline-block;
  width: 1.5rem;
  height: 1rem;
  line-height: 1rem;
  margin-right: 0.125rem;
  text-align: center;
  font-size: 0.3rem;
}

.main_box .left .top .collect a span {
  text-align: 0.15rem;
  padding-left: 0.1rem;
  padding-bottom: 0.0625rem;
}

.main_box .left .bot {
}

.top_video {
  position: relative;
  width: 100%;
  height: auto;
  overflow: hidden;
}

.top_video h2 {
  color: #c20c0c;
  text-align: left;
  font-size: 0.3rem;
  font-weight: bold;
  margin-top: 0.75rem;
  margin-left: 0.5rem;
}

/* 视频部分 end */

.video {
  width: 100%;
  height: 9rem;
  background-color: #000;
  margin-top: 0.5rem;
  overflow: hidden;
}

video {
  width: 100%;
  height: 100%;
  outline: 0;
}

/*收藏*/

.top .collect {
  width: 100%;
  height: 1.25rem;
  line-height: 1.25rem;
}

/*右栏*/

.main_box .right .information_bar {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin: auto;
  margin-top: 0.25rem;
  height: 1.75rem;
  background-color: white;
  border-radius: 0.05rem;
  .l {
    width: 70%;
    .t {
      margin-top: 0.25rem;
      display: flex;
      div {
        padding-left: 10px;
        padding-top: 0.1875rem;
        p {
          margin-top: 0.1rem;
          font-size: 0.175rem;
        }
      }
    }
    .b {
      margin-top: 0.125rem;
      margin-left: 0.25rem;
    }
    a:last-child {
      font-size: 0.2rem;
      color: #000;
    }
    img {
      width: 0.75rem;
      height: 0.75rem;
      margin-top: 0.125rem;
      margin-left: 0.1875rem;
      border-radius: 50%;
    }
    p:first-child {
      font-weight: 600;
    }
    p:last-child {
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .r {
    width: 30%;
    height: 1.75rem;
    line-height: 1.75rem;
    text-align: center;
    button {
      width: 1rem;
      height: 0.5rem;
      line-height: 0.5rem;
      border-radius: 0.1rem;
      background-color: #e35555;
    }
    button:hover {
      background-color: #d11212;
    }
  }
}

/*视频推荐*/

.main_box .right .recommendation_list {
  width: 98%;
  height: auto;
  margin-top: 0.125rem;
  margin: auto;
}

.main_box .right .recommendation_list .relative {
  width: 98%;
  margin: auto;
  height: 0.75rem;
  line-height: 0.75rem;
  font-size: 0.25rem;
  font-weight: bold;
  color: #000;
  border-bottom: 0.0125rem solid #c20c0c;
}

.main_box .right .recommendation_list ul {
  width: 100%;
  margin-top: 0.125rem;
  li{
    cursor: pointer;
  }
}
</style>