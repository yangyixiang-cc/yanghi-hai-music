<template>
  <div class="ad">
    <a href="index.html">
      <img :src="url" alt="ad" />
    </a>
  </div>
</template>

<script>
export default {
    name:"HaiAd",
    props:['url']
};
</script>

<style lang="scss" scoped>
.ad {
  width: 98%;
  height: 1.25rem;
  margin-top: 0.5rem;
  margin-left: 0.0625rem;
  margin-bottom: .25rem;
  border-radius: 0.1rem;
  overflow: hidden;
  img {
    width: 100%;
    height: 100%;
  }
}
</style>