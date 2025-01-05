package com.hooke.zdl.admin.listener;

import cn.hutool.core.util.ReflectUtil;
import com.mybatisflex.annotation.UpdateListener;

import java.time.LocalDateTime;

public class MybatisUpdateListener implements UpdateListener {
    @Override
    public void onUpdate(Object o) {
        if (ReflectUtil.hasField(o.getClass(), "updateTime")) {
            ReflectUtil.setFieldValue(o, "updateTime", LocalDateTime.now());
        }
    }
}