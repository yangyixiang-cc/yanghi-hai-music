<template>
  <div class="songs_comment">
    <div class="song_header">评论</div>
    <div class="send_message">
      <div class="top">
        <img
          :src="this.user.pic ? this.user.pic : require('@/assets/logout.jpg')"
          :alt="this.user.nickName"
        />
        <div class="text">
          <textarea
            name="text"
            rows="36"
            cols="10"
            placeholder="发送你的评论吧......"
            v-model.lazy="commentContext"
          ></textarea>
        </div>
      </div>
      <div class="bot">
        <button
          :class="
            JSON.stringify(this.user) == '{}' ? 'btn btn-disabled' : 'btn '
          "
          @click="sendComment"
        >
          发送
        </button>
      </div>
    </div>
    <div class="comments" v-if="pageInfo.total != 0">
      <div class="header">
        <b>众多评论</b>
        <span
          ><a
            @click.prevent="clickSort(1)"
            :class="isActive == 1 ? 'btn_red' : ''"
            >热度</a
          >|<a
            @click.prevent="clickSort(2)"
            :class="isActive == 2 ? 'btn_red' : ''"
            >时间</a
          >|<a
            @click.prevent="clickSort(3)"
            :class="isActive == 3 ? 'btn_red' : ''"
            >随机</a
          ></span
        >
      </div>
      <table>
        <tr v-for="(item, index) in commentsInfo" :key="index">
          <template
            v-if="
              index >= pageInfo.size * (pageInfo.current - 1) &&
              index < pageInfo.size * pageInfo.current
            "
          >
            <td>
              <img
                :src="item.user.pic"
                @click="goDetail(item.user.id)"
                class="cursor"
              />
            </td>
            <td>
              <div class="name"><a v-text="item.user.nickName"></a></div>
              <div class="context">{{ item.comment.content }}</div>
              <div class="footer">
                <span class="left">{{
                  item.comment.createTime | timeFormat
                }}</span>
                <span class="right">
                  <a
                    :class="
                      index === isSupport
                        ? 'fa fa-thumbs-up'
                        : 'fa fa-thumbs-o-up'
                    "
                    @click.prevent="supportComment(index)"
                    >({{ item.comment.supportVolume }})</a
                  >
                  |&nbsp;
                  <a class="fa fa-commenting-o"
                    >({{ item.comment.replyVolume }})</a
                  >
                </span>
              </div>
            </td>
          </template>
        </tr>
      </table>
      <!-- 分页条 -->
      <HaiPagination
        v-if="pageInfo.pages > 1"
        :current="pageInfo.current"
        :eventName="eventName"
        :pages="pageInfo.pages"
      ></HaiPagination>
    </div>
    <div class="empty-tip" v-if="pageInfo.total == 0">
      <div class="fa fa-comment-o fa-5x"></div>
      <div class="text">评 论 为 空，快 来 评 论 吧！</div>
    </div>
  </div>
</template>

<script>
import qs from "qs";
import HaiPagination from "./HaiPagination.vue";
import { mapState } from "vuex";
import dayjs from "dayjs";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Comment",
  components: {
    HaiPagination,
  },
  props: ["commentsPageInfo", "eventName"],
  computed: {
    ...mapState("userOptions", ["user"]),
    ...mapState("songSheetOptions", { songSheetId: "id" }),
    ...mapState("songOptions", { songId: "id" }),
    ...mapState("videoOptions", { videoId: "id" }),
  },
  data() {
    return {
      temp: [],
      isActive: 0,
      isSupport: -1,
      pageInfo: {},
      commentsInfo: [],
      commentContext: "",
      addCommentInfo: {},
    };
  },
  watch: {
    commentsPageInfo(nVal) {
      this.temp = nVal;
      this.pageInfo = nVal[nVal.length - 1];
      for (var i = 0; i < nVal.length - 1; i++) {
        this.$set(this.commentsInfo, i, nVal[i]);
      }
    },
    temp(nVal) {
      this.commentsInfo = nVal.slice(0, nVal.length - 1);
    },
  },
  methods: {
    goDetail(id) {
      this.$router.push({
        name: "Singer",
        params: {
          id: id,
        },
      });
    },
    supportComment(index) {
      this.isSupport = index;
    },
    //选项排序
    clickSort(type = 1) {
      let sortObj = this.commentsInfo;
      if (type == 1) {
        this.isActive = 1;
        this.commentsInfo = sortObj.sort(function (a, b) {
          return b["comment"]["supportVolume"] - a["comment"]["supportVolume"];
        });
      }
      if (type == 2) {
        this.isActive = 2;
        this.commentsInfo = sortObj.sort(function (a, b) {
          let date1 = new Date(b.comment.createTime).getTime();
          let date2 = new Date(a.comment.createTime).getTime();
          return date1 - date2;
        });
      }
      if (type == 3) {
        this.isActive = 3;
        this.commentsInfo = sortObj;
      }
    },
    //发送评论
    async sendComment() {
      if (this.commentContext != "") {
        const { data: res } = await this.axios.post(
          "/comments/user",
          qs.stringify(this.addCommentInfo)
        );
        if (res.code == 1) {
          alert("评论成功！");
          this.$bus.$emit(this.eventName, {
            pageNum: -1,
            flog: true,
          });
        } else {
          alert("评论失败！");
        }
      } else {
        alert("请输入评论内容！");
      }
      this.commentContext = "";
    },
  },
  created() {
    if (this.eventName == "obtainSongPageInfo") {
      this.addCommentInfo = {
        userId: this.user.id,
        content: this.commentContext,
        createTime: dayjs(new Date()).format("YYYY-MM-DD HH:mm:ss"),
        songId: this.songId,
      };
    } else if (this.eventName == "obtainSongSheetPageInfo") {
      this.addCommentInfo = {
        userId: this.user.id,
        content: this.commentContext,
        createTime: dayjs(new Date()).format("YYYY-MM-DD HH:mm:ss"),
        songSheetId: this.songSheetId,
      };
    } else if (this.eventName == "obtainVideoPageInfo") {
      this.addCommentInfo = {
        userId: this.user.id,
        content: this.commentContext,
        createTime: dayjs(new Date()).format("YYYY-MM-DD HH:mm:ss"),
        videoId: this.videoId,
      };
    }
  },
  mounted() {
    this.clickSort();
  },
};
</script>

<style lang="scss" scoped>
.songs_comment {
  width: 98%;
  margin: auto;
  padding: auto;
  margin-top: 1rem;
  .song_header {
    width: 100%;
    margin-top: 0.25rem;
    height: 0.625rem;
    line-height: 0.625rem;
    border-bottom: 0.025rem solid #c20c0c;
    font-size: 0.3rem;
    color: #000;
    font-weight: bold;
  }

  .send_message {
    width: 100%;
    height: auto;
    margin-top: 0.25rem;
    overflow: hidden;
    .top {
      display: flex;
      justify-content: space-between;
      margin-bottom: 0.25rem;
      img {
        flex: 1;
        width: 1rem;
        height: 1.25rem;
        border-radius: 0.05rem;
        margin-top: 0.25rem;
        margin-left: 0.25rem;
      }
      .text {
        flex: 9;
        width: auto;
        height: 1.5rem;
        margin-top: 0.25rem;
        margin-left: 0.25rem;
        background-color: beige;
        border-radius: 0.1rem;
        overflow: hidden;
        textarea {
          width: 102%;
          height: 102%;
          text-indent: 1em;
          font-size: 0.25rem;
        }
      }
    }
    .bot {
      display: flex;
      justify-content: right;
      padding-right: 0.375rem;
      .btn {
        width: 1.5rem;
        height: 0.625rem;
        border-radius: 0.1rem;
        background-color: #e35555;
        transition: all 0.3s ease-in;
      }
      .btn:hover {
        opacity: 0.8;
      }
    }
  }
  .comments {
    width: 98%;
    margin: auto;
    padding-top: 0.5rem;
    .header {
      width: 98%;
      height: 0.5rem;
      margin: auto;
      line-height: 0.5rem;
      font-size: 0.225rem;
      color: #000;
      border-bottom: 0.0125rem solid #c20c0c;
      span {
        float: right;
        font-size: 0.2rem;
        a {
          display: inline-block;
          margin-left: 0.0625rem;
        }
      }
    }
    table {
      width: 98%;
      height: auto;
      margin: auto;
      margin-bottom: 0.25rem;
      padding-bottom: .25rem;
      tr {
        display: flex;
        width: 100%;
        line-height: 0.2875rem;
        background-color: #ffffff;
        margin: .125rem auto;
        border-radius: .05rem;
      }
      td {
        text-align: left;
        height: auto;
        padding-bottom: 0.125rem;
      }

      td:first-child {
        flex: 1;
        padding-top: 0.3125rem;
        padding-left: 0.125rem;
        img {
          width: 1rem;
          height: 1rem;
          border-radius: 0.125rem;
        }
        img:hover {
          opacity: 0.8;
        }
      }
      td:last-child {
        flex: 9;
        display: flex;
        flex-direction: column;
        div {
          margin-top: 0.4rem;
        }
        .name {
          color: #000;
          font-weight: bold;
        }
        .footer {
          display: flex;
          justify-content: space-between;
          font-size: 0.15rem;
          padding-right: 0.05rem;
          .right{
            padding-right: .125rem;
          }
        }
      }
    }
  }
}

.empty-tip {
  padding: 0.625rem;
  text-align: center;
  color: #c20c0c;
  font-weight: bold;
  opacity: 0.8;
  .text {
    color: #000;
    margin-top: 0.625rem;
  }
}
</style>