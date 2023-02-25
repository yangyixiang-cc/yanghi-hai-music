<template>
  <div id="app">
    <Main></Main>
  </div>
</template>
<script>
import Main from './views/Main.vue';
export default {
  name: "App",
  components: {
    Main
  },
  data() {
    return {
      
    };
  },
};
</script>

<style lang="scss">
/* 把我们所有标签的内外边距清零 */
* {
  margin: 0;
  padding: 0;
  font-family: "poppin,Tahoma,Arial,\5FAE\8F6F\96C5\9ED1,sans-serif";
  /*css3 盒子模型*/
  box-sizing: border-box;
  font-size: 0.2rem;
}

/*rem自适应 1024-1920*/

@media screen and (max-width: 1024px) {
  html {
    font-size: 42.66px !important;
  }
}

@media screen and (min-width: 1920px) {
  html {
    font-size: 80px !important;
  }
}

/* em 和 i 斜体的文字不倾斜 */

em,
i {
  font-style: normal;
}

/* 去掉li 的小圆点 */

li {
  list-style: none;
}

img {
  /* border 0 照顾低版本浏览器 如果 图片外面包含了链接会有边框的问题 */
  border: 0;
  /* 取消图片底侧有空白缝隙的问题 */
  vertical-align: middle;
}

button {
  /* 当我们鼠标经过button 按钮的时候，鼠标变成小手 */
  cursor: pointer;
}

a {
  color: #666;
  text-decoration: none;
  cursor: pointer;
}

a:hover {
  text-decoration: none;
}
button,
input {
  font-family: Microsoft YaHei, Heiti SC, tahoma, arial, Hiragino Sans GB,
    "\5B8B\4F53", sans-serif;
  border: 0;
  outline: none;
}
.cursor {
  cursor: pointer;
}
//禁用按钮
.btn-disabled {
  pointer-events: none;
}

body {
  -webkit-font-smoothing: antialiased;
  background-color: #f9f9f9;
  font: 0.15rem/1.5 Microsoft YaHei, Heiti SC, tahoma, arial, Hiragino Sans GB,
    "\5B8B\4F53", sans-serif;
  color: #666;
}

body::-webkit-scrollbar {
  width: 0.1rem;
  height: 0.05rem;
  scrollbar-arrow-color: red;
}
body::-webkit-scrollbar-thumb {
  border-radius: 0.0625rem;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: rgba(0, 0, 0, 0.2);
  scrollbar-arrow-color: red;
}
body::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 0.0625rem rgba(0, 0, 0, 0.2);
  border-radius: 0;
  background: #f9f9f9;
}

.hide,
.none {
  display: none;
}

/* 清除浮动 */
.clear {
  clear: both;
}

.clearfix {
  *zoom: 1;
}

.clearfix:after {
  visibility: hidden;
  clear: both;
  display: block;
  content: ".";
  height: 0;
}

a:link,
:visited {
  text-decoration: none;
  color: #000;
}

a:hover {
  color: #c20c0c;
}
</style>
