package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 *
 * @Desc：学习如何使用wrapper
 *
 * 关于wrapper：封装修改，删除和查询的条件
 *
 * UpdateWrapper：封装修改的条件，和修改的字段
 * QueryWrapper：封装查询和删除的条件
 *
 */
@SpringBootTest
public class UserWrapperServiceTest {

    @Autowired
    private UserService userService;

    /**
     * 条件查询
     */
    @Test
    void selectListByWrapper(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //查询条件
        queryWrapper
                .like("name","张")
                .between("age",30,40)
                .orderByDesc("create_time");
        List<User> users = userService.list(queryWrapper);

        users.forEach(System.out::println);

    }

    /**
     * 条件修改
     */
    @Test
    void updateByWrapper(){

        Integer age = 40;
        String name = "李";

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        //修改的条件,只有当ObjectUtils.isNotEmpty(age)为true的时候才会在sql中进行条件拼接
        updateWrapper.eq(ObjectUtils.isNotEmpty(age),"age",age)
                     .like("name",name);

        //修改的值
        updateWrapper.set("age",50);
        userService.update(updateWrapper);

    }


    @Test
    void useLambdaWrapper(){

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(User::getId, "1");// 与  queryWrapper.like("id", "1");  一样

        List<User> list = userService.list(queryWrapper);

    }




}
