<template>
  <div class="nav" id="nav">
    <div class="sub-nav">
      <a
        v-for="(item, index) in nav"
        :key="index"
        @click.prevent="changeNav(item.name, index)"
        :class="item.activeStyle"
        >{{ item.text }}</a
      >
    </div>
  </div>
</template>

<script>
export default {
  name: "SubHeader",
  data() {
    return {
      nav: [
        {
          text: "推荐",
          name: "main",
          activeStyle: {
            "active-red": true,
          },
        },
        {
          text: "排行榜",
          name: "ranking_list",
          activeStyle: {
            "active-red": false,
          },
        },
        {
          text: "分类歌单",
          name: "sort_list",
          activeStyle: {
            "active-red": false,
          },
        },
        {
          text: "MV",
          name: "mv_list",
          activeStyle: {
            "active-red": false,
          },
        },
        {
          text: "歌手",
          name: "singer_list",
          activeStyle: {
            "active-red": false,
          },
        },
        // {
        //   text: "关于作者",
        //   name: "about",
        //   activeStyle: {
        //     "active-red": false,
        //   },
        // },
      ],
    };
  },
  methods: {
    changeNav(name, index = 0) {
      this.nav.forEach((item) => {
        item.activeStyle["active-red"] = false;
      });
      this.nav[index].activeStyle["active-red"] = true;
      if (this.$router.name != name) {
        this.$router.push({
          name: name,
        });
      }
    },
  },
  created() {
    this.changeNav("main");
  },
};
</script>

<style lang="scss" scoped>
.nav {
  width: 100%;
  height: 0.5rem;
  background-color: #ffffff;
  border-bottom: 0.0375rem solid white;
  .sub-nav {
    width: 15.5rem;
    height: 0.5rem;
    margin-left: 4rem;
    display: flex;
    a {
      width: auto;
      height: 0.5rem;
      line-height: 0.5rem;
      text-align: center;
      font-size: 0.15rem;
      cursor: pointer;
      padding-left: 0.25rem;
      padding-right: 0.25rem;
    }
  }
}
</style>