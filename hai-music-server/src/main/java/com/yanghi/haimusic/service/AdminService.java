package com.yanghi.haimusic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanghi.haimusic.bean.Admin;
import com.yanghi.haimusic.utils.Result;
import java.util.List;

public interface AdminService extends IService<Admin> {


    /**
     * 根据ID获取管理员信息
     * @param id
     * @return
     */
    Result<Admin> getAdminById(int id);


    Result<List<Admin>> getAllAdmin();

}
