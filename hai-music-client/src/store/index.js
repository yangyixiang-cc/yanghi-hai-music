import Vue from 'vue'
import Vuex from 'vuex'
import userOptions from './module/user'
import songSheetOptions from './module/songSheet'
import songOptions from './module/song'
import singerOptions from './module/singer'
import videoOptions from './module/video'
import playOptions from './module/play'
Vue.use(Vuex)


export default new Vuex.Store({
    modules:{
        userOptions: userOptions,
        songSheetOptions:songSheetOptions,
        songOptions: songOptions,
        singerOptions:singerOptions,
        videoOptions:videoOptions,
        playOptions:playOptions
    }
})