export default {
    namespaced: true,
    state: {
        flog:false,
        currentSongId: JSON.parse(localStorage.getItem("currentSongId") || "0"),
        historySongs: JSON.parse(localStorage.getItem("historySongs") || "[]")
    },
    actions: {

    },
    mutations: {
        UPDATEHISTORYSONGS(state, value) {
            let flog = false;
            state.historySongs.forEach((item) => {
                if (item.id == value.id) {
                    flog = true;
                }
            })
            if (!flog) {
                state.historySongs.push(value);
            }
            state.currentSongId = value.id;
            localStorage.setItem("currentSongId", JSON.stringify(state.currentSongId));
            localStorage.setItem("historySongs", JSON.stringify(state.historySongs));
        },
        UPDATEFLOG(state,value){
            if(value==true){
                state.flog = true;
            }else{
                state.flog = !state.flog;
            }
        },
        NEXTSONG(state){
            let index = state.historySongs.findIndex((item) => {
                return item.id == state.currentSongId
            });
            if(index < state.historySongs.length){
                state.currentSongId = state.historySongs[index+1].id;
            }else{
                state.currentSongId = state.historySongs[0].id;
            }
            localStorage.setItem("currentSongId", JSON.stringify(state.currentSongId));
        },
        PREVSONG(state){
            let index = state.historySongs.findIndex((item) => {
                return item.id == state.currentSongId
            });
            if(index > 0){
                state.currentSongId = state.historySongs[index-1].id;
            }else{
                state.currentSongId = state.historySongs[state.historySongs.length-1].id;
            }
            localStorage.setItem("currentSongId", JSON.stringify(state.currentSongId));
        }
    },
    getters: {
        currentSongs(state) {
            let index = state.historySongs.findIndex((item) => {
                return item.id == state.currentSongId
            });
            return state.historySongs[index];
        },

    }

}