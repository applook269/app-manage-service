package com.hooke.zdl.admin.module.support.table.domain;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 自定义表格列
 */
@Data
@Table("zdl_admin_table_column")
public class TableColumnEntity {

    @Id(keyType = KeyType.Auto)
    private Long tableColumnId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 表id
     */
    private Integer tableId;

    /**
     * 表列
     */
    private String columns;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
