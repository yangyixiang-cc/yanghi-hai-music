<template>
  <div class="singer-card">
    <img :src="singer.pic" alt="歌手" @click="goSingerDetail" class="cursor"/>
    <p class="singer-name">{{singer.name}}</p>
    <p class="singer-fans" v-if="singer.fans">{{singer.fans}}W已关注</p>
    <button class="flog fa fa-check" v-if="singer.flog">已互关</button>
  </div>
</template>

<script>
export default {
  name: "HaiSingerCard",
  props:['singer'],
  methods:{
    goSingerDetail(){
      this.$router.push({
        name:"Singer",
        params:{
          id:this.singer.id,
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.singer-card {
  width: 4rem;
    background-color: #ffffff;
  padding: 0.0625rem;
  margin: .125rem;
  border-radius: 0.05rem;
  text-align: center;
  height: 5rem;

  img {
    width: 2rem;
    height: 2rem;
    border-radius: 50%;
    margin-top: 0.375rem;
    transition: all 0.3s ease-in;
  }

  img:hover {
    opacity: 0.8;
  }

  p {
    margin-top: 0.1875rem;
  }

  .singer-name {
    color: #000;
    font-weight: bolder;
  }

  button {
    width: 2rem;
    height: 0.625rem;
    margin-top: 0.375rem;
    background-color: #e35555;
    border-radius: 0.05rem;
  }
}
</style>