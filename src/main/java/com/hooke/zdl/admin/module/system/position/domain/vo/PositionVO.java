package com.hooke.zdl.admin.module.system.position.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 职务表 列表VO
 */

@Data
public class PositionVO {


    @Schema(description = "职务ID")
    private Long positionId;

    @Schema(description = "职务名称")
    private String positionName;

    @Schema(description = "职级")
    private String level;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}