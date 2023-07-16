package com.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 对数据库的读操作
 */
@SpringBootTest
public class UserQueryServiceTest {

    @Autowired
    private UserService userService;

    /**
     * 计数
     */
    @Test
    void count(){
        long count = userService.count();
        System.out.println("count = " + count);
    }




}
