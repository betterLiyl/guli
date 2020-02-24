package com.guli.teacher.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充处理器
 *      insertFill：插入数据时填充
 *      updateFill：更新数据时填充
 */
@Component
public class DataMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
this.setFieldValByName("gmtCreate",new Date(),metaObject);
this.setFieldValByName("gmtModified",new Date(),metaObject);
this.setFieldValByName("isDeleted",false,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
