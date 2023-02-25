<template>
  <div class="hai-card">
    <a href="#"><img :src="card.imgSrc" :alt="card.imgAlt" /></a>
    <a href="#">
      <p v-text="card.title"></p>
    </a>
    <p v-text="card.content"></p>
  </div>
</template>

<script>
export default {
    name:"HaiCard",
    props:['card'],
};
</script>

<style lang="scss" scoped>
.hai-card {
  width: 100%;
  margin-bottom: 0.25rem;
  background-color: white;
  padding: 0.0625rem;
  a:first-child {
    transition: all 0.3s ease-in;
  }

  a:first-child:hover {
    opacity: 0.8;
  }
  a>p{
    color: #000;
    font-weight: bolder;
  }
  a > img {
    width: 100%;
    height: 1.75rem;
  }

  > a {
    color: #000;
  }

  > a:hover {
    color: #c20c0c;
  }
}
</style>