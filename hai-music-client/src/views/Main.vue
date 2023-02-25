<template>
  <div class="main">
    <!-- 轮播图区域 -->
    <el-carousel
      :interval="3000"
      arrow="always"
      height="6.25rem"
      trigger="click"
    >
      <el-carousel-item v-for="item in swiperList" :key="item.id">
        <el-image
          style="width: 100%; height: 100%"
          :src="item.imgSrc"
          :alt="item.imgAlt"
        >
          <div slot="error" class="image-slot">
            <i class="el-icon-picture-outline"></i>
          </div>
        </el-image>
      </el-carousel-item>
    </el-carousel>
    <div class="main_box">
      <div id="tab1" class="tab1">
        <!-- 左侧栏 -->
        <div class="left">
          <!-- 歌单推荐 -->
          <div class="header">
            <span>歌单推荐</span
            ><a @click.prevent="goMore('sort_list')">查看更多</a>
          </div>
          <div class="song_list_recommend">
            <div v-for="item in gdList" :key="item.id">
              <img
                :src="item.pic"
                alt="歌单推荐"
                @click.prevent="goMore('songSheet', item.id)"
              />
              <p class="title">{{ item.title }}</p>
              <span>播放量：{{ item.playbackVolume | formatNumber }}</span>
            </div>
          </div>
          <HaiAd :url="require('@/assets/images/ad1.jpg')"></HaiAd>
          <!-- 排行榜 -->
          <div class="header">
            <span>排行榜</span
            ><a @click.prevent="goMore('ranking_list')">查看更多</a>
          </div>
          <div class="ranking_recommend">
            <div
              class="ban"
              v-for="(item, index) in songRankingList"
              :key="index"
            >
              <HaiSongsRanking
                :rankingName="rankingNames[index]"
                :songs="item"
              ></HaiSongsRanking>
            </div>
          </div>
          <HaiAd :url="require('@/assets/images/ad6.jpg')"></HaiAd>
          <!-- MV推荐 -->
          <div class="header">
            <span>MV推荐</span
            ><a @click.prevent="goMore('mv_list')">查看更多</a>
          </div>
          <div class="mv_recommend">
            <div v-for="item in mvList" :key="item.id">
              <img
                :src="item.pic"
                alt="MV推荐"
                @click.prevent="goMore('videoDetail',item.id)"
              />
              <p class="title">{{ item.title }}</p>
              <a>
                <p class="introduction">{{ item.introduction }}</p>
              </a>
              <span
                >{{ item.playVolume | formatNumber }}播放
                {{ item.supportVolume | formatNumber }}获赞</span
              >
            </div>
          </div>
          <HaiAd :url="require('@/assets/images/ad9.jpg')"></HaiAd>
        </div>
        <!-- 右侧栏 -->
        <div class="right">
          <!-- 热门歌曲 -->
          <div class="header">
            热门歌曲<a @click.prevent="goMore('sort_list')">查看更多</a>
          </div>
          <div class="popular_songs">
            <ul>
              <li
                v-for="item in recommendSongList"
                :key="item.id"
                @click.prevent="goMore('Song', item.id)"
              >
                <img :src="item.pic" alt="歌曲推荐" />
                <div>
                  <a class="name">{{ item.name }}</a>
                  <p class="introduction">{{ item.introduction }}</p>
                </div>
                <a class="fa fa-chevron-right"></a>
              </li>
            </ul>
          </div>
          <!-- 歌手推荐  -->
          <div class="header">
            歌手推荐<a @click.prevent="goMore('singer_list')">查看更多</a>
          </div>
          <div class="singers_comment">
            <ul>
              <li
                v-for="item in recommendSingerList"
                :key="item.id"
                @click.prevent="goMore('Singer', item.id)"
              >
                <img :src="item.pic" alt="歌手推荐" />
                <div>
                  <a class="name">{{ item.name }}</a>
                  <p class="introduction">{{ item.introduction }}</p>
                </div>
                <a class="fa fa-chevron-right"></a>
              </li>
            </ul>
          </div>
          <HaiAd :url="require('@/assets/images/ad4.jpg')"></HaiAd>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HaiSongsRanking from "../components/main/HaiSongsRanking.vue";
import HaiAd from "../components/common/HaiAd.vue";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Main",
  components: {
    HaiSongsRanking,
    HaiAd,
  },
  data() {
    return {
      swiperList: [
        {
          id: 1,
          imgAlt: "lb1",
          imgSrc: require("@/assets/images/swiper/lbt1.jpg"),
        },
        {
          id: 2,
          imgAlt: "lb2",
          imgSrc: require("@/assets/images/swiper/lbt2.jpg"),
        },
        {
          id: 3,
          imgAlt: "lb3",
          imgSrc: require("@/assets/images/swiper/lbt3.jpg"),
        },
        {
          id: 4,
          imgAlt: "lb4",
          imgSrc: require("@/assets/images/swiper/lbt4.jpg"),
        },
        {
          id: 5,
          imgAlt: "lb5",
          imgSrc: require("@/assets/images/swiper/lbt5.jpg"),
        },
        {
          id: 6,
          imgAlt: "lb6",
          imgSrc: require("@/assets/images/swiper/lbt6.jpg"),
        },
        {
          id: 7,
          imgAlt: "lb7",
          imgSrc: require("@/assets/images/swiper/lbt7.jpg"),
        },
        {
          id: 8,
          imgAlt: "lb8",
          imgSrc: require("@/assets/images/swiper/lbt8.jpg"),
        },
      ],
      gdList: [],
      mvList: [],
      rankingNames: ["热歌榜", "民谣榜", "说唱榜", "国电榜"],
      songRankingList: [],
      recommendSongList: [],
      recommendSingerList: [],
    };
  },
  methods: {
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
    //获取推荐歌单信息
    obtainSongSheetInfo() {
      this.axios.get("/song_sheet/recommend").then((response) => {
        if (response.data.code == 1) {
          this.gdList = response.data.data;
        }
      });
    },
    //获取推荐歌手列表
    obtainSingerListInfo() {
      this.axios.get("/singer/recommend").then((response) => {
        if (response.data.code == 1) {
          this.recommendSingerList = response.data.data;
        }
      });
    },
    //获取推荐歌曲列表
    obtainSongListInfo() {
      this.axios.get("/song/recommend").then((response) => {
        if (response.data.code == 1) {
          this.recommendSongList = response.data.data;
        }
      });
    },
    //获取推荐MV列表
    obtainMvListInfo() {
      this.axios.get("/video/recommend").then((response) => {
        if (response.data.code == 1) {
          this.mvList = response.data.data;
        }
      });
    },
    //获取歌曲排行榜 只展示前4条
    obtainRankingList(style = "", region = "") {
      this.axios
        .get("/song/song_rankings", {
          params: {
            num: 4,
            region: region,
            style: style,
          },
        })
        .then((res) => {
          if (res.data.code == 1) {
            this.songRankingList.push(res.data.data.records);
          }
        });
    },
    obtainSongRankingList() {
      this.obtainRankingList();
      for (let i = 1; i < 4; i++) {
        this.obtainRankingList(
          this.rankingNames[i].substring(0, this.rankingNames[i].length - 1)
        );
      }
    },
  },
  mounted() {
    this.obtainSongSheetInfo();
    this.obtainSingerListInfo();
    this.obtainSongListInfo();
    this.obtainSongRankingList();
    this.obtainMvListInfo();
  },
};
</script>

<style lang="scss" scoped>
.title {
  color: #000;
  font-weight: bold;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

/* 首页中间主体部分，推荐 */
.main_box .tab1 {
  width: 100%;
  height: auto;
  display: flex;
}
/* 左侧栏 */
.tab1 .left {
  flex: 7;
}

/* 右侧栏 */
.tab1 .right {
  flex: 3;
  border-left: 0.0125rem solid #c20c0c;
}

/* 标题 */

.tab1 .left .header {
  width: 90%;
  border-bottom: 1px solid #c20c0c;
  margin-top: 0.5rem;
  margin-left: 0.25rem;
}
.tab1 .left > .header span {
  font-size: 0.3rem;
  cursor: pointer;
  color: #000;
  font-weight: bolder;
}

.tab1 .left > .header a {
  float: right;
  font-size: 0.15rem;
  padding-right: 0.125rem;
}
/* 歌单推荐 */
.tab1 .left .song_list_recommend {
  width: 90%;
  display: flex;
  flex-wrap: wrap;
  margin-top: 0.25rem;
  margin-left: 0.25rem;
}

.tab1 .left .song_list_recommend div {
  flex: 25%;
  padding: 0.0625rem;
  border-radius: 0.1rem;
  overflow: hidden;
  margin: .0625rem;
  cursor: pointer;
  background-color: #ffffff;
  img {
    width: 100%;
    height: 2rem;
    border-radius: 0.05rem;
  }
  p {
    margin-top: 0.0625rem;
    font-weight: bold;
    margin-bottom: 0.0625rem;
  }
  span {
    font-size: 0.15rem;
  }
}


/* 排行榜 */

.tab1 .left .ranking_recommend {
  width: 90%;
  display: flex;
  margin-left: 0.25rem;
  justify-content: space-between;
  padding: 0.25rem 0 0.25rem 0;
}

/* mv推荐 */

.tab1 .left .mv_recommend {
  width: 90%;
  display: flex;
  flex-wrap: wrap;
  margin-top: 0.25rem;
  margin-left: 0.25rem;
}

.tab1 .left .mv_recommend div {
  width: 32%;
  padding: 0.0625rem;
  margin: .0625rem;
  border-radius: 0.05rem;
  background-color: #ffffff;
  .title,
  .introduction {
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  img {
    width: 100%;
    height: 1.75rem;
    border-radius: 0.05rem;
    cursor: pointer;
  }
  p {
    padding-left: 0.0625rem;
    margin-top: 0.0625rem;
    margin-bottom: 0.025rem;
  }
  span {
    padding-left: 0.0625rem;
    font-size: 0.15rem;
  }
}

/* 右侧栏 */
.tab1 .right {
  height: auto;
}
/* 热门歌曲 */
.tab1 .right > .header {
  width: 98%;
  height: 0.5rem;
  line-height: 0.5rem;
  margin-top: 0.375rem;
  margin-left: 0.125rem;
  border-bottom: 0.0125rem solid #c20c0c;
  font-size: 0.25rem;
  color: #000;
  font-weight: bolder;
}

.tab1 .right > .header a {
  float: right;
  padding-right: 0.1875rem;
  font-size: 0.15rem;
  font-weight: normal;
}
.tab1 .right .popular_songs {
  width: 100%;
  .name,
  .introduction {
    width: 3.2rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}

/* 歌手推荐 */

.tab1 .right .singers_comment {
  width: 100%;
}

.tab1 .right ul {
  width: 98%;
  height: auto;
  margin-left: 0.125rem;
  margin-top: 0.5rem;
}
.tab1 .right ul li {
  width: 96%;
  height: 1rem;
  margin-top: 0.125rem;
  margin-bottom: 0.125rem;
  border-radius: 0.05rem;
  position: relative;
  background-color: #ffffff;
}
.tab1 .right ul li > a {
  position: absolute;
  bottom: 0.3125rem;
  right: 0.25rem;
}

.tab1 .right ul li img {
  width: 0.75rem;
  height: 0.75rem;
  margin-top: 0.125rem;
  margin-bottom: 0.125rem;
  margin-left: 0.125rem;
}


.tab1 .right ul li div {
  display: inline-block;
  height: auto;
  position: absolute;
  top: 0.125rem;
  left: 1.5rem;
  font-size: 0.225rem;
  .name,
  .introduction {
    width: 3.2rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}
.tab1 .right ul li div a {
  display: block;
  margin-bottom: 0.0625rem;
  color: #000;
}

.tab1 .right ul li div a:hover {
  color: #c20c0c;
}
</style>