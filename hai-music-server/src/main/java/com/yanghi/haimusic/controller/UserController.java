package com.yanghi.haimusic.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanghi.haimusic.bean.User;
import com.yanghi.haimusic.bean.UserDetails;
import com.yanghi.haimusic.service.UserDetailsService;
import com.yanghi.haimusic.service.UserService;
import com.yanghi.haimusic.utils.Md5Utils;
import com.yanghi.haimusic.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    //登录方法
    @PostMapping("/login")
    public Result<User> login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .eq("username",username)
                        .eq("password",Md5Utils.getMD5(password));
        User user = userService.getOne(userQueryWrapper);
        if(user != null) {
            user.setPassword("");
            return Result.ok(user);
        }
        return Result.failed("账号或密码错误，请重新输入");
    }

    //登录后发送请求获取用户详细信息
    @GetMapping("/userDetails")
    public Result<UserDetails> returnUserDetailsInfo(@RequestParam("userId") Integer userId){
        QueryWrapper<UserDetails> userDetailsQueryWrapper = new QueryWrapper<>();
        userDetailsQueryWrapper.eq("user_id",userId);
        UserDetails userDetails = userDetailsService.getOne(userDetailsQueryWrapper);
        if(userDetails == null){
            return Result.failed("获取数据失败，不存在该用户！");
        }
        return Result.ok(userDetails);
    }

    //根据id获取用户信息，会自动去除密码和账号等关键信息
    @GetMapping("/{id}")
    public Result<User> returnUserInfoById(@PathVariable("id") Integer id){
        User userServiceById = userService.getById(id);
        userServiceById.setUsername("");
        userServiceById.setPassword("");
        if(userServiceById == null){
            return Result.failed("数据获取失败！");
        }
        return Result.ok(userServiceById);
    }

    //注册
    @PostMapping("/register")
    public Result<User> register(@RequestParam("username") String username,
                              @RequestParam("password") String password,@RequestParam("nickName") String nickName){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .eq("username",username);
        User user = userService.getOne(userQueryWrapper);
        if(user != null) {
            return Result.failed("用户名已被使用，请重新输入");
        }
        User user1 = new User();
        user1.setUsername(username);
        user1.setPassword(Md5Utils.getMD5(password));
        user1.setNickName(nickName);
        user1.setPic("/img/avatorImages/defaultAvator.jpg");
        boolean save = userService.save(user1);
        if(save){
            return Result.ok(user1,"注册成功!");
        }
        return Result.failed("注册失败，服务器出现错误");
    }

}
