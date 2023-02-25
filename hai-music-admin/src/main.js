import Vue from 'vue'
import App from './App.vue'
// 导入axios
import axios from 'axios'
import VueAxios from 'vue-axios'
Vue.use(VueAxios, axios)
axios.defaults.baseURL = "http://localhost:8080/api";
// 导入vuex
import store from './store/index'
// 导入vue-router
import VueRouter from 'vue-router'
import router from './router/router'
Vue.use(VueRouter)

// 引入font-awesome
import 'font-awesome/css/font-awesome.min.css'
import {
  Carousel,
  CarouselItem,
  Image,
  Empty,
  Slider
} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.component(Carousel.name, Carousel);
Vue.component(CarouselItem.name, CarouselItem);
Vue.component(Image.name, Image);
Vue.component(Empty.name, Empty);
Vue.component(Slider.name,Slider);
Vue.config.productionTip = false
//导入animate.css
import 'animate.css';
import dayjs from "dayjs";
Vue.filter('splitStr', function (value,len=10) {
  if (value.length <= len) {
    return value;
  }
  return value.slice(0, len) + "...";
});
Vue.filter('formatNumber', function (value) {
  let vNum = parseInt(value);
  if (vNum >= 10000) {
    return (
      parseInt(vNum / 10000) + "." + parseInt((vNum % 10000) / 1000) + "W"
    );
  }
  return value;
});

Vue.filter('timeFormat',function(value, str = "YYYY-MM-DD HH:mm:ss") {
  //形参默认值
  return dayjs(value).format(str);
});

new Vue({
  render: h => h(App),
  store, //挂载 store 应用于所有组件实例
  router: router, //挂载router
  beforeCreate() {
    Vue.prototype.$bus = this; //安装全局事件总线
  }
}).$mount('#app')