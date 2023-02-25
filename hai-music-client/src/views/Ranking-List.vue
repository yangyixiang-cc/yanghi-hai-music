<template>
  <div class="main_box">
    <div class="box">
      <!-- 排行榜 -->
      <div class="left">
        <div class="ranking" v-for="(ranking, i) in rankings" :key="i">
          <div class="header" v-text="ranking.name"></div>
          <div class="bot">
            <ul>
              <li
                v-for="(item, index) in ranking.list"
                :key="index"
                @click="goChangeNav(i, index)"
                :class="fIndex == i && sIndex == index ? 'tab-active' : ''"
              >
                <a>{{ item }}</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="right">
        <div class="header">
          <div class="top">
            <span id="name">{{ title }}</span
            ><span id="time">{{
              new Date() | timeFormat((str = "YYYY-MM-DD"))
            }}</span>
          </div>
          <div class="bot">
            <a class="fa fa-play" @click="playMusic"></a>
            <a class="fa fa-arrow-circle-down"></a>
          </div>
        </div>
        <!-- 歌曲列表 -->
        <SongsList
          :songsPageInfo="songsPageInfo"
          :flog="true"
          v-show="
            rankings[fIndex].name != 'MV榜' &&
            rankings[fIndex].list[sIndex] != 'MV榜'
          "
        ></SongsList>
        <div
          class="mv-list"
          v-show="
            rankings[fIndex].name == 'MV榜' ||
            rankings[fIndex].list[sIndex] == 'MV榜'
          "
        >
          <div class="card head">
            <div class="left"></div>
            <div class="pic">MV</div>
            <div class="middle">简介</div>
            <div class="right"></div>
          </div>
          <div v-for="(item, index) in videoList" :key="item.id" class="card">
            <div class="left">{{ index + 1 }}</div>
            <div class="pic" @click="goVideoDetail(item.id)">
              <img :src="item.pic" alt="mv" />
            </div>
            <div class="middle" @click="goVideoDetail(item.id)">
              <p class="title">
                {{ item.title }}
              </p>
              <p class="name">
                <a>{{ item.introduction }}</a>
              </p>
            </div>
            <div class="right">
              <a class="fa fa-heart-o"></a>
              <a class="fa fa-play"></a>
            </div>
          </div>
          <div class="empty-list" v-show="videoList.length == 0">
            暂时还没有MV哦！
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
import SongsList from "../components/common/SongList.vue";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "RankingList",
  components: {
    SongsList,
  },
  data() {
    return {
      title: "热歌榜",
      rankings: [
        {
          name: "巅峰榜",
          list: ["热歌榜", "MV榜"],
        },
        {
          name: "地区榜",
          list: ["内地榜", "港台榜", "欧美榜", "非洲榜", "日本榜", "韩国榜"],
        },
        {
          name: "曲风榜",
          list: [
            "电音榜",
            "ACG榜",
            "摇滚榜",
            "民谣榜",
            "国电榜",
            "古典榜",
            "古风榜",
            "轻音乐榜",
            "说唱榜",
          ],
        },
        {
          name: "MV榜",
          list: ["内地榜", "港台榜", "欧美榜", "日本榜", "韩国榜"],
        },
      ],
      //歌曲列表
      songsPageInfo: {},
      videoList: [],
      fIndex: 0,
      sIndex: 0,
    };
  },
  methods: {
    ...mapMutations("playOptions", {
      updateHistorySongs: "UPDATEHISTORYSONGS",
      updateFlog: "UPDATEFLOG",
    }),
    goVideoDetail(id) {
      this.$router.push({
        name: "videoDetail",
        params: {
          id: id,
        },
      });
    },
    obtainRankingList(style = "", region = "") {
      this.axios
        .get("/song/song_rankings", {
          params: {
            num: 20,
            region: region,
            style: style,
          },
        })
        .then((response) => {
          if (response.data.code == 1) {
            this.songsPageInfo = response.data.data;
          }
        });
    },
    obtainVideoRanking(region = "") {
      this.axios
        .get("/video/video_rankings", {
          params: {
            num: 12,
            region: region,
          },
        })
        .then((response) => {
          if (response.data.code == 1) {
            this.videoList = response.data.data;
          }
        });
    },
    goChangeNav(i, index) {
      this.songsPageInfo = {};
      this.videoList = [];
      this.fIndex = i;
      this.sIndex = index;
      this.title = this.rankings[i].list[index];
      if (
        this.rankings[i].name == "巅峰榜" &&
        this.rankings[i].list[index] == "热歌榜"
      ) {
        this.obtainRankingList();
      } else if (
        this.rankings[i].name == "巅峰榜" &&
        this.rankings[i].list[index] == "MV榜"
      ) {
        this.obtainVideoRanking();
      } else if (this.rankings[i].name == "地区榜") {
        this.obtainRankingList(
          "",
          this.rankings[i].list[index].substring(
            0,
            this.rankings[i].list[index].length - 1
          )
        );
      } else if (this.rankings[i].name == "曲风榜") {
        this.obtainRankingList(
          this.rankings[i].list[index].substring(
            0,
            this.rankings[i].list[index].length - 1
          ),
          ""
        );
      } else if (this.rankings[i].name == "MV榜") {
        this.obtainVideoRanking(
          this.rankings[i].list[index].substring(
            0,
            this.rankings[i].list[index].length - 1
          )
        );
      }
    },
    playMusic() {
      if (
        this.rankings[this.fIndex].name != "MV榜" ||
        this.rankings[this.fIndex].list[this.sIndex] != "MV榜"
      ) {
        this.updateHistorySongs(this.songsPageInfo.records[0]);
        this.updateFlog(true);
      }
    },
  },
  mounted() {
    this.obtainRankingList();
  },
};
</script>

<style lang="scss" scoped>
.main_box .box {
  display: flex;
  width: 98%;
  background-color: #f9f9f9;
  margin: auto;
  .left {
    flex: 2;
    border-right: 0.0125rem solid #c20c0c;
    padding-bottom: 0.625rem;
    .ranking {
      width: 100%;
      height: auto;
      .header {
        width: 95%;
        height: 0.75rem;
        line-height: 0.75rem;
        font-weight: bolder;
        font-size: 0.3rem;
        text-align: center;
        margin: auto;
        margin-top: 0.25rem;
        border-bottom: 0.0125rem solid #c20c0c;
      }
      .bot {
        ul {
          width: 95%;
          margin: auto;
          margin-top: 0.25rem;
          li {
            width: 100%;
            height: 0.625rem;
            line-height: 0.625rem;
            margin-top: 0.125rem;
            text-align: center;
            border-radius: 0.05rem;
          }
          li:hover {
            background-color: white;
          }
        }
      }
    }
  }
  .right {
    flex: 8;
    width: 97%;
    padding-bottom: 0.625rem;
    .header .top {
      width: 100%;
      padding-left: 0.25rem;
      height: 1.5rem;
      line-height: 1.5rem;
      #name {
        font-weight: bolder;
        font-size: 0.45rem;
        color: #000;
      }
      #time {
        margin-left: 0.5rem;
      }
    }
    .header .bot {
      width: 100%;
      padding-left: 0.25rem;
      margin-top: 0.375rem;
      margin-bottom: 0.25rem;
      a {
        display: inline-block;
        width: 1.5rem;
        height: 0.75rem;
        line-height: 0.75rem;
        font-size: 0.2rem;
        text-align: center;
        background-color: white;
        padding-left: 0.0625rem;
        padding-right: 0.0625rem;
        border-radius: 0.05rem;
        transition: 0.5s ease-in;
        margin-right: 0.25rem;
        color: #000;
      }
      a:first-child {
        background-color: #e35555;
      }
      a:hover {
        color: #000;
        background-color: #e35555;
      }
    }

    .mv-list {
      margin-left: 0.125rem;
      background: white;
      padding-bottom: 0.25rem;
      .head {
        font-size: 0.3rem;
        font-weight: bolder;
        color: #000;
        margin-bottom: 0.25rem;
        border-bottom: 0.0125rem solid #c20c0c;
      }
      .card {
        display: flex;
        width: 100%;
        height: 1.5rem;
        line-height: 1.5rem;
        overflow: hidden;
        .left {
          flex: 1;
          text-align: center;
          border: none;
          font-size: 0.2rem;
        }

        .pic {
          flex: 2;
          height: 1.5rem;
          line-height: 1.5rem;
          text-align: center;
          overflow: hidden;
          transition: all 0.3s ease-in;
          cursor: pointer;
          img {
            width: 80%;
            height: 1.25rem;
            border-radius: 0.05rem;
          }
        }
        .pic:hover {
          opacity: 0.8;
        }

        .middle {
          flex: 5;
          height: 1.5rem;
          p {
            height: 0.75rem;
            line-height: 0.75rem;
          }
          .title {
            font-weight: bold;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
          }
        }
        .right {
          flex: 2;
          display: flex;
          a {
            display: block;
            width: 50%;
            height: 1.5rem;
            line-height: 1.5rem;
            text-align: center;
          }
        }
      }
      .card:nth-child(odd) {
        background-color: #fbfbfd;
      }
      .card:hover {
        background-color: #f7e8e9;
      }
      .card:first-child:hover {
        background-color: white;
      }
    }
  }
}
.tab-active {
  background-color: white;
}

.empty-list {
  text-align: center;
  margin: 0.625rem auto;
}
</style>