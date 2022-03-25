package com.oyangc.bootsharding;

import com.alibaba.fastjson.JSON;
import com.oyangc.bootsharding.entity.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//测试环境使用，用来表示测试环境使用的ApplicationContext将是WebApplicationContext类型的
@WebAppConfiguration
@SpringBootTest
public class ShardingApplicationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        System.out.println("BeforeEach========");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void insert() throws Exception {
        UserInfo userInfo = buildUserInfo();
        String s = JSON.toJSONString(userInfo);
        System.out.println(s);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/save")
                        // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(s)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    public UserInfo buildUserInfo() {
        long i = (long) (Math.random() * 100000000L);
        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(i);
        userInfo.setAccount("Account" + i);
        userInfo.setPassword("pass" + i);
        userInfo.setUserName("name" + i);
        return userInfo;
    }

    @Test
    public void testGet() throws Exception {
        /*mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1)) //执行请求
                .andExpect(model().attributeExists("user")) //验证存储模型数据
                .andExpect(view().name("user/view")) //验证viewName
                .andExpect(forwardedUrl("/WEB-INF/jsp/user/view.jsp"))//验证视图渲染时forward到的jsp
                .andExpect(status().isOk())//验证状态码
                .andDo(print()); //输出MvcResult到控制台*/
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/{id}", 1507233264488345601L)
                        // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .queryParam("name", "Tom")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGet2() throws Exception {
        String responseString = mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/{id}", 1507233264488345601L)    //请求的url,请求的方法是get
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        // 设置返回值类型为utf-8，否则默认为ISO-8859-1
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)  //数据的格式
                        .param("pcode", "root")  //添加参数
                ).andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        System.out.println("<<<=====返回的json = " + responseString);
    }
}
