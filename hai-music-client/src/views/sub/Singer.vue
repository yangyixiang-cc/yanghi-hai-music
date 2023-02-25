<template>
  <div class="main_box">
    <div class="box">
      <div class="top">
        <div class="left">
          <img :src="singerInfo.pic" :alt="singerInfo.name" />
        </div>
        <div class="right">
          <div class="singer_name">{{ singerInfo.name }}</div>
          <div class="country">籍贯：{{ singerInfo.location }}</div>
          <div class="birth">
            出生时间：{{ singerInfo.birth | timeFormat((str = "YYYY-MM-DD")) }}
          </div>
          <div class="introduction">
            {{ singerInfo.introduction }}
          </div>
          <div class="tab">
            <a>单曲 {{ singerInfo.songVolume }}</a
            >|
            <a>MV {{ singerInfo.mvVolume }}</a>
          </div>
          <div class="bot">
            <a class="fa fa-play" @click.prevent="playMusic"></a>
            <a>关注 {{ singerInfo.supportVolume | formatNumber }}</a>
            <a>分享 {{ singerInfo.shareVolume | formatNumber }}</a>
          </div>
        </div>
      </div>
      <div class="middle">
        <div class="left">
          <div class="header">
            <span>歌曲列表</span>
            <!-- <div>
              <button>热门</button
              ><button style="background-color: #e35555">全部</button>
            </div> -->
          </div>
          <!-- 歌曲列表 -->
          <song-list
            :songsPageInfo="songsPageInfo"
            eventName="obtainSongsPageInfo"
          ></song-list>
        </div>
        <div class="right">
          <div class="header">相似歌手</div>
          <div class="silmar">
            <div v-for="item in silmarList" :key="item.id">
              <img :src="item.pic" alt="歌手" />
              <a>{{ item.name }}</a>
            </div>
          </div>
          <HaiAd :url="require('@/assets/images/ad5.jpg')"></HaiAd>
        </div>
      </div>
      <div class="header"><span>MV推荐</span></div>
      <div class="bottom">
        <div v-for="item in mvList" :key="item.id">
          <a @click.prevent="goVideoDetail(item.id)"
            ><img :src="item.pic" alt="mv"
          /></a>
          <div class="bot">
            <div class="title">
              <a @click.prevent="goVideoDetail(item.id)">{{ item.title }}</a>
            </div>
            <div class="name">
              {{ item.introduction }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SongList from "@/components/common/SongList.vue";
import HaiAd from "@/components/common/HaiAd.vue";
import { mapState, mapMutations } from "vuex";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Singer",
  components: { SongList, HaiAd },
  data() {
    return {
      singerInfo: {},
      //歌曲列表分页数据
      songsPageInfo: {},
      //相似歌手列表
      silmarList: [],
      //MV推荐列表
      mvList: [],
      location:''
    };
  },
  computed: {
    ...mapState("singerOptions", {
      singerId: "id",
    }),
  },
  methods: {
    ...mapMutations("singerOptions", {
      saveSingerId: "SAVESINGERID",
    }),
    ...mapMutations("playOptions", {
      updateHistorySongs: "UPDATEHISTORYSONGS",
      updateFlog: "UPDATEFLOG",
    }),
    //根据歌手id获取歌手信息
    async obtainSingerInfo() {
      const { data: res } = await this.axios.get("/singer/" + this.singerId);
      if (res.code == 1) {
        this.singerInfo = res.data;
        this.location = res.data.location;
      }
    },
    //根据歌单id获取对应的歌曲分页数据
    async obtainSongsPage(args) {
      let pageNum = args.pageNum;
      const { data: res } = await this.axios.get("/song/songs", {
        params: {
          id: this.singerId,
          pageNum: pageNum,
          num: 6,
        },
      });
      if (res.code == 1) {
        this.songsPageInfo = res.data;
      }
    },
    async obtainRecommendMV() {
      const { data: res } = await this.axios.get("/video/recommend/style", {
        params: {
          num: 8,
          style: "",
        },
      });
      if (res.code == 1) {
        this.mvList = res.data;
      }
    },
    obtainSlimarSingerList() {
      this.axios
        .get("/singer/silmar_singer", {
          params: {
            num: 6,
            location: this.location
          },
        })
        .then((res) => {
          if (res.data.code == 1) {
            this.silmarList = res.data.data;
          }
        });
    },
    playMusic() {
      this.updateHistorySongs(this.songsPageInfo.records[0]);
      this.updateFlog(true);
    },
    goVideoDetail(id) {
      this.$router.push({
        name: "videoDetail",
        params: {
          id: id,
        },
      });
    },
  },
  created() {
    this.saveSingerId(this.$route.params.id);
  },
  mounted() {
    this.obtainSingerInfo();
    this.obtainSongsPage({
      pageNum: 1,
    });
    this.obtainSlimarSingerList();
    this.obtainRecommendMV();
    this.$bus.$on("obtainSongsPageInfo", this.obtainSongsPage);
  },
  beforeDestroy() {
    this.$bus.$off("obtainSongsPageInfo");
  },
};
</script>

<style lang="scss" scoped>
.main_box .box {
  width: 98%;
  height: auto;
  margin: auto;
  padding-top: 0.25rem;
  padding-bottom: 0.375rem;
}

.main_box .box .top {
  display: flex;
  width: 100%;
  height: auto;
  border-radius: 0.05rem;
  overflow: hidden;
  background-color: white;
  margin-bottom: 0.375rem;
  padding-top: 0.25rem;
  padding-bottom: 0.25rem;
}

.main_box .box .top > .left {
  flex: 2;
  padding-left: 1rem;
}

.main_box .box .top > .left img {
  width: 3.75rem;
  height: 3.75rem;
  border-radius: 50%;
}

.main_box .box .top > .right {
  flex: 8;
  padding-left: 0.625rem;
}

.main_box .box .top > .right div {
  width: 100%;
  height: 0.625rem;
  line-height: 0.625rem;
  font-size: 0.2rem;
}

.main_box .box .top > .right .singer_name {
  font-size: 0.45rem;
  color: #000;
  font-weight: bold;
  margin-top: 0.625rem;
}

.main_box .box .top > .right .introduction {
  width: 100%;
  height: 2.25rem;
  line-height: 0.5rem;
  border-left: 0.05rem solid #f7e8e9;
  padding-left: 0.25rem;
  padding-right: 0.25rem;
  overflow: auto;
  overflow-y: scroll;
  cursor: pointer;
}

.main_box .box .top > .right .introduction::-webkit-scrollbar {
  width: 0.05rem;
  height: 0.05rem;
  scrollbar-arrow-color: red;
}
.main_box .box .top > .right .introduction::-webkit-scrollbar-thumb {
  border-radius: 0.0625rem;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: rgba(0, 0, 0, 0.2);
  scrollbar-arrow-color: red;
}
.main_box .box .top > .right .introduction::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 0.0625rem rgba(0, 0, 0, 0.2);
  border-radius: 0;
  background: #f7e8e9;
}

.main_box .box .top > .right .tab a {
  display: inline-block;
  width: 1.25rem;
  text-align: center;
  font-size: 0.25rem;
}

.main_box .box .top > .right .tab a:first-child {
  text-align: left;
}

.main_box .box .top > .right .bot {
  margin-top: 0.125rem;
}

.main_box .box .top > .right .bot a {
  display: inline-block;
  height: 0.625rem;
  line-height: 0.625rem;
  padding-left: 0.1875rem;
  padding-right: 0.1875rem;
  background-color: #f7e8e9;
  font-size: 0.2rem;
  border-radius: 0.05rem;
  margin-left: 0.125rem;
  color: #000;
}

.main_box .box .top > .right .bot a:first-child {
  margin-left: 0;
  padding-left: 0.375rem;
  padding-right: 0.375rem;
  background-color: #e35555;
}

.main_box .box .top > .right .bot a:hover {
  color: #000;
  background-color: #e35555;
}

.main_box .box .middle {
  display: flex;
  width: 100%;
  height: auto;
}

.main_box .box .middle .left {
  flex: 7;
  .header {
    width: 100%;
    height: 0.75rem;
    color: #000;
    display: flex;
    justify-content: space-between;
    padding-left: 0.125rem;
    padding-right: 0.125rem;
    span {
      font-size: 0.3rem;
      font-weight: bold;
    }
    div {
      button {
        width: 1.25rem;
        height: 0.5rem;
        line-height: 0.5rem;
        margin-left: 0.125rem;
        border-radius: 0.05rem;
        background-color: white;
        transition: 0.2s ease-in;
      }
      button:hover {
        background-color: #e35555;
      }
    }
  }
}

.main_box .box .bottom {
  display: flex;
  flex-wrap: wrap;
  background-color: white;
  border-radius: 0.05rem;
  padding-top: 0.25rem;
}

.main_box .box .bottom > div {
  flex: 20%;
  margin: 0.125rem;
}

.main_box .box .bottom > div img {
  width: 100%;
  height: 2rem;
  border-radius: 0.05rem;
}

.main_box .box .bottom > div .bot {
  padding-top: 0.125rem;
  padding-left: 0.125rem;
}

.main_box .box .bottom > div .bot .title {
  color: #000;
  font-weight: bold;
}

.main_box .box > .header {
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 0.625rem;
  line-height: 0.625rem;
  margin-top: 0.25rem;
  span {
    color: #000;
    font-size: 0.3rem;
    font-weight: bold;
  }
  a {
    padding-right: 0.125rem;
  }
}

.main_box .box .middle .right {
  flex: 3;
  border-radius: 0.05rem;
  margin-left: 0.25rem;
  padding-top: 0.25rem;
  > .header {
    width: 97%;
    margin: auto;
    height: 0.5rem;
    line-height: 0.5rem;
    border-bottom: 0.0125rem solid #c20c0c;
    font-size: 0.225rem;
    font-weight: bold;
    color: #000;
  }
  .silmar {
    width: 97%;
    margin: auto;
    margin-top: 0.25rem;
    display: flex;
    flex-wrap: wrap;
    div {
      width: 1.5rem;
      height: auto;
      margin: 0.0625rem;
      text-align: center;
      border-radius: 0.05rem;
      background-color: #ffffff;
      img {
        display: block;
        width: 0.875rem;
        height: 0.875rem;
        margin-top: 0.25rem;
        margin-left: 0.3125rem;
        border-radius: 0.05rem;
      }
      a {
        display: block;
        font-size: 0.175rem;
        margin-top: 0.125rem;
        padding-bottom: 0.125rem;
      }
    }
  }
}
</style>