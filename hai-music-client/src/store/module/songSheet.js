export default {
    namespaced: true,
    state: {
        id:JSON.parse(localStorage.getItem("songSheetId") || 0),
        userId:JSON.parse(JSON.stringify(localStorage.getItem("userIdBySongSheet")) || 0)
    },
    actions:{

    },
    mutations: {
        SAVESONGSHEETID(state,value){
            if(value){
                state.id = value;
                localStorage.setItem("songSheetId",JSON.stringify(value));
            }
        },
        SAVEUSERID(state,value){
            state.userId = value;
            localStorage.setItem("userIdBySongSheet",JSON.stringify(value));
        }
    },
    getters:{

    }

}