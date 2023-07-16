package com.example.mapper;

import com.example.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.*;

/**
 * @Desc：测试mybatis-plus所有关于写数据方法的Test
 *
 *
 * 删除功能：
 *  如果实体类属性上没有加@TableLogic则sql是真删除
 *  如果实体类属性上加了@TableLogic则sql是逻辑删除，即update修改@TableLogic修饰的字段，
 *
 *
 * 新增功能：
 *  自动填充：会自动填充createTime,delFlag。具体怎么实现看com.example.interceptor.AutoFillDateInterceptor这个类
 *  自动生成id：实体类id上没有任何注解，默认使用雪花算法生成id，所以数据库id类型最好为 bigint(20)。插入成功后，可以获取id的值。
 *  批量添加：批量添加的方法在mybatis-plus提供的service中
 *
 * 修改功能：
 * 自动修改‘update_time'字段: 具体怎么实现看com.example.interceptor.AutoFillDateInterceptor这个类
 */

@SpringBootTest
@Slf4j
public class UserCommandMapperTest {

    @Resource
    private UserMapper userMapper;

    /**
     * @Desc：插入一条数据
     */
    @Test
    void insertTest(){
        User user = User.builder().addr("广州").age(20).email("4444@QQ.COM").name("HHHHH").build();
        int result = userMapper.insert(user);
        System.out.println("result = " + result);
        System.out.println("user.getId() = " + user.getId());
    }



    /**
     * @Desc:根据id删除
     */
    @Test
    void deleteByIdTest(){
        User user = User.builder().addr("广州").age(17).email("555@QQ.COM").name("ccccc").build();
        userMapper.insert(user);
        userMapper.deleteById(user.getId());
    }



    /**
     * @Desc：根据ids批量删除
     */
    @Test
    void deleteBatchByIdsTest(){
        User user1 = User.builder().addr("广州").age(17).email("555@QQ.COM").name("ccccc").build();
        User user2 = User.builder().addr("北京").age(17).email("555@QQ.COM").name("aaaaa").build();
        userMapper.insert(user1);
        userMapper.insert(user2);


        List<Long> ids = Arrays.asList(user1.getId(), user2.getId());
        userMapper.deleteBatchIds(ids);
    }



    /**
     * @Desc：根据map的条件删除，这个方法只能写非常固定的条件，比如xxx = xxx的添加，无法写 xxx > xx类似的条件
     * 如果没有条件，删除的就是所有
     */
    @Test
    void deleteByMapTest(){
        Map<String,Object> condition = new HashMap<>();
        condition.put("name","ccccc");
        userMapper.deleteByMap(condition);
    }


    /**
     * @Desc:根据id进行修改，传入一个实体类，id是修改的条件，其他属性是修改的值。
     * 但是当属性为null,或者属性没有显示赋值的时候，则sql中不会修改该字段啊
     */
    @Test
    void updateByIdTest(){
        User updateBefore = User.builder().age(18).email("555@QQ.COM").name("ccccc").build();
        // INSERT INTO mybatis_user ( id, name, age, email, create_time, del_flag ) VALUES ( ?, ?, ?, ?, ?, ? )
        userMapper.insert(updateBefore);

        User updateById = User.builder().id(updateBefore.getId()).age(18).name("iiiii").build();
        //UPDATE mybatis_user SET name=?, age=?, update_time=? WHERE id=? AND del_flag='0'
        userMapper.updateById(updateById);

        //SELECT id,name,age,email,create_time,update_time,del_flag FROM mybatis_user WHERE id=? AND del_flag='0'
        User updateAfter = userMapper.selectById(updateById.getId());

        System.out.println("updateAfter = " + updateAfter);
        //updateAfter = User(id=1680501683676413954, name=iiiii, age=18, email=555@QQ.COM, ...)
    }


}
