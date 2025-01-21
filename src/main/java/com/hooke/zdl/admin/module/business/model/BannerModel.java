package com.hooke.zdl.admin.module.business.model;

import lombok.Data;

@Data
public class BannerModel {
    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String cover;

    /**
     * 链接
     */
    private String link;

    /**
     * 位置标识
     */
    private String position;

    private  String searchText;
}
