package com.hooke.zdl.admin.module.support.table.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 自定义表格列
 */
@Data
public class TableColumnItemForm {

    @NotEmpty(message = "列不能为空")
    @Schema(description = "字段")
    private String columnKey;

    @Schema(description = "宽度")
    private Integer width;

    @NotNull(message = "显示不能为空")
    @Schema(description = "是否显示")
    private Boolean showFlag;

    @NotNull(message = "排序不能为空")
    @Schema(description = "排序")
    private Integer sort;


}
