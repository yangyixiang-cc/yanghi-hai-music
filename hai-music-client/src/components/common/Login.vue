<template>
  <div class="login" id="login">
    <div id="title" class="login-title">
      {{ header }}
      <span
        ><a
          id="closeBtn"
          class="close-login fa fa-close"
          @click.prevent="closeLogin"
        >
        </a
      ></span>
    </div>
    <!-- <div class="sm_header">扫 码 登 录</div>
    <div class="left">
      <img src="@/assets/images/login.png" alt="登录二维码" />
      <div class="shuffle" style="display: none">
        <a href="javascript:void(0)" class="fa fa-refresh"></a>
      </div>
    </div> -->
    <div class="login-input-content">
      <span class="msg">{{ msg }}</span>
      <form action="#">
        <div class="login-input">
          <label for="info[username]">用户名：</label>
          <input
            type="text"
            placeholder="请输入用户名"
            name="info[username]"
            id="username"
            class="input"
            v-model.lazy="user.username"
          />
        </div>
        <div class="login-input" v-show="flog">
          <label for="info[nickname]">用户昵称：</label>
          <input
            type="text"
            placeholder="请输入昵称"
            name="info[nickname]"
            id="nickname"
            class="input"
            v-model.lazy="user.nickName"
          />
        </div>
        <div class="login-input">
          <label for="info[password]">登录密码：</label>
          <input
            type="password"
            placeholder="请输入登录密码"
            name="info[password]"
            id="password"
            class="input"
            v-model.lazy="user.password"
          />
        </div>
      </form>
    </div>
    <div id="loginBtn" class="login-button">
      <a class="fa fa-check" @click.prevent="toRegister" v-if="flog"></a>
      <a class="fa fa-check" @click.prevent="toLogin" v-else></a>
    </div>
    <div class="register">
      <a @click.prevent="goLogin" v-if="flog">去登录 &gt;</a>
      <a @click.prevent="goRegister" v-else>还未注册，立即去注册 &gt;</a>
    </div>
    <div class="toast" v-show="isRegister">{{ registerText }}</div>
  </div>
</template>

<script>
import qs from "qs";
import { mapMutations } from "vuex";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data() {
    return {
      flog: false,
      isRegister: false,
      msg: "",
      user: {
        username: "",
        password: "",
        nickName: "",
      },
      header: "登 录",
      registerText: "",
    };
  },
  methods: {
    ...mapMutations("userOptions", { saveUser: "SAVEUSER" }),
    closeLogin() {
      this.$bus.$emit("changeClose");
    },
    toLogin() {
      if (this.user.username == "" || this.user.password == "") {
        this.msg = "请输入用户名或密码！";
      } else {
        this.axios
          .post(
            "/user/login",
            qs.stringify({
              username: this.user.username,
              password: this.user.password,
            })
          )
          .then((response) => {
            if (response.data.code == -1) {
              this.msg = response.data.msg;
            } else {
              this.saveUser(response.data.data);
              this.closeLogin();
              this.msg = "";
            }
            this.user.username = "";
            this.user.password = "";
          });
      }
      this.msg = "";
    },
    toRegister() {
      let prep = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{5,18}$/;
      let uprep = /^[A-Za-z]{5,18}$/;
      let nrep = /^[\u4e00-\u9fa5]{4,8}$/;
      if (
        this.user.username == "" ||
        this.user.password == "" ||
        this.user.nickName == ""
      ) {
        this.registerText = "请将每一项填写完整！";
        this.isRegister = true;
        setTimeout(() => {
          this.isRegister = false;
        }, 500);
        return;
      }
      if (!uprep.test(this.user.username)) {
        this.registerText =
          "用户名必须是英文字母，不能使用特殊字符，长度在6-18之间";
        this.isRegister = true;
        setTimeout(() => {
          this.isRegister = false;
        }, 500);
        return;
      }

      if (!prep.test(this.user.password)) {
        this.registerText =
          "密码必须包含大小写字母和数字的组合，不能使用特殊字符，长度在6-18之间";
        this.isRegister = true;
        setTimeout(() => {
          this.isRegister = false;
        }, 500);
        return;
      }

      if (!nrep.test(this.user.nickName)) {
        this.registerText = "昵称必须为汉字，长度在5-8之间";
        this.isRegister = true;
        setTimeout(() => {
          this.isRegister = false;
        }, 500);
        return;
      }
      this.axios
        .post(
          "/user/register",
          qs.stringify({
            username: this.user.username,
            password: this.user.password,
            nickName: this.user.nickName,
          })
        )
        .then((response) => {
          if (response.data.code == -1) {
            this.msg = response.data.msg;
          } else {
            this.registerText = response.data.msg;
            this.isRegister = true;
            setTimeout(() => {
              this.isRegister = false;
            }, 500);
          }
          this.user.username = "";
          this.user.password = "";
          this.user.nickName = "";
        });
      this.msg = "";
    },
    goRegister() {
      this.flog = true;
      this.header = "注 册";
    },
    goLogin() {
      this.flog = false;
      this.header = "登 录";
    },
  },
};
</script>

<style lang="scss" scoped>
.msg {
  color: #c20c0c;
  margin-left: 2rem;
  margin-top: 0.625rem;
  font-weight: 800;
  padding: 0.0625rem;
}

.toast {
  position: absolute;
  left: 50%;
  top: 40%;
  transform: translate(-50%, -50%); /* 50%为自身尺寸的一半 */
  width: 80%;
  display: inline-block;
  height: 0.625rem;
  line-height: 0.625rem;
  padding-left: 0.125rem;
  padding-right: 0.0625rem;
  background-color: #000;
  opacity: 0.8;
  border-radius: 0.125rem;
  color: white;
  text-align: center;
}

.login {
  width: 12.5rem;
  height: 7.5rem;
  background-color: #fbfbfb;
  border-radius: 0.1rem;
  position: fixed;
  left: 50%;
  top: 50%;
  z-index: 9999;
  transform: translate(-50%, -50%);
  background-image: url("@/assets/images/backimg2.jpg");
}

.login-title {
  text-align: center;
  margin-top: 0.375rem;
  font-weight: bolder;
  font-size: 0.3rem;
  cursor: pointer;
  resize: none;
  color: #000;
}

.close-login {
  width: 0.625rem;
  height: 0.625rem;
  display: block;
  line-height: 0.625rem;
  text-align: center;
  font-size: 0.225rem;
  background-color: white;
  border-radius: 50%;
  text-decoration: none;
  position: absolute;
  top: -0.25rem;
  right: -0.25rem;
  font-weight: 100;
}

.login .left {
  width: 2.5rem;
  height: 2.5rem;
  text-align: center;
  line-height: 2.5rem;
  margin-top: 0.25rem;
  margin-left: 1.25rem;
  position: relative;
}

.login .left .shuffle {
  width: 2.5rem;
  height: 2.5rem;
  text-align: center;
  line-height: 2.5rem;
  position: absolute;
  top: 0;
  background-color: #ffffff;
  opacity: 0.6;
}
.login .sm_header {
  width: 1.5rem;
  margin-left: 2rem;
  padding-top: 0.125rem;
  font-size: 0.225rem;
  color: #000;
  font-weight: bold;
}

.login .left .shuffle a {
  font-size: 0.625rem;
  color: #000;
}
.login .left img {
  width: 2rem;
  height: 2rem;
}

.login-input-content {
  width: 6.25rem;
  height: 2.5rem;
  margin: auto;
}

.login-input {
  width: 100%;
  height: 1.25rem;
  line-height: 1.25rem;
}

.login-input label {
  width: 1.25rem;
  height: 1.25rem;
  line-height: 1.25rem;
  margin-left: 0.75rem;
  font-size: 0.225rem;
  font-weight: bold;
  color: #000;
}

.input {
  width: 3.75rem;
  height: 0.5rem;
  line-height: 0.5rem;
  text-indent: 1em;
  font-size: 0.15rem;
  font-weight: 500;
  color: #000;
  border-collapse: collapse;
  border-radius: 0.1rem;
  overflow: hidden;
  outline: 0;
  border: 1px solid #000;
}

.login-input input[type="text"] {
  margin-left: 0.325rem;
}

.login-input input[type="password"] {
  margin-left: 0.125rem;
}

.login-input #nickname {
  margin-left: 0.125rem;
}

.login-button {
  width: 1.5rem;
  height: 0.5rem;
  line-height: 0.5rem;
  font-size: 0.2rem;
  font-weight: bold;
  text-align: center;
  margin: auto;
  margin-top: 2rem;
  background-color: white;
  border-radius: 0.1rem;
  text-decoration: none;
  transition: all 0.3s ease-in;
  border: 0.0125rem solid #000;
}

.login-button a {
  display: block;
  margin-top: 0.125rem;
}

.login-button:hover {
  color: #c20c0c;
}

a {
  text-decoration: none;
}

.login .register {
  position: absolute;
  bottom: 0.375rem;
  right: 0.625rem;
  width: auto;
  height: 0.5rem;
  line-height: 0.5rem;
  text-align: center;
  padding-left: 0.125rem;
  padding-right: 0.125rem;
  background-color: white;
  border-radius: 0.05rem;
  font-size: 0.15rem;
  color: #000;
  border-bottom: 0.0125rem solid #000;
  font-weight: bold;
}
</style>
