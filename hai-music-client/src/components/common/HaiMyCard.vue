<template>
  <div>
    <a><img :src="card.src" :alt="card.name" /></a>
    <p class="title">{{ card.title }}</p>
    <p class="username">{{ card.name }}</p>
  </div>
</template>

<script>
export default {
  name: "HaiMyCard",
  props: ["card"]
};
</script>

<style lang="scss" scoped>
div {
  flex: 30%;
  height: auto;
  margin: 0.0625rem;
  padding: 0.0625rem;
  border-radius: 0.05rem;
  img {
    width: 100%;
    height: 3.75rem;
    border-radius: 0.05rem;
  }
  p {
    margin-top: 0.125rem;
  }
  .title {
    color: #000;
    font-weight: bolder;
  }

  .username:hover {
    color: #c20c0c;
  }
}

div:hover {
  background-color: rgb(233, 233, 233);
}
</style>