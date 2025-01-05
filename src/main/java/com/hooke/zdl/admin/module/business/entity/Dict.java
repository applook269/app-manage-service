package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_dict")
public class Dict extends BaseEntity{
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典键
     */
    private String dictKey;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 字典描述
     */
    private String dictDesc;
}
