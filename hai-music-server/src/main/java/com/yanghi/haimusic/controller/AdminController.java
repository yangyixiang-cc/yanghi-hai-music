package com.yanghi.haimusic.controller;

import com.yanghi.haimusic.bean.Admin;
import com.yanghi.haimusic.service.AdminService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 泗安
 * 明确职责：controller 只进行参数校验和调用service层方法
 */
@RestController
@RequestMapping("/api/admin")
@Validated
public class AdminController {

    @Resource(name = "adminServiceImpl")
    private AdminService adminService;

    /**
     * 返回指定管理员信息
     */
    @GetMapping("/{id}")
    public Result<Admin> returnAdminInfoById(@PathVariable("id") @NotNull(message = "用户名不能为空") Integer id){
        return adminService.getAdminById(id);
    }

    /**
     * 返回全局的管理员信息
     * @return
     */
    @GetMapping("/")
    public Result<List<Admin>> returnAllAdminInfo(){
        return adminService.getAllAdmin();
    }
}
