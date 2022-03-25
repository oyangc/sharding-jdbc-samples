package com.oyangc.bootsharding;

import com.alibaba.fastjson.JSON;
import com.oyangc.bootsharding.entity.UserInfo;
import com.oyangc.bootsharding.service.IUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ServiceTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private IUserService userService;

    @Test
    public void testSelect() {
        System.out.println(("----- testSelect ------"));
        System.out.println(userService.getById(Long.valueOf(1)));
    }
    @Test
    public void testPage() {
        System.out.println(("----- testPage ------"));
        Page<UserInfo> page = new Page<>();
        Page<UserInfo> ans = userService.page(page);
        System.out.println(JSON.toJSONString(ans));
        System.out.println(ans.getMaxLimit());
        int count = userService.count();
        System.out.println(count);
    }

    @Test
    public void testList() {
        System.out.println(("----- selectAll method test ------"));
        userService.list().forEach(System.out::println);
    }

    @Test
    public void testShardingList() {
        System.out.println(("----- selectShardingList method test ------"));
        userService.getList("gz").forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        System.out.println(("----- insert method test ------"));

        UserInfo userInfo = buildUserInfo();
//        userInfoMapper.insert(userInfo);
        userService.insertUser(userInfo);
        System.out.println(userInfo);
    }

    @Test
    public void testBatchInsert() {
        System.out.println(("----- insert method test ------"));
        for (int i = 0; i < 1000; i++) {
            UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(i);
            userInfo.setAccount("Account" + i);
            userInfo.setPassword("pass" + i);
            userInfo.setUserName("name" + i);
//        userInfoMapper.insert(userInfo);
            userService.insertUser(userInfo);
//            System.out.println(userInfo);
        }
        System.out.println("-- complete --");
    }

    public UserInfo buildUserInfo() {
        long i = (long) (Math.random() * 10000L);
        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(i);
        userInfo.setAccount("Account" + i);
        userInfo.setPassword("pass" + i);
        userInfo.setUserName("name" + i);
        return userInfo;
    }


}
