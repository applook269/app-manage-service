package com.hooke.zdl.admin.module.support.datatracer.domain.form;

import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.swagger.SchemaEnum;
import com.hooke.zdl.admin.module.support.datatracer.constant.DataTracerTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 查询表单
 */
@Data
public class DataTracerQueryForm extends PageParam {

    @SchemaEnum(DataTracerTypeEnum.class)
    private Integer type;

    @Schema(description = "业务id")
    @NotNull(message = "业务id不能为空")
    private Long dataId;

    @Schema(description = "关键字")
    private String keywords;
}
