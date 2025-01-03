package com.hooke.zdl.admin.module.business.entity;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Id;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public abstract class BaseEntity implements Serializable {
    @Id(keyType = KeyType.Auto)
    private Integer id;

    private LocalDateTime insertTime;

    private Integer insertUserId;

    private LocalDateTime updateTime;

    private Integer updateUserId;
}
