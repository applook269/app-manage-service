package com.hooke.zdl.admin.module.support.datatracer.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hooke.zdl.admin.common.util.SmartBigDecimalUtil;
import com.hooke.zdl.admin.common.util.SmartEnumUtil;
import com.hooke.zdl.admin.module.support.datatracer.annoation.DataTracerFieldBigDecimal;
import com.hooke.zdl.admin.module.support.datatracer.annoation.DataTracerFieldEnum;
import com.hooke.zdl.admin.module.support.datatracer.annoation.DataTracerFieldLabel;
import com.hooke.zdl.admin.module.support.datatracer.annoation.DataTracerFieldSql;
import com.hooke.zdl.admin.module.support.datatracer.constant.DataTracerConst;
import com.hooke.zdl.admin.module.support.datatracer.domain.bo.DataTracerContentBO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据变更内容
 */
@Slf4j
@Service
public class DataTracerChangeContentService {

    @Resource
    private ApplicationContext applicationContext;
    /**
     * 字段描述缓存
     */
    private final ConcurrentHashMap<String, String> fieldDescCacheMap = new ConcurrentHashMap<>();

    /**
     * 类 加注解字段缓存
     */
    private final ConcurrentHashMap<Class<?>, List<Field>> fieldMap = new ConcurrentHashMap<>();

    /**
     * 数据批量对比
     */
    public <T> String getChangeContent(List<T> oldObjectList, List<T> newObjectList) {
        boolean valid = this.valid(oldObjectList, newObjectList);
        if (!valid) {
            return "";
        }
        String operateType = this.getOperateType(oldObjectList, newObjectList);
        String operateContent = "";
        if (DataTracerConst.INSERT.equals(operateType) || DataTracerConst.DELETE.equals(operateType)) {
            operateContent = this.getObjectListContent(newObjectList);
            if (StringUtils.isEmpty(operateContent)) {
                return "";
            }
            return operateType + ":" + operateContent;
        }
        if (DataTracerConst.UPDATE.equals(operateType)) {
            return this.getUpdateContentList(oldObjectList, newObjectList);
        }
        return operateContent;
    }


    /**
     * 解析多个对象的变更，删除，新增
     * oldObject 为空 ，newObject 不为空 为新增
     * oldObject 不为空 ，newObject 不空 为删除
     * 都不为空为编辑
     */
    public String getChangeContent(Object oldObject, Object newObject) {
        boolean valid = this.valid(oldObject, newObject);
        if (!valid) {
            return "";
        }
        String operateType = this.getOperateType(oldObject, newObject);
        String operateContent = "";
        if (DataTracerConst.INSERT.equals(operateType) || DataTracerConst.DELETE.equals(operateType)) {
            operateContent = this.getAddDeleteContent(newObject);
        }
        if (DataTracerConst.UPDATE.equals(operateType)) {
            operateContent = this.getUpdateContent(oldObject, newObject);
        }
        if (StringUtils.isEmpty(operateContent)) {
            return "";
        }
        return operateContent;
    }

    /**
     * 解析单个bean的内容
     */
    public String getChangeContent(Object object) {
        return this.getAddDeleteContent(object);
    }

    // ---------------------------- 以下 是 私有private 方法 ----------------------------

    /**
     * 获取新增或删除操作内容
     */
    private String getAddDeleteContent(Object object) {
        List<Field> fields = this.getField(object);
        Map<String, DataTracerContentBO> beanParseMap = this.fieldParse(object, fields);
        return this.getAddDeleteContent(beanParseMap);
    }

    /**
     * 单个对象变动内容
     */
    private <T> String getUpdateContentList(List<T> oldObjectList, List<T> newObjectList) {
        String oldContent = this.getObjectListContent(oldObjectList);
        String newContent = this.getObjectListContent(newObjectList);
        if (oldContent.equals(newContent)) {
            return "";
        }
        if (StringUtils.isEmpty(oldContent) && StringUtils.isEmpty(newContent)) {
            return "";
        }
        return "【原数据】:<br/>" + oldContent + "<br/>" + "【新数据】:<br/>" + newContent;
    }

    /**
     * 获取一个对象的内容信息
     */
    private <T> String getObjectListContent(List<T> objectList) {
        if (CollectionUtils.isEmpty(objectList)) {
            return "";
        }
        List<Field> fields = this.getField(objectList.get(0));
        List<String> contentList = Lists.newArrayList();
        for (Object objItem : objectList) {
            Map<String, DataTracerContentBO> beanParseMap = this.fieldParse(objItem, fields);
            contentList.add(this.getAddDeleteContent(beanParseMap));
        }
        return StringUtils.join(contentList, "<br/>");
    }

    private String getAddDeleteContent(Map<String, DataTracerContentBO> beanParseMap) {
        List<String> contentList = new ArrayList<>();
        for (Entry<String, DataTracerContentBO> entry : beanParseMap.entrySet()) {
            DataTracerContentBO dataTracerContentBO = entry.getValue();
            boolean jsonFlag = JSONUtil.isTypeJSON(dataTracerContentBO.getFieldContent());
            String filedDesc = dataTracerContentBO.getFieldDesc();
            if (jsonFlag) {
                contentList.add(filedDesc + "(请进入详情查看)");
            } else {
                contentList.add(dataTracerContentBO.getFieldDesc() + ":" + dataTracerContentBO.getFieldContent());
            }
        }
        String operateContent = StringUtils.join(contentList, "<br/>");
        if (StringUtils.isEmpty(operateContent)) {
            return "";
        }
        return operateContent;
    }


    /**
     * 获取更新操作内容
     */
    private <T> String getUpdateContent(T oldObject, T newObject) {
        List<Field> fields = this.getField(oldObject);
        List<String> contentList = new ArrayList<>();
        Map<String, DataTracerContentBO> oldBeanParseMap = this.fieldParse(oldObject, fields);
        Map<String, DataTracerContentBO> newBeanParseMap = this.fieldParse(newObject, fields);
        //oldBeanParseMap与newBeanParseMap size一定相同
        for (Entry<String, DataTracerContentBO> entry : oldBeanParseMap.entrySet()) {
            String fieldName = entry.getKey();
            // 新旧对象内容
            DataTracerContentBO oldContentBO = entry.getValue();
            DataTracerContentBO newContentBO = newBeanParseMap.get(fieldName);
            // fieldContent
            String oldContent = oldContentBO == null || oldContentBO.getFieldContent() == null ? "" : oldContentBO.getFieldContent();
            String newContent = newContentBO == null || newContentBO.getFieldContent() == null ? "" : newContentBO.getFieldContent();

            if (oldContent.equals(newContent)) {
                continue;
            }
            String fieldDesc = oldContentBO.getFieldDesc();
            boolean jsonFlag = JSONUtil.isTypeJSON(oldContent) || JSONUtil.isTypeJSON(newContent);
            if (jsonFlag) {
                String content = fieldDesc + "【进入详情查看】";
                contentList.add(content);
                continue;
            }
            String content = fieldDesc + ":" + "由【" + oldContent + "】变更为【" + newContent + "】";
            contentList.add(content);
        }
        if (CollectionUtils.isEmpty(contentList)) {
            return "";
        }
        String operateContent = StringUtils.join(contentList, "<br/>");
        if (StringUtils.isEmpty(operateContent)) {
            return "";
        }
        return operateContent;
    }


    /**
     * 接bean对象
     */
    private Map<String, DataTracerContentBO> fieldParse(Object object, List<Field> fields) {
        if (fields == null || fields.isEmpty()) {
            return new HashMap<>();
        }
        //对象解析结果
        Map<String, DataTracerContentBO> objectParse = new HashMap<>(16);
        for (Field field : fields) {
            field.setAccessible(true);
            String desc = this.getFieldDesc(field);
            if (StringUtils.isEmpty(desc)) {
                continue;
            }
            DataTracerContentBO dataTracerContentBO = this.getFieldValue(field, object);
            if (dataTracerContentBO != null) {
                dataTracerContentBO.setFieldDesc(desc);
                objectParse.put(field.getName(), dataTracerContentBO);
            }
        }
        return objectParse;
    }

    /**
     * 获取字段值
     */
    private DataTracerContentBO getFieldValue(Field field, Object object) {
        Object fieldValue = "";
        Class clazz = object.getClass();
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method get = pd.getReadMethod();
            fieldValue = get.invoke(object);
        } catch (Exception e) {
            log.error("bean operate log: reflect field value error " + field.getName());
            return null;
        }
        if (fieldValue == null) {
            return null;
        }

        String fieldContent = "";
        DataTracerFieldEnum dataTracerFieldEnum = field.getAnnotation(DataTracerFieldEnum.class);
        DataTracerFieldSql dataTracerFieldSql = field.getAnnotation(DataTracerFieldSql.class);
        if (dataTracerFieldEnum != null) {
            if (fieldValue instanceof Collection) {
                fieldContent = SmartEnumUtil.getEnumDescByValueList((Collection) fieldValue, dataTracerFieldEnum.enumClass());
            } else {
                fieldContent = SmartEnumUtil.getEnumDescByValue(fieldValue, dataTracerFieldEnum.enumClass());
            }
        } else if (dataTracerFieldSql != null) {
            fieldContent = this.getRelateDisplayValue(fieldValue, dataTracerFieldSql);
        } else if (fieldValue instanceof Date) {
            fieldContent = DateUtil.formatDateTime((Date) fieldValue);
        } else if (fieldValue instanceof LocalDateTime) {
            fieldContent = LocalDateTimeUtil.formatNormal((LocalDateTime) fieldValue);
        } else if (fieldValue instanceof LocalDate) {
            fieldContent = LocalDateTimeUtil.formatNormal((LocalDate) fieldValue);
        } else if (fieldValue instanceof BigDecimal) {
            DataTracerFieldBigDecimal dataTracerFieldBigDecimal = field.getAnnotation(DataTracerFieldBigDecimal.class);
            if (dataTracerFieldBigDecimal != null) {
                BigDecimal value = SmartBigDecimalUtil.setScale((BigDecimal) fieldValue, dataTracerFieldBigDecimal.scale());
                fieldContent = value.toString();
            }
        } else {
            fieldContent = JSON.toJSONString(fieldValue);
        }
        DataTracerContentBO dataTracerContentBO = new DataTracerContentBO();
        dataTracerContentBO.setField(field);
        dataTracerContentBO.setFieldValue(fieldValue);
        dataTracerContentBO.setFieldContent(fieldContent);
        return dataTracerContentBO;
    }

    /**
     * 获取关联字段的显示值
     */
    private String getRelateDisplayValue(Object fieldValue, DataTracerFieldSql dataTracerFieldSql) {
//        Class<? extends BaseMapper> relateMapper = dataTracerFieldSql.relateMapper();
//        BaseMapper<?> mapper = applicationContext.getBean(relateMapper);
//        if (mapper == null) {
//            return "";
//        }
//        String relateFieldValue = fieldValue.toString();
//        QueryWrapper qw = new QueryWrapper();
//        qw.select(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, dataTracerFieldSql.relateDisplayColumn()));
//        qw.in(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, dataTracerFieldSql.relateColumn()), relateFieldValue);
//        List<Object> displayValue = mapper.selectObjs(qw);
//        if (CollectionUtils.isEmpty(displayValue)) {
//            return "";
//        }
//        return SmartStringUtil.join(",", displayValue);
        return null;
    }

    /**
     * 获取字段描述信息 优先 OperateField 没得话swagger判断
     */
    private String getFieldDesc(Field field) {
        // 根据字段名称 从缓存中查询
        String fieldName = field.toGenericString();
        String desc = fieldDescCacheMap.get(fieldName);
        if (null != desc) {
            return desc;
        }
        DataTracerFieldLabel operateField = field.getAnnotation(DataTracerFieldLabel.class);
        if (operateField != null) {
            return operateField.value();
        }
        fieldDescCacheMap.put(fieldName, desc);
        return desc;
    }

    /**
     * 获取操作类型
     */
    private String getOperateType(Object oldObject, Object newObject) {
        if (oldObject == null && newObject != null) {
            return DataTracerConst.INSERT;
        }
        if (oldObject != null && newObject == null) {
            return DataTracerConst.DELETE;
        }
        return DataTracerConst.UPDATE;
    }

    /**
     * 校验是否进行比对
     */
    private boolean valid(Object oldObject, Object newObject) {
        if (oldObject == null && newObject == null) {
            return false;
        }
        if (oldObject == null) {
            return true;
        }
        if (newObject == null) {
            return true;
        }
        String oldClass = oldObject.getClass().getName();
        String newClass = newObject.getClass().getName();
        return oldClass.equals(newClass);
    }


    /**
     * 校验
     */
    private <T> boolean valid(List<T> oldObjectList, List<T> newObjectList) {
        if (CollectionUtils.isEmpty(oldObjectList) && CollectionUtils.isEmpty(newObjectList)) {
            return false;
        }
        if (CollectionUtils.isEmpty(oldObjectList) && CollectionUtils.isNotEmpty(newObjectList)) {
            return true;
        }
        if (CollectionUtils.isNotEmpty(oldObjectList) && CollectionUtils.isEmpty(newObjectList)) {
            return true;
        }
        if (CollectionUtils.isNotEmpty(oldObjectList) && CollectionUtils.isNotEmpty(newObjectList)) {
            T oldObject = oldObjectList.get(0);
            T newObject = newObjectList.get(0);
            String oldClass = oldObject.getClass().getName();
            String newClass = newObject.getClass().getName();
            return oldClass.equals(newClass);
        }
        return true;
    }

    /**
     * 查询 包含 file key 注解的字段
     * 使用缓存
     */
    private List<Field> getField(Object obj) {
        // 从缓存中查询
        Class<?> tClass = obj.getClass();
        List<Field> fieldList = fieldMap.get(tClass);
        if (null != fieldList) {
            return fieldList;
        }

        // 这一段递归代码 是为了 从父类中获取属性
        Class<?> tempClass = tClass;
        fieldList = new ArrayList<>();
        while (tempClass != null) {
            Field[] declaredFields = tempClass.getDeclaredFields();
            for (Field field : declaredFields) {
                // 过虑出有注解字段
                if (!field.isAnnotationPresent(DataTracerFieldLabel.class)) {
                    continue;
                }
                field.setAccessible(true);
                fieldList.add(field);
            }
            tempClass = tempClass.getSuperclass();
        }
        fieldMap.put(tClass, fieldList);
        return fieldList;
    }


}
