package com.oyangc.bootsharding.service;


import com.oyangc.bootsharding.entity.UserInfo;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IUserService extends IService<UserInfo> {
    int insertUser(UserInfo userInfo);

    UserInfo getUserInfoByUserId(Long id);

    @DS("sharding")
    List<UserInfo> getList(String city);
}
