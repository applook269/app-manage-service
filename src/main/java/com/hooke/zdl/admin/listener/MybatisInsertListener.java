package com.hooke.zdl.admin.listener;

import cn.hutool.core.util.ReflectUtil;
import com.mybatisflex.annotation.InsertListener;

import java.time.LocalDateTime;

public class MybatisInsertListener implements InsertListener {
    @Override
    public void onInsert(Object o) {
//        Object username = StpUtil.getExtra("username");  //此处获取用户名
        if (ReflectUtil.hasField(o.getClass(), "createTime")) {
            ReflectUtil.setFieldValue(o, "createTime", LocalDateTime.now());
        }
        if (ReflectUtil.hasField(o.getClass(), "updateTime")) {
            ReflectUtil.setFieldValue(o, "updateTime", LocalDateTime.now());
        }
        if (ReflectUtil.hasField(o.getClass(), "deletedFlag")) {
            ReflectUtil.setFieldValue(o, "deletedFlag", 0);
        }
    }
}