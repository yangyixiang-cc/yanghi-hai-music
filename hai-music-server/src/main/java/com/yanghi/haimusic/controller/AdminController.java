package com.yanghi.haimusic.controller;

import com.yanghi.haimusic.bean.Admin;
import com.yanghi.haimusic.service.AdminService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author 泗安
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;


    /**
     * 返回指定管理员信息
     */
    @PostMapping("/{id}")
    public Result<Admin> returnAdminInfoById(@PathVariable("id") int id){
        Admin admin = adminService.getById(id);
        Result<Admin> result = Result.ok(admin);
        return result;
    }

    //返回全部的管理员信息
    @GetMapping("/")
    public Result<List<Admin>> returnAllAdminInfo(){
        List<Admin> admins = adminService.list();
        Result<List<Admin>> listResult = Result.ok(admins);
        return listResult;
    }
}
