<template>
  <div class="main_box">
    <div class="box">
      <div class="header">
        <div>
          <span>区域</span
          ><a
            :class="regionIndex == -1 ? 'tab-active' : ''"
            @click.prevent="changeNav('region', -1)"
            >全部</a
          >
          <a
            v-for="(item, index) in mvNav.region"
            :key="index"
            @click.prevent="changeNav('region', index)"
            :class="regionIndex == index ? 'tab-active' : ''"
            >{{ item }}</a
          >
        </div>
        <div>
          <span>版本</span
          ><a
            :class="editionIndex == -1 ? 'tab-active' : ''"
            @click.prevent="changeNav('edition', -1)"
            >全部</a
          >
          <a
            v-for="(item, index) in mvNav.edition"
            :key="index"
            @click.prevent="changeNav('edition', index)"
            :class="editionIndex == index ? 'tab-active' : ''"
            >{{ item }}</a
          >
        </div>
      </div>
      <div class="context_header">
        <div class="left">{{ headerText }}</div>
        <div class="right">
          <button
            :class="tabIndex == 1 ? 'tab-active' : ''"
            @click.prevent="changePx(1)"
          >
            热门
          </button>
          <button
            :class="tabIndex == 2 ? 'tab-active' : ''"
            @click.prevent="changePx(2)"
          >
            最新
          </button>
        </div>
      </div>
      <div class="context">
        <div v-for="item in filterMvList" :key="item.id">
          <a @click="goVideoDetail(item.id)"
            ><img :src="item.pic" alt="mv"
          /></a>
          <div class="bot">
            <div class="title">
              <a @click="goVideoDetail(item.id)">{{ item.title }}</a>
            </div>
            <div class="name">
              {{ item.introduction }}
            </div>
            <div>
              播放量：{{ item.playVolume | formatNumber }}&nbsp;&nbsp;&nbsp;
              {{ item.updateTime | timeFormat }}
            </div>
          </div>
        </div>
        <div class="empty-mv" v-show="filterMvList.length == 0">
          本栏目暂时还没有MV
        </div>
      </div>
      <hai-drop-down-loading
        @click.native="obtainVideoPageInfoNext"
        v-show="filterMvList.length != 0"
      />
    </div>
  </div>
</template>

<script>
import HaiDropDownLoading from "@/components/common/HaiDropDownLoading.vue";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "MvList",
  components: { HaiDropDownLoading },
  data() {
    return {
      headerText: "全部MV",
      mvNav: {
        region: ["内地", "港台", "欧美", "非洲", "日本", "韩国"],
        edition: ["MV", "现场", "翻唱", "舞蹈", "影视", "综艺", "儿歌"],
      },
      current: 1,
      pages: 0,
      mvList: [],
      tabIndex: 1,
      regionIndex: -1,
      editionIndex: -1,
      filterMvList: [],
    };
  },
  methods: {
    goVideoDetail(id) {
      this.$router.push({
        name: "videoDetail",
        params: {
          id: id,
        },
      });
    },
    obtainVideoPageInfo(pageNum = 1, region = "", edition = "") {
      this.axios
        .get("/video/page", {
          params: {
            pageNum: pageNum,
            num: 16,
            region: region,
            edition: edition,
          },
        })
        .then((response) => {
          if (response.data.code == 1) {
            this.current = response.data.data.current;
            this.pages = response.data.data.pages;
            this.mvList = [...this.mvList, ...response.data.data.records];
            this.filterMvList = this.mvList;
          }
        });
    },
    changePx(tabIndex) {
      this.tabIndex = tabIndex;
      if (tabIndex == 1) {
        this.filterMvList.sort(function (a, b) {
          var value1 = a.playVolume,
            value2 = b.playVolume;
          return value2 - value1;
        });
      } else if (tabIndex == 2) {
        this.filterMvList.sort(function (a, b) {
          var value1 = a.updateTime,
            value2 = b.updateTime;
          return +new Date(value2) - +new Date(value1);
        });
      }
    },
    obtainVideoPageInfoNext() {
      if (this.current < this.pages && this.regionIndex != -1) {
        this.obtainVideoPageInfo(
          this.current + 1,
          this.mvNav.region[this.regionIndex]
        );
      } else if (this.current < this.pages && this.editionIndex != -1) {
        this.obtainVideoPageInfo(
          this.current + 1,
          "",
          this.mvNav.edition[this.regionIndex]
        );
      } else {
        this.obtainVideoPageInfo(this.current + 1);
      }
    },
    changeNav(type, index) {
      this.mvList = [];
      this.filterMvList = [];
      if (type == "region") {
        this.editionIndex = -1;
        this.regionIndex = index;
      } else if (type == "edition") {
        this.regionIndex = -1;
        this.editionIndex = index;
      }
      if (index == -1) {
        this.headerText = "全部MV";
        this.filterMvList = this.mvList;
        this.obtainVideoPageInfo();
        return;
      }
      if (index != -1 && type == "region") {
        this.headerText = "区域 - " + this.mvNav[type][index];
        this.obtainVideoPageInfo(1, this.mvNav[type][index]);
      } else if (index != -1 && type == "edition") {
        this.headerText = "版本 - " + this.mvNav[type][index];
        this.obtainVideoPageInfo(1, "", this.mvNav[type][index]);
      }
    },
  },
  mounted() {
    this.obtainVideoPageInfo();
    this.changePx(1);
  },
};
</script>

<style lang="scss" scoped>
.main_box .box {
  width: 98%;
  background-color: #f9f9f9;
  margin: auto;
  padding-top: 0.625rem;
  padding-bottom: 0.375rem;
  .header {
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
  .context_header {
    width: 98%;
    height: 0.5rem;
    margin: auto;
    margin-top: 1.25rem;
    display: flex;
    justify-content: space-between;
    .left {
      font-size: 0.4rem;
      padding-left: 0.125rem;
      font-weight: bold;
      color: #000;
    }
    .right {
      button {
        width: 1rem;
        height: 0.5rem;
        line-height: 0.5rem;
        margin-left: 0.0625rem;
        margin-right: 0.0625rem;
        background-color: white;
        border-radius: 0.05rem;
        font-size: 0.2rem;
        text-align: center;
      }
    }
  }
  .context {
    display: flex;
    flex-wrap: wrap;
    justify-content: left;
    width: 98%;
    height: auto;
    margin: auto;
    background-color: white;
    border-radius: 0.1rem;
    margin-top: 0.25rem;
    padding: 0.125rem;
    padding-left: 0.175rem;
    padding-top: 0.25rem;
    div {
      width: 5.75rem;
      margin: 0.125rem;
      overflow: hidden;
      a {
        transition: all 0.3s ease-in;
      }
      a:hover {
        opacity: 0.8;
      }
      img {
        width: 100%;
        height: 3rem;
        border-radius: 0.05rem;
      }
      .bot {
        margin-left: 0.05rem;
        margin-top: 0.05rem;
        .title {
          font-weight: bold;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        }
      }
    }
  }
}
.tab-active {
  background-color: #e35555 !important;
}

.empty-mv {
  margin: 1.25rem auto !important;
  text-align: center !important;
  padding-left: -0.05rem;
}
</style>