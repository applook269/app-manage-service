package com.hooke.zdl.admin.module.business.entity;


import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_wallet")
public class Wallet extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private BigDecimal balance;

    private BigDecimal tokens;

    private String payPassword;

}
