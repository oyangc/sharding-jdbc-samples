package com.oyangc.bootsharding.service.impl;

import com.oyangc.bootsharding.entity.UserInfo;
import com.oyangc.bootsharding.mapper.UserInfoMapper;
import com.oyangc.bootsharding.service.IUserService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Component("userService")
@Service
public class UserServiceImpl  extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @DS("sharding")
    public int insertUser(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    @DS("sharding")
    public UserInfo getUserInfoByUserId(Long id) {
        return userInfoMapper.selectById(id);
    }
    @Override
    @DS("sharding")
    public List<UserInfo> getList(String city) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper();
//        wrapper.eq("city", city);
        return userInfoMapper.selectList(wrapper);
    }

    @Override
    @DS("sharding")
    public int count() {
        return super.count();
    }

    @Override
    @DS("sharding")
    public <E extends IPage<UserInfo>> E page(E page) {
        return super.page(page);
    }
}
