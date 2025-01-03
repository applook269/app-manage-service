package com.hooke.zdl.admin.module.business.entity;


import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;


@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("zdl_team_member")
public class TeamMember extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 团队 ID
     */
    private Integer teamId;

    /**
     * 团队成员 id
     */
    private Integer memberUserId;

    /**
     * 是否直推好友
     */
    private Boolean isChild;

}
