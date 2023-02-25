export default {
    namespaced: true,
    state: {
        id:JSON.parse(localStorage.getItem("videoId") || 0),
        videoId:JSON.parse(JSON.stringify(localStorage.getItem("userIdByVideo")) || 0)
    },
    actions:{

    },
    mutations: {
        SAVEVIDEOID(state,value){
            if(value){
                state.id = value;
                localStorage.setItem("videoId",JSON.stringify(value));
            }
        },
        SAVEUSERID(state,value){
            state.userId = value;
            localStorage.setItem("userIdByVideo",JSON.stringify(value));
        }
    },
    getters:{

    }

}