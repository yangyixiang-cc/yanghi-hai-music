<template>
  <div class="main_box">
    <div class="box">
      <div class="header">
        <div class="search">
          <input
            type="search"
            name="search"
            placeholder="搜索你想要知道的歌手"
            v-model="keyword"
          />
          <a
            class="fa fa-search search-btn"
            @click.prevent="goSearchSinger"
          ></a>
        </div>
      </div>
      <div class="context">
        <hai-singer-card
          v-for="item in singerList"
          :key="item.id"
          :singer="item"
        ></hai-singer-card>
        <div class="empty-keyword" v-show="isSearch && singerList.length == 0">
          没有搜索到该歌手
        </div>
      </div>
      <HaiDropDownLoading
        @click.native="obtainSingerPageInfoNext"
        v-show="!isSearch"
      />
    </div>
  </div>
</template>

<script>
import HaiDropDownLoading from "../components/common/HaiDropDownLoading.vue";
import HaiSingerCard from "@/components/common/HaiSingerCard.vue";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "SingerList",
  components: {
    HaiDropDownLoading,
    HaiSingerCard,
  },
  data() {
    return {
      keyword: "",
      current: 1,
      pages: 0,
      singerList: [],
      isSearch: false,
    };
  },
  watch: {
    keyword(nVue) {
      if (nVue == "") {
        this.isSearch = false;
        this.singerList = [];
        this.obtainSingerPageInfo();
      } else {
        this.isSearch = true;
      }
    },
  },
  methods: {
    obtainSingerPageInfo(pageNum = 1) {
      this.axios
        .get("/singer/page", {
          params: {
            pageNum: pageNum,
            num: 20,
          },
        })
        .then((response) => {
          if (response.data.code == 1) {
            this.current = response.data.data.current;
            this.pages = response.data.data.pages;
            this.singerList = [
              ...this.singerList,
              ...response.data.data.records,
            ];
          }
        });
    },
    obtainSingerPageInfoNext() {
      if (this.current < this.pages) {
        this.obtainSingerPageInfo(this.current + 1);
      }
    },
    goSearchSinger() {
      if (this.keyword == "") return;
      this.axios
        .get("/singer/search", {
          params: {
            keyword: this.keyword,
          },
        })
        .then((response) => {
          if (response.data.code == 1) {
            this.singerList = response.data.data;
          }
        });
    },
  },
  mounted() {
    this.obtainSingerPageInfo();
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
    height: 1.25rem;
    line-height: 1.25rem;
    text-align: center;
    .search {
      color: #000;
      input {
        border: none;
        font-size: 0.225rem;
        width: 10rem;
        height: 1rem;
        border-radius: 0.05rem;
        text-align: left;
        background-color: white;
        padding-left: 0.375rem;
        padding-right: 0.125rem;
        vertical-align: baseline;
      }
      .search-btn {
        width: 1.5rem;
        height: 1rem;
        line-height: 1rem;
        margin-left: 0.625rem;
        text-align: center;
        vertical-align: baseline;
        color: #000;
        border-radius: 0.05rem;
        background-color: #f25b67;
        transition-duration: 0.4s;
      }
      .search-btn:hover {
        color: #000;
        opacity: 0.8;
      }
    }
  }
  .context {
    display: flex;
    flex-wrap: wrap;
    justify-content: left;
    width: 98%;
    height: auto;
    border-radius: 0.1rem;
    margin: auto;
    margin-top: 0.5rem;
    padding: 0.125rem;
    padding-left: 0.6875rem;
    padding-top: 0.25rem;
    .empty-keyword {
      margin: 0.625rem auto;
      padding-right: 0.6875rem;
      text-align: center;
    }
  }
}
</style>