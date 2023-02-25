<template>
  <div class="root">
    <div class="flog fa fa-arrows-v" @click="changeToggle"></div>
    <div class="container" v-show="flog">
      <div id="playbutton" class="play-btn">
        <div class="fa fa-step-backward" @click="prev"></div>
        <div
          class="fa fa-play"
          v-if="musicStatus == 'pause'"
          @click="play"
        ></div>
        <div class="fa fa-pause" v-else @click="pause"></div>
        <div class="fa fa-step-forward" @click="next"></div>
      </div>
      <div class="left">
        <div id="song">
          <div style="padding-left: 100px">
            <span>{{ songName }}</span
            >&nbsp;&nbsp;<span>{{ singer }}</span>
          </div>
          <el-slider
            v-model="currentTime1"
            v-bind:max="duration1"
            @change="replay"
            :show-tooltip="false"
          ></el-slider>
        </div>
        <div id="time">
          <span>{{ currentTime }}</span
          >/<span>{{ duration }}</span>
        </div>
        <div>
          <audio :src="musicUrl" autoplay></audio>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState, mapMutations } from "vuex";
export default {
  name: "PlayMusic",
  props: ["musicUrl", "singer", "songName", "status"],
  data() {
    return {
      musicStatus: "",
      currentTime: "00:00",
      duration: "00:00",
      currentTime1: 0,
      duration1: 10,
    };
  },
  watch: {
    musicUrl(val) {
      this.url = val;
      // if (this.musicStatus == "play") {
      //   this.move();
      // }
    },
    status(val) {
      this.musicStatus = val;
      // if (this.musicStatus == "play") {
      //   this.move();
      // }
    },
  },
  methods: {
    ...mapMutations("playOptions", {
      updateFlog: "UPDATEFLOG",
      nextSong:"NEXTSONG",
      prevSong:"PREVSONG"
    }),
    pause() {
      var audio = document.getElementsByTagName("audio")[0];
      audio.pause();
      this.musicStatus = "pause";
      this.formatTime(audio.duration, audio.currentTime);
    },
    play() {
      var audio = document.getElementsByTagName("audio")[0];
      this.musicStatus = "play";
      audio.play();
      this.move();
    },
    move() {
      var audio = document.getElementsByTagName("audio")[0];
      this.formatTime(audio.duration, audio.currentTime);
      setTimeout(this.move, 10);
    },
    replay(time) {
      var audio = document.getElementsByTagName("audio")[0];
      audio.currentTime = time;
      this.musicStatus = "play";
      audio.play();
    },
    prev(){
      this.prevSong();
    },
    next(){
      this.nextSong();
    },
    formatTime(duration, currentTime) {
      this.duration1 = duration;
      this.currentTime1 = currentTime;
      var currentSecond = Math.floor(currentTime % 60);
      var currentMinute = Math.floor(currentTime / 60);
      if (currentSecond < 10) {
        currentSecond = "0" + currentSecond;
      }
      this.currentTime = currentMinute + ":" + currentSecond;
      var durationSecond = Math.floor(duration % 60);
      var durationMinute = Math.floor(duration / 60);
      if (durationSecond < 10) {
        durationSecond = "0" + durationSecond;
      }
      this.duration = durationMinute + ":" + durationSecond;
    },
    changeToggle() {
      this.updateFlog();
    },
  },
  computed: {
    ...mapState("playOptions", ["flog"]),
  },
  mounted() {
    this.move();
  },
};
</script>

<style lang="scss" scoped>
.root {
  position: fixed;
  bottom: 0rem;
  width: 100%;
  z-index: 99;
  background-color: white;
  border-top: 0.0125rem solid #e0e0e0;
}
.container {
  width: 100%;
  display: flex;
  width: 100%;
  height: 1.25rem;
  margin: auto;
}
.flog {
  position: absolute;
  right: 0.2rem;
  top: -0.5rem;
  width: 0.5rem;
  height: 0.5rem;
  line-height: 0.5rem;
  font-size: 0.2rem;
  text-align: center;
  cursor: pointer;
  background-color: #ffffff;
  border-radius: 0.125rem 0.125rem 0 0;
  border-top: 0.0125rem solid #e0e0e0;
  border-left: 0.0125rem solid #e0e0e0;
  border-right: 0.0125rem solid #e0e0e0;
}
.play-btn {
  display: flex;
  flex: 2;
  justify-content: space-around;
  width: 1.25rem;
  margin-left: 0.25rem;
  div{
    cursor: pointer;
  }
}
#playbutton,
#mycanvas,
#time,
#song {
  float: left;
}
#playbutton {
  margin-top: 0.5rem;
}
#time {
  margin-top: 0.625rem;
  margin-left: 0.375rem;
}
#song {
  width: 80%;

  margin-top: 0.25rem;
}

.left {
  flex: 8;
}
.el-slider {
  margin-left: 0.375rem;
}
.el-slider__bar {
  background-color: #707070 !important;
}
.el-slider__button {
  border: 0.05rem solid #2c2c2c !important;
}
</style>
