<template>
  <div class="paging_bar">
    <button
      class="btn fa fa-angle-left"
      :class="current <= 1 ? 'btn-disabled' : ''"
      @click.prevent="changeSongsPage(current - 1)"
    ></button>
    <a
      v-for="(item, index) in pages"
      :key="index"
      :class="current == item ? 'choose' : ''"
      @click.prevent="changeSongsPage(item)"
      >{{ item }}</a
    >
    <button
      class="btn fa fa-angle-right"
      :class="current >= pages ? 'btn-disabled' : ''"
      @click.prevent="changeSongsPage(current + 1)"
    ></button>
  </div>
</template>

<script>
export default {
  name: "HaiPagination",
  // pages 总页码数 current 当前页码 eventName 改变页码的事件名 由所在页面路由页面提供
  props: ["pages", "current","eventName"],
  methods: {
    changeSongsPage(pageNum) {
      this.$bus.$emit(this.eventName, {
        pageNum:pageNum,
        flog: false
      });
    }
  },
};
</script>

<style lang="scss" scoped>
.paging_bar {
  width: 98%;
  height: 1.25rem;
  margin: auto;
  margin-top: 0.375rem;
  text-align: center;
  a {
    display: inline-block;
    width: 0.5rem;
    height: 0.5rem;
    line-height: 0.5rem;
    margin: auto 0.0625rem auto 0.0625rem;
  }
  .btn {
    margin-top: 0.025rem;
    width: 1rem;
    height: 0.5rem;
    line-height: 0.5rem;
    font-size: 0.3rem;
    background-color: white;
  }
  .btn:hover {
    background-color: #fbfbfd;
  }
  .choose {
    background-color: #e35555;
  }
}
</style>