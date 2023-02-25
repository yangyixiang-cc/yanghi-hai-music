package com.yanghi.haimusic.service.impl;

import com.yanghi.haimusic.bean.Admin;
import com.yanghi.haimusic.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    AdminService adminService;

    @Test
    public void toSelect(){
        Admin admin = adminService.getById(1);
        System.out.println(admin);
    }
}