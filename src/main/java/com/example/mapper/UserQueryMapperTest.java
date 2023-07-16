package com.example.mapper;

import com.example.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
/**
 * 测试mybatis-plus所有查询方法的Test
 *
 * 如果实体类属性的逻辑删除字段加了@TableLogic，则查询会自动添加逻辑删除的条件，如del_flag= 0
 */
public class UserQueryMapperTest {

    @Resource
    private UserMapper userMapper;

    /**
     * @Desc：通过条件构造器查询一个集合，
     */
    @Test
    void selectListByConditionTest(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     * @Desc：通过ids查询一个集合
     */
    @Test
    void selectBatchIdsTest(){
        User user1 = User.builder().age(20).email("4444@QQ.COM").name("HHHHH").build();
        User user2 = User.builder().age(20).email("2222@QQ.COM").name("HHHHH").build();
        userMapper.insert(user1);
        userMapper.insert(user2);

        List<Long> ids = Arrays.asList(user1.getId(), user2.getId());
        List<User> users = userMapper.selectBatchIds(ids);
        users.forEach(System.out::println);
    }

    /**
     * @Desc:以map集合为条件查询集合,这个条件只能是 xxx = xxx，无法加入xxx > xxx之类的条件
     * 如果没有条件，查询的就是所有。但是使用了@TableLogic逻辑删除之后，查询的就是所有del_flag= 0的数据
     */
    @Test
    void selectByMap(){
        User user1 = User.builder().age(20).email("4444@QQ.COM").name("BBBB1").build();
        User user2 = User.builder().age(20).email("2222@QQ.COM").name("BBBB1").build();
        userMapper.insert(user1);
        userMapper.insert(user2);

        Map<String,Object> condition = new HashMap<>();
        condition.put("name","BBBB1");

        List<User> users = userMapper.selectByMap(condition);
        users.forEach(System.out::println);
    }

    /**
     * @Desc：通过id查询一条记录
     */
    @Test
    void selectByIdTest(){
        User user = User.builder().age(20).email("4444@QQ.COM").name("HHHHH").build();
        userMapper.insert(user);
        User queryUser = userMapper.selectById(user.getId());
        System.out.println("queryUser = " + queryUser);
    }





}
