<template>
  <div class="footer">
    <a href="https://beian.miit.gov.cn" target="_blank">
      <p>豫ICP备2021020486号-1</p>
    </a>
    <p>本网站仅供演示所用，无任何商业用途</p>
    <p>此演示网页中，图片与名称可能不对应，敬请谅解</p>
  </div>
</template>

<script>
export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: 'Footer'
};
</script>

<style lang="scss" scoped>
.footer {
  background-color: #6f6f6f;
  width: 100%;
  height: 5rem;
  padding-top: 0.625rem;
  p {
    text-align: center;
    color: #ddd;
    font-weight: bolder;
    font-size: 0.3rem;
    margin-bottom: 0.25rem;
  }
  a {
    color: #ddd;
    font-weight: bolder;
    font-size: 0.3rem;
    p:hover {
      color: #000;
      text-decoration: underline;
    }
  }
}
</style>