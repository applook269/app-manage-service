package com.hooke.zdl.admin.module.support.datatracer.annoation;

import com.mybatisflex.core.BaseMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 支持查询sql
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DataTracerFieldSql {

    /**
     * 关联字段名称
     */
    String relateColumn() default "id";

    /**
     * 关联显示的字段
     */
    String relateDisplayColumn() default "";

    /**
     * 是否关联字段查询Mapper
     */
    Class<? extends BaseMapper> relateMapper() default BaseMapper.class;

}
