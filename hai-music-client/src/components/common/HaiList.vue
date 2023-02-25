<template>
  <div class="hai-list">
    <img :src="list.pic" />
    <p class="title">{{ list.title }}</p>
    <p>{{ list.introduction }}</p>
  </div>
</template>

<script>
export default {
  name: "HaiList",
  props: ["list"],
};
</script>

<style lang="scss" scoped>
.hai-list {
  width: 100%;
  height: auto;
  padding: 0.0625rem;
  margin-bottom: 0.125rem;
  background-color: white;
  border-radius: 0.05rem;
  overflow: hidden;
  img {
    width: 100%;
    height: 2rem;
    border-radius: 0.05rem;
  }
  p {
    width: 3.2rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .title {
    color: #000;
    font-weight: bolder !important;
  }
}
</style>