export default {
    namespaced: true,
    state: {
        id:JSON.parse(localStorage.getItem("singerId") || 0),
    },
    actions:{

    },
    mutations: {
        SAVESINGERID(state,value){
            if(value){
                state.id = value;
                localStorage.setItem("singerId",JSON.stringify(value));
            }
        }
    },
    getters:{

    }

}