package com.yanghi.haimusic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanghi.haimusic.bean.Admin;
import com.yanghi.haimusic.mapper.AdminMapper;
import com.yanghi.haimusic.service.AdminService;
import com.yanghi.haimusic.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public Result<Admin> getAdminById(int id) {
        return Result.ok(this.getById(id));
    }

    @Override
    public Result<List<Admin>> getAllAdmin() {
        return Result.ok(this.list());
    }
}
