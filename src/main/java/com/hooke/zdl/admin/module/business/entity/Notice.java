package com.hooke.zdl.admin.module.business.entity;


import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("zdl_notice")
public class Notice extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    private String title;

    private String content;

    private String status;

}
