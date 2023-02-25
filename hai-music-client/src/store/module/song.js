export default {
    namespaced: true,
    state: {
        id:JSON.parse(localStorage.getItem("songId") || 0),
        singerId:JSON.parse(JSON.stringify(localStorage.getItem("singerIdBySong")) || 0)
    },
    actions:{

    },
    mutations: {
        SAVESONGID(state,value){
            if(value){
                state.id = value;
                localStorage.setItem("songId",JSON.stringify(value));
            }
        },
        SAVESINGERID(state,value){
            state.userId = value;
            localStorage.setItem("singerIdBySong",JSON.stringify(value));
        }
    },
    getters:{

    }

}