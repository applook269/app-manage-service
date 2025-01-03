package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

import java.util.List;

@Data
public class SetAuthModel {
    private Integer roleId;
    private List<Integer> authIds;
}
