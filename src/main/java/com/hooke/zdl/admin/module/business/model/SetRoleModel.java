package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

import java.util.List;

@Data
public class SetRoleModel {
    private Integer userId;
    private List<Integer> roleIds;
}
