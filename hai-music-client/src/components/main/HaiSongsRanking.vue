<template>
  <div class="songs-ranking">
    <a @click.prevent="goRankingList">
      <div class="header">{{ rankingName }}</div>
    </a>

    <ul>
      <li v-for="(item, index) in songs" :key="item.id" @click="goSong(item.id)">
        <div class="left">{{ index + 1 }}</div>
        <div class="right">
          <div class="name">{{ item.name }}</div>
          <div class="singer-name">{{ item.singerName }}</div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "HaiSongsRanking",
  props: ["rankingName", "songs"],
  methods: {
    goRankingList() {
      this.$router.push({
        name: "ranking_list",
      });
    },
    goSong(id) {
       this.$router.push({
          name: "Song",
          params: {
            id: id,
          },
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.songs-ranking {
  padding: 0 0.375rem 0 0.375rem;
  border-radius: 0.1rem;
  overflow: hidden;
  background-color: white;
  margin-left: 0.0625rem;
  text-align: center;
  a {
    display: block;
  }
  .header {
    width: 2rem;
    height: 0.625rem;
    font-size: 0.3rem;
    text-align: center !important;
    margin: auto;
    margin-top: 0.375rem;
  }
  ul {
    width: 100%;
    text-align: center;
    margin-top: 0.25rem;
    padding: auto .0625rem;
    padding-bottom: 0.625rem;
    overflow: hidden;
    li {
      display: flex;
      width: 2rem;
      height: auto;
      margin: 0.125rem auto 0.0625rem auto;
      cursor: pointer;
      border-radius: 0.1rem;
      position: relative;
      .left {
        flex: 1;
        width: 100%;
        text-align: left;
      }
      .right {
        flex: 9;
        width: 100%;
        padding-left: 0.0625rem;
        text-align: left;
        .name,
        .singer-name {
          width: 1.5rem;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
      }
    }
    li:hover {
      background-color: #f7e8e9;
    }
  }
}
</style>