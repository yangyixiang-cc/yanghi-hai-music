<template>
  <div class="main_box">
    <div class="box" id="box">
      <div class="top">
        <div>
          <span>分类</span
          ><a
            :class="mvNavIndex == -1 ? 'tab-active' : ''"
            @click.prevent="changeNav(-1)"
            >全部</a
          >
          <a
            v-for="(item, index) in mvNav"
            :key="index"
            @click.prevent="changeNav(index)"
            :class="mvNavIndex == index ? 'tab-active' : ''"
            >{{ item }}</a
          >
        </div>
      </div>
      <div class="header">{{ headerText }}</div>
      <div class="container">
        <div v-for="item in filterGdList" :key="item.id">
          <a @click.prevent="goGdDetails(item.id)"
            ><img :src="item.pic" alt="歌单推荐"
          /></a>
          <a @click.prevent="goGdDetails(item.id)">
            <p>{{ item.title }}</p>
          </a>
          <span>播放量：{{ item.playbackVolume | formatNumber }}</span>
        </div>
        <div class="empty-sort" v-show="filterGdList.length == 0">
          本栏目暂时还没有歌单
        </div>
      </div>
      <hai-drop-down-loading @click.native="obtainGdPageInfoNext" v-show="filterGdList.length != 0"/>
    </div>
  </div>
</template>
<script>
import HaiDropDownLoading from "../components/common/HaiDropDownLoading.vue";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "SortList",
  components: {
    HaiDropDownLoading,
  },
  data() {
    return {
      headerText: "推荐歌单",
      current: 1,
      pages: 0,
      gdList: [],
      mvNav: ["华语", "粤语", "日韩", "欧美", "BGM", "乡村", "乐器", "轻音乐"],
      filterGdList: [],
      mvNavIndex: -1,
    };
  },
  methods: {
    goGdDetails(id) {
      this.$router.push({
        name: "songSheet",
        params: {
          id: id,
        },
      });
    },
    obtainGdPageInfo(pageNum = 1, style = "") {
      this.axios
        .get("/song_sheet/page", {
          params: {
            pageNum: pageNum,
            num: 12,
            style: style,
          },
        })
        .then((response) => {
          if (response.data.code == 1) {
            this.current = response.data.data.current;
            this.pages = response.data.data.pages;
            this.gdList = [...this.gdList, ...response.data.data.records];
            this.filterGdList = this.gdList;
            this.changePx();
          }
        });
    },
    changePx() {
      this.filterGdList.sort(function (a, b) {
        var value1 = a.playbackVolume,
          value2 = b.playbackVolume;
        return value2 - value1;
      });
    },
    obtainGdPageInfoNext() {
      if (this.current < this.pages && this.headerText == "推荐歌单") {
        this.obtainGdPageInfo(this.current + 1);
      } else if (this.current < this.pages && this.headerText != "推荐歌单") {
        this.obtainGdPageInfo(this.current + 1, this.mvNav[this.mvNavIndex]);
      }
    },
    changeNav(index) {
      this.gdList = [];
      this.filterGdList = [];
      this.mvNavIndex = index;
      if (index == -1) {
        this.headerText = "推荐歌单";
        this.obtainGdPageInfo(1);
        return;
      } else {
        this.headerText = this.mvNav[index];
        this.obtainGdPageInfo(1, this.mvNav[index]);
      }
    },
  },
  mounted() {
    this.obtainGdPageInfo();
  },
};
</script>

<style lang="scss" scoped>
.main_box .box {
  width: 98%;
  height: auto;
  margin: auto;
  padding-top: 0.625rem;
  padding-bottom: 0.375rem;
    background-color: #f9f9f9;
  .top {
    width: 98%;
    height: 3rem;
    margin: auto;
    border-radius: 0.1rem;
    background-color: white;
    padding-top: 0.375rem;
    div {
      width: 100%;
      height: 1rem;
      line-height: 1rem;
      span {
        display: inline-block;
        width: 1rem;
        height: 0.5rem;
        line-height: 0.5rem;
        margin-left: 0.125rem;
        text-align: center;
        border-radius: 0.05rem;
        background-color: #ccc;
        margin-right: 0.25rem;
        font-weight: bolder;
        color: #000;
      }
      a {
        display: inline-block;
        width: 1rem;
        height: 0.5rem;
        line-height: 0.5rem;
        color: #000;
        text-align: center;
        font-size: 0.15rem;
        padding: auto 0.25rem auto 0.25rem;
        background-color: #f7e8e9;
        margin: auto 0.0625rem auto 0.0625rem;
        border-radius: 0.05rem;
      }
      a:hover {
        color: #000;
        background-color: #e35555;
      }
    }
  }
  .header {
    width: 98%;
    height: 1.25rem;
    margin: auto;
    line-height: 1.25rem;
    font-size: 0.4rem;
    font-weight: bold;
    border-bottom: 0.0125rem solid #c20c0c;
    color: #000;
  }
  .container {
    display: flex;
    flex-wrap: wrap;
    justify-content: left;
    width: 98%;
    margin: auto;
    margin-top: 0.25rem;
    padding-top: 0.125rem;
    padding-left: 0.175rem;
    background-color: white;
    border-radius: 0.05rem;
    div {
      width: 5.75rem;
      margin: 0.125rem;
      margin-bottom: 0.25rem;
      a {
        display: block;
      }
      img {
        width: 100%;
        height: 3.75rem;
        margin-bottom: 0.125rem;
        border-radius: 0.05rem;
        overflow: hidden;
        transition: all 0.3s ease-in;
      }
      img:hover {
        opacity: 0.8;
      }
    }
  }
}

.tab-active {
  background-color: #e35555 !important;
}

.empty-sort {
  margin: 1.25rem auto !important;
  text-align: center !important;
  padding-left: -0.05rem;
}
</style>