package com.wpg.wpgblog.entity;

import lombok.Data;

@Data
public class Role {
    /**
     * 主键
     */
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 描述
     */
    private String description;
}
