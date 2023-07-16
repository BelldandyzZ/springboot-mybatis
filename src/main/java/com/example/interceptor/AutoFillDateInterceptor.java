package com.example.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Desc:新增和修改时根据重写的方法自动填充数据,但是如果需要填充的数据已经有值了就不会自动填充
 */
@Slf4j
@Component
public class AutoFillDateInterceptor implements MetaObjectHandler {

    /**
     * 添加时自动填充create_time和delFlag，但是需要在实体类上添加@TableField(fill = FieldFill.INSERT)注解才可以
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
        this.strictInsertFill(metaObject, "delFlag", () -> "0", String.class);
    }


    /**
     * 修改时自动填充update_time,但是需要在实体类上添加@TableField(fill = FieldFill.UPDATE)注解才可以
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
    }
}
