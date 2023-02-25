<template>
  <div class="main_box">
    <div class="box">
      <div class="header">
        <div
          v-for="(item, index) in tab"
          :key="index"
          @click="changeNav(index)"
          :class="tabIndex == index ? 'active-color' : ''"
        >
          {{ item }}
        </div>
      </div>
      <div class="context">
        <div v-show="tabIndex == 0" v-for="item in songSheets" :key="item.id" @click.prevent="goMore('songSheet', item.id)">
          <img :src="item.pic" />
          <div class="text">{{ item.title }}</div>
        </div>
        <div v-show="tabIndex == 1" v-for="item in songs" :key="item.id" @click.prevent="goMore('Song', item.id)">
          <img :src="item.pic" />
          <div class="text">{{ item.name }}</div>
        </div>
        <div v-show="tabIndex == 2" v-for="item in singers" :key="item.id" @click.prevent="goMore('Singer', item.id)">
          <img :src="item.pic" />
          <div class="text">{{ item.name }}</div>
        </div>
        <div v-show="tabIndex == 3" v-for="item in videos" :key="item.id" @click.prevent="goMore('videoDetail', item.id)">
          <img :src="item.pic" />
          <div class="text">{{ item.title }}</div>
        </div>
      </div>
    </div>
     <div class="empty" v-show="tabIndex == 0 && songSheets.length == 0">没有搜索到相关歌单</div>
      <div class="empty" v-show="tabIndex == 1 && songs.length == 0">没有搜索到相关歌曲</div>
      <div class="empty" v-show="tabIndex == 2 && singers.length == 0">没有搜索到相关歌手</div>
      <div class="empty" v-show="tabIndex == 3 && videos.length == 0">没有搜索到相关MV</div>
    <div class="flog" @click="close">关闭</div>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Search",
  props: ["keyword"],
  data() {
    return {
      tab: ["歌单", "歌曲", "歌手", "MV"],
      tabIndex: 0,
      singers: [],
      songSheets: [],
      videos: [],
      songs: [],
    };
  },
  watch: {
    keyword(nval) {
      if (nval != "") {
        this.goSearch(nval);
      }
    },
  },
  methods: {
    changeNav(index) {
      this.tabIndex = index;
    },
    goSearch(keyword) {
      this.axios
        .get("/common/search", {
          params: {
            keyword: keyword,
          },
        })
        .then((response) => {
          if (response.data.code == 1) {
            this.singers = response.data.data.singers;
            this.songs = response.data.data.songs;
            this.songSheets = response.data.data.songSheets;
            this.videos = response.data.data.videos;
          }
        });
    },
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
      this.close();
    },
    close(){
      this.$bus.$emit("closeSearch");
    }
  },
  mounted() {},
};
</script>

<style lang="scss" scoped>
.main_box {
  position: fixed;
  width: 100%;
  height:auto;
  top: 1rem;
  z-index: 88;
  padding-bottom: .375rem;
}
.box {
  width: 80%;
  margin: auto;
}
.header {
  display: flex;
  width: 100%;
  justify-content: space-evenly;
  margin-top: 0.125rem;
  border-radius: 0.05rem;
  div {
    flex: 25%;
    height: 0.75rem;
    line-height: 0.75rem;
    text-align: center;
    background-color: white;
    cursor: pointer;
  }
}
.context {
  display: flex;
  justify-content: left;
  flex-wrap: wrap;
  div {
    cursor: pointer;
    background-color: white;
    border-radius: 0.05rem;
    padding: 0.125rem;
    margin: 0.1rem;
    text-align: center;
    img {
      width: 2rem;
      height: 2rem;
      border-radius: 0.05rem;
    }
    .text {
      width: 2rem;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
}
.active-color {
  border-bottom: 0.05rem solid #c20c0c;
}
.flog{
  position: absolute;
  top:.125rem;
  right: .125rem;
  width: 1.5rem;
  height: .75rem;
  line-height: .75rem;
  text-align: center;
  border-radius: .1rem;
  color: #000;
  background-color: #f25b67;
}

.empty{
  text-align: center;
  margin: .625rem auto;
}

</style>