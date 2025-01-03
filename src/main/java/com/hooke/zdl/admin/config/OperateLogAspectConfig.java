package com.hooke.zdl.admin.config;

import com.hooke.zdl.admin.module.support.operatelog.core.OperateLogAspect;
import com.hooke.zdl.admin.module.support.operatelog.core.OperateLogConfig;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志切面 配置
 */
@Configuration
public class OperateLogAspectConfig extends OperateLogAspect {

    /**
     * 配置信息
     */
    @Override
    public OperateLogConfig getOperateLogConfig() {
        return OperateLogConfig.builder().corePoolSize(1).queueCapacity(10000).build();
    }


}