package com.example.service;

import com.example.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * 对数据库的写操作
 */
@SpringBootTest
public class UserCommandServiceTest {

    @Autowired
    private UserService userService;


    /**
     * @Desc:添加一条记录
     */
    @Test
    void saveTest(){
        User user = User.builder().name("张三21212121").age(88).email("9999@QQ.COM").build();
        boolean isSuccess = userService.save(user);
        System.out.println("isSuccess = " + isSuccess);
    }

    /**
     * 批量添加，底层executeType为batch，不用担心效率
     */
    @Test
    void saveBatchTest(){
        User user1 = User.builder().name("张三").age(88).email("9999@QQ.COM").build();
        User user2 = User.builder().name("李四").age(88).email("9999@QQ.COM").build();
        List<User> users = Arrays.asList(user1, user2);
        boolean isSuccess = userService.saveBatch(users);
        System.out.println("isSuccess = " + isSuccess);
    }


    /**
     * 添加或修改一条数据，有id为修改，无id为新增
     */
    @Test
    void saveOrUpdateTest(){
        User saveUser = User.builder().name("张三").age(88).email("9999@QQ.COM").build();
        //这个时候是新增
        userService.saveOrUpdate(saveUser);
        User user1 = userService.getById(saveUser.getId());
        System.out.println("新增的user = " + user1);


        User updateUser = User.builder().id(saveUser.getId()).name("王五").age(88).email("000000000@QQ.COM").build();
        //这个时候是修改
        userService.saveOrUpdate(updateUser);
        User user2 = userService.getById(updateUser.getId());
        System.out.println("修改后的user = " + user2);
    }


    /**
     * 添加或修改多条数据，有id为修改，无id为新增
     */
    @Test
    void saveOrUpdateListTest(){
        User user1 = User.builder().name("张三").age(88).email("9999@QQ.COM").build();
        User user2 = User.builder().name("李四").age(90).email("0000@QQ.COM").build();

        List<User> saveUsers = Arrays.asList(user1, user2);
        //此时添加
        userService.saveOrUpdateBatch(saveUsers);

        User user3 = User.builder().id(user1.getId()).name("张三update").age(88).email("9999@QQ.COM").build();
        User user4 = User.builder().id(user2.getId()).name("李四update").age(90).email("0000@QQ.COM").build();
        List<User> updateUsers = Arrays.asList(user3, user4);
        //此时修改
        userService.saveOrUpdateBatch(updateUsers);

    }




}
