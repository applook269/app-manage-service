package com.hooke.zdl.admin.config;

import com.hooke.zdl.admin.listener.MybatisInsertListener;
import com.hooke.zdl.admin.listener.MybatisUpdateListener;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisFlexConfiguration {
    private static final Logger logger = LoggerFactory.getLogger("mybatis-flex-sql");

    public MyBatisFlexConfiguration() {
        //开启审计功能
        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage ->
                logger.info("{},{}ms", auditMessage.getFullSql()
                        , auditMessage.getElapsedTime())
        );

        MybatisInsertListener mybatisInsertListener = new MybatisInsertListener();
        MybatisUpdateListener mybatisUpdateListener = new MybatisUpdateListener();
        FlexGlobalConfig config = FlexGlobalConfig.getDefaultConfig();

        //设置BaseEntity类启用
        config.registerInsertListener(mybatisInsertListener, Object.class);
        config.registerUpdateListener(mybatisUpdateListener, Object.class);
    }
}