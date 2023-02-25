export default {
    namespaced: true,
    state: {
        user:JSON.parse(localStorage.getItem("user")||'{}'),
        userDetails:JSON.parse(localStorage.getItem("userDetails")||'{}')
    },
    actions:{

    },
    mutations: {
        SAVEUSER(state,value){
            state.user = value;
            localStorage.setItem("user",JSON.stringify(state.user));
        },
        REMOVEUSER(state){
            state.user = {};
            localStorage.removeItem("user");
            localStorage.removeItem("userDetails");
        },
        SAVEUSERDETAILS(state,value){
            state.userDetails = value;
            localStorage.setItem("userDetails",JSON.stringify(state.userDetails));
        }
    },
    getters:{

    }

}