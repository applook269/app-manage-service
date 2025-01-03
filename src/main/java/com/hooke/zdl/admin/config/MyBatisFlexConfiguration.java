package com.hooke.zdl.admin.config;

import com.hooke.zdl.admin.listener.MybatisInsertListener;
import com.hooke.zdl.admin.listener.MybatisUpdateListener;
import com.mybatisflex.core.FlexGlobalConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisFlexConfiguration {

    public MyBatisFlexConfiguration() {

        MybatisInsertListener mybatisInsertListener = new MybatisInsertListener();
        MybatisUpdateListener mybatisUpdateListener = new MybatisUpdateListener();
        FlexGlobalConfig config = FlexGlobalConfig.getDefaultConfig();

        //设置BaseEntity类启用
        config.registerInsertListener(mybatisInsertListener, Object.class);
        config.registerUpdateListener(mybatisUpdateListener, Object.class);
    }
}