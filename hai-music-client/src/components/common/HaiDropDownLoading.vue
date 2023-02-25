<template>
  <div class="hai-drop-down-loading">
    <div class="forward">
      <button class="fa fa-angle-down"></button>
    </div>
  </div>
</template>

<script>
export default {
    name:"HaiDropDownLoading"
};
</script>

<style lang="scss" scoped>
.hai-drop-down-loading .forward {
  width: 30%;
  margin: 0.5rem auto 0.125rem auto;
  text-align: center;
  border-top: 1px solid #c20c0c;
  padding-top: 0.25rem;
  font-size: 0.3rem;
  button {
    width: 0.625rem;
    height: 0.625rem;
    line-height: 0.625rem;
    text-align: center;
    border-radius: 50%;
    background-color: white;
  }
  button:hover {
    background-color: #f25b67;
  }
}
</style>