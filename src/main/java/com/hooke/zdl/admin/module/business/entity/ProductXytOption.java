package com.hooke.zdl.admin.module.business.entity;


import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_product_xyt_option")
public class ProductXytOption extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 产品 ID
     */
    private Integer productId;

    /**
     * 期限
     */
    private Integer yieldPeriod;

    /**
     * 收益率
     */
    private BigDecimal yieldRate;

    /**
     * 选项描述
     */
    private String content;

}
