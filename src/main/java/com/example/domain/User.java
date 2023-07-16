package com.example.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 如果使用@Builder注解，则必须加@NoArgsConstructor和@AllArgsConstructor
 * 使用@Builder生成满参数构造就没有无参构造了，@NoArgsConstructor加了之后满参数构造又没了，需要加@AllArgsConstructor
 *
 * 逻辑删除：使用@TableLogic注解加在delFlag上，在查询时就会自动加上del_flag = 0的条件，删除的时候也不会真删而是update修改del_flag的值为1。
 *          mybatis-plus默认的@TableLogic值是0为正常，1为删除，但是可以改。
 *
 * 自动填充： 使用了@TableField(fill = FieldFill.INSERT)和 @TableField(fill = FieldFill.UPDATE)，写数据的时候就会自动填充数据，填充的规则自己写
 *           见com.example.interceptor.AutoFillDateInterceptor例子
 *
 * 主键id:  插入时默认生成20位的雪花算法id, 默认@TableId(type = IdType.ASSIGN_ID)
 *         如果使用数据库自增策略，需要 @TableId(type = IdType.AUTO)，否则任然是雪花算法
 *         插入时如果开发者已经设置了id，则使用开发者的id
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@TableName("mybatis_user")
public class User {

    private Long id;

    private String name;

    private Integer age;

    private String email;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String delFlag;

    @TableField(exist = false)
    private String addr;




}
