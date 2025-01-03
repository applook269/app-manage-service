package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_product_xyt")
public class ProductXyt extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品参数
     */
    private String detail;

    /**
     * 起投金额
     */
    private BigDecimal minPrice;

    /**
     * 上限金额
     */
    private BigDecimal maxPrice;

    /**
     * 删除标记
     */
    private String deleteFlag;

}
