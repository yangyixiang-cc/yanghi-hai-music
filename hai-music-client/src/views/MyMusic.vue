<template>
  <div class="main_box">
    <div class="box">
      <div class="top">
        <div class="left">
          <img :src="user.pic" alt="user.nickName" />
        </div>
        <div class="right">
          <div class="singer_name">{{ user.nickName }}</div>
          <div class="tab">
            <a href="#">关注 {{ userDetails.followVolume }}</a
            >|
            <a href="#">粉丝 {{ userDetails.supportVolume }}</a>
          </div>
        </div>
      </div>
      <div class="middle">
        <div class="tab">
          <button @click.prevent="changePreTab(1)" :class="preTabStyle[0]">
            我喜欢
          </button>
          <button @click.prevent="changePreTab(2)" :class="preTabStyle[1]">
            我创建的歌单
          </button>
          <button @click.prevent="changePreTab(3)" :class="preTabStyle[2]">
            关注
          </button>
          <button @click.prevent="changePreTab(4)" :class="preTabStyle[3]">
            粉丝
          </button>
        </div>
        <div class="tab_context">
          <div class="context" v-show="preIndex == 1">
            <div class="d_top">
              <a @click.prevent="changeSubTab(1)" :class="subTabStyle[0]"
                >歌曲</a
              >
              <a @click.prevent="changeSubTab(2)" :class="subTabStyle[1]"
                >歌单</a
              >
              <a @click.prevent="changeSubTab(3)" :class="subTabStyle[2]">MV</a>
            </div>
            <div class="d_context">
              <div class="list" v-show="subIndex == 1">
                <song-list :songList="songList"></song-list>
              </div>
              <div class="list" v-show="subIndex == 2">
                <div class="box">
                  <hai-my-card
                    v-for="item in myLikeSongSheetList"
                    :key="item.id"
                    :card="item"
                  ></hai-my-card>
                </div>
                <hai-pagination></hai-pagination>
              </div>
              <div class="list" v-show="subIndex == 3">
                <div class="box">
                  <hai-my-card
                    v-for="item in myLikeMvList"
                    :key="item.id"
                    :card="item"
                  ></hai-my-card>
                </div>
                <hai-pagination></hai-pagination>
              </div>
            </div>
          </div>
          <div class="context" v-show="preIndex == 2">
            <div class="header">
              <button class="fa fa-plus">新建歌单</button>
              <button class="fa fa-upload">导入歌单</button>
              <button class="fa fa-share">分享歌单</button>
            </div>
            <div class="bot">
              <hai-my-card
                v-for="item in myCreateSongSheetList"
                :key="item.id"
                :card="item"
              ></hai-my-card>
            </div>
          </div>
          <div class="context" v-show="preIndex == 3">
            <div class="header">
              <button
                @click.prevent="changeFollowTab(1)"
                :class="followTabStyle[0]"
              >
                歌手
              </button>
              <button
                @click.prevent="changeFollowTab(2)"
                :class="followTabStyle[1]"
              >
                用户
              </button>
            </div>
            <div class="bot" id="bot" v-show="followIndex == 1">
              <hai-singer-card
                v-for="item in myFollowSingerList"
                :key="item.id"
                :singer="item"
              ></hai-singer-card>
            </div>
            <div class="bot" id="bot" v-show="followIndex == 2">
              <hai-singer-card
                v-for="item in myFollowUserList"
                :key="item.id"
                :singer="item"
              ></hai-singer-card>
            </div>
            <hai-pagination
              v-if="
                (followIndex == 1 && myFollowSingerList.length >= 8) ||
                (followIndex == 2 && myFollowUserList.length >= 8)
              "
            ></hai-pagination>
          </div>
          <div class="context" v-show="preIndex == 4">
            <div class="bot">
              <hai-singer-card
                v-for="item in myFansList"
                :key="item.id"
                :singer="item"
              ></hai-singer-card>
            </div>
            <hai-pagination></hai-pagination>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SongList from "@/components/common/SongList.vue";
import HaiPagination from "@/components/common/HaiPagination.vue";
import HaiMyCard from "@/components/common/HaiMyCard.vue";
import HaiSingerCard from "@/components/common/HaiSingerCard.vue";
import { mapState, mapMutations } from "vuex";
export default {
  name: "MyMusic",
  components: {
    SongList,
    HaiPagination,
    HaiSingerCard,
    HaiMyCard,
  },
  data() {
    return {
      //父tab栏切换
      preIndex: 1,
      //父tab栏激活的样式
      preTabStyle: [
        {
          tab_change: true,
        },
        {
          tab_change: false,
        },
        {
          tab_change: false,
        },
        {
          tab_change: false,
        },
      ],
      //子tab栏切换
      subIndex: 1,
      //子tab栏激活的样式
      subTabStyle: [
        {
          "active-color": true,
        },
        {
          "active-color": false,
        },
        {
          "active-color": false,
        },
      ],
      //关注tab栏切换
      followIndex: 1,
      //子tab栏激活的样式
      followTabStyle: [
        {
          "active-btn-color": true,
        },
        {
          "active-btn-color": false,
        },
      ],
      //歌曲列表
      songList: [],
      //我喜欢的歌单列表
      myLikeSongSheetList: [],
      //我喜欢的MV列表
      myLikeMvList: [],
      //我创建的歌单列表
      myCreateSongSheetList: [],
      //我关注的歌手
      myFollowSingerList: [],
      //我关注的用户
      myFollowUserList: [],
      //我的粉丝列表
      myFansList: [],
    };
  },
  methods: {
    ...mapMutations("userOptions", { saveUserDetails: "SAVEUSERDETAILS" }),
    changePreTab(index) {
      this.preIndex = index;
      this.preTabStyle.forEach((item) => {
        item.tab_change = false;
      });
      this.preTabStyle[index - 1].tab_change = true;
    },
    changeSubTab(index) {
      this.subIndex = index;
      this.subTabStyle.forEach((item) => {
        item["active-color"] = false;
      });
      this.subTabStyle[index - 1]["active-color"] = true;
    },
    changeFollowTab(index) {
      this.followIndex = index;
      this.followTabStyle.forEach((item) => {
        item["active-btn-color"] = false;
      });
      this.followTabStyle[index - 1]["active-btn-color"] = true;
    },
  },
  computed: {
    ...mapState("userOptions", ["user", "userDetails"]),
  },
  mounted() {
    this.axios
      .get("/user/userDetails", {
        params: {
          userId: this.user.id,
        },
      })
      .then((response) => {
        if (response.data.code == -1) {
          console.log(response.data.msg);
        } else {
          this.saveUserDetails(response.data.data);
        }
      });
  },
};
</script>

<style lang="scss" scoped>
.tab_change {
  border-bottom: 0.0125rem solid #c20c0c;
}

.active-color {
  color: #c20c0c;
}

.active-btn-color {
  background-color: #e35555 !important;
}

.title {
  font-weight: bolder;
}

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
  height: 5rem;
  line-height: 5rem;
  border-radius: 0.05rem;
  overflow: hidden;
  background-color: white;
  margin-bottom: 0.375rem;
  border-bottom: 0.0125rem solid #c20c0c;
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
  height: 1rem;
  line-height: 1rem;
  font-size: 0.2rem;
}

.main_box .box .top > .right .singer_name {
  font-size: 0.45rem;
  color: #000;
  font-weight: bold;
  margin-top: 0.625rem;
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

.main_box .box .middle {
  width: 100%;
  height: auto;
}

.main_box .box .middle .tab {
  width: 100%;
  height: 1rem;
  line-height: 1rem;
  background-color: white;
}

.main_box .box .middle .tab button {
  width: auto;
  height: 1rem;
  font-size: 0.25rem;
  font-weight: bolder;
  padding-left: 0.1875rem;
  padding-right: 0.1875rem;
  background-color: white;
}

.main_box .box .middle .tab_context {
  width: 100%;
  height: auto;
  background-color: white;
  padding: 0.125rem;
}

.main_box .box .middle .tab_context .context:nth-child(1) .d_top {
  width: 100%;
  height: 0.875rem;
  line-height: 0.875rem;
  padding-left: 0.125rem;
}

.main_box .box .middle .tab_context .context:nth-child(1) .d_top a {
  display: inline-block;
  width: 1.5rem;
  height: 0.625rem;
  line-height: 0.625rem;
  font-size: 0.15rem;
}

.main_box .box .middle .tab_context .context:nth-child(1) .d_context {
  width: 100%;
}

.main_box
  .box
  .middle
  .tab_context
  .context:nth-child(1)
  .d_context
  .list
  .box {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 0.375rem;
  background-color: white;
}

.main_box .box .middle .tab_context .context:nth-child(2) {
  height: 12.5rem;
}

.main_box .box .middle .tab_context .context:nth-child(2) .header {
  width: 100%;
  height: 1rem;
  line-height: 1rem;
}

.main_box .box .middle .tab_context .context:nth-child(2) .header button {
  width: 1.5rem;
  height: 0.625rem;
  line-height: 0.625rem;
  text-align: center;
  background-color: #f7e8e9;
  margin-right: 0.25rem;
  border-radius: 0.05rem;
}

.main_box .box .middle .tab_context .context:nth-child(2) .header button:hover {
  background-color: #e35555;
}

.main_box .box .middle .tab_context .context:nth-child(2) .bot {
  display: flex;
  flex-wrap: wrap;
  width: 100%;
}

.main_box .box .middle .tab_context .context:nth-child(3) {
  height: 12.5rem;
}

.main_box .box .middle .tab_context .context:nth-child(3) .header {
  width: 100%;
  height: 1rem;
  line-height: 1rem;
}

.main_box .box .middle .tab_context .context:nth-child(3) .header button {
  width: 1.5rem;
  height: 0.625rem;
  line-height: 0.625rem;
  text-align: center;
  background-color: #f7e8e9;
  margin-right: 0.25rem;
  border-radius: 0.05rem;
}

.main_box .box .middle .tab_context .context:nth-child(3) .header button:hover {
  background-color: #e35555;
}

.main_box .box .middle .tab_context .context:nth-child(3) .bot {
  display: flex;
  flex-wrap: wrap;
  width: 100%;
}

.main_box .box .middle .tab_context .context:nth-child(4) {
  height: 12.5rem;
}

.main_box .box .middle .tab_context .context:nth-child(4) .bot {
  display: flex;
  flex-wrap: wrap;
  width: 100%;
}
</style>