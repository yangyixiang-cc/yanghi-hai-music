<template>
  <div class="songs-list">
    <ul>
      <li>
        <div class="num"></div>
        <div class="song">歌曲</div>
        <div class="btn"></div>
        <div class="introduction">简介</div>
        <div class="singer_name">歌手</div>
      </li>
      <li v-for="(item, index) in songsPageInfo.records" :key="item.id">
        <div class="num">{{ index + 1 }}</div>
        <div class="song" @click.prevent="goMore('Song', item.id)">
          <img :src="item.pic" alt="歌曲" />
          <span class="song_name"
            ><a>{{ item.name | splitStr(15) }}</a></span
          >
        </div>
        <div class="btn">
          <a class="fa fa-play" @click="playMusic(index)"></a>
          <a class="fa fa-heart-o"></a>
          <a>MV</a>
        </div>
        <div class="introduction">
          {{ item.introduction }}
        </div>
        <div class="singer_name" >
          <a @click.prevent="goMore('Singer', item.singerId)">{{ item.singerName }}</a>
        </div>
      </li>
    </ul>
    <div class="empty-list" v-show="!songsPageInfo.records">
      暂时还没有歌曲哦！
    </div>
    <!-- 分页条 -->
    <HaiPagination
      v-if="songsPageInfo.total > songsPageInfo.size && !flog"
      :pages="songsPageInfo.pages"
      :current="songsPageInfo.current"
      :eventName="eventName"
    ></HaiPagination>
  </div>
</template>

<script>
import HaiPagination from "./HaiPagination.vue";
import { mapMutations } from "vuex";
export default {
  name: "SongsList",
  components: {
    HaiPagination,
  },
  props: ["songsPageInfo", "eventName", "flog"],
  methods:{
     ...mapMutations("playOptions", {
      updateHistorySongs: "UPDATEHISTORYSONGS",
      updateFlog: "UPDATEFLOG",
    }),
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
    playMusic(index){
      this.updateHistorySongs(this.songsPageInfo.records[index]);
      this.updateFlog(true);
    }
  }
};
</script>

<style lang="scss" scoped>
.songs-list {
  width: 98%;
  margin: auto;
  background-color: white;
  border-radius: 0.05rem;
  overflow: hidden;
  ul {
    width: 100%;
    margin-top: 0.25rem;
    li {
      display: flex;
      width: 100%;
      height: 2rem;
      line-height: 2rem;
      margin-bottom: 0.125rem;
      .num {
        flex: 1;
        text-align: center;
      }
      .song {
        flex: 3;
        img {
          width: 1.25rem;
          height: 1.25rem;
          margin-right: 0.25rem;
        }
        .song_name {
          margin-right: 0.125rem;
        }
      }
      .btn {
        flex: 2;
        text-align: center;
        a {
          padding-left: 0.0625rem;
          padding-right: 0.0625rem;
          color: #c20c0c;
          font-size: 0.2rem;
          margin-left: 0.3125rem;
        }
      }
      .introduction {
        flex: 2;
        text-align: center;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .singer_name {
        flex: 2;
        text-align: center;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
    li:nth-child(even) {
      background-color: #fbfbfd;
    }
    li:first-child {
      border-bottom: 0.0125rem solid #c20c0c;
      font-size: 0.3rem;
      font-weight: bolder;
      color: #000;
      height: 1.25rem;
      line-height: 1.25rem;
    }
    li:nth-child(n + 2):hover {
      background-color: #f7e8e9;
    }
  }
}

.empty-list {
  margin: 0.625rem auto;
  text-align: center;
}
</style>