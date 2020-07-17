package com.wpg.wpgblog.entity;

import lombok.Data;

@Data
public class Comment {

    private Integer id;

    private Integer userId;

    private Integer articleId;

    private String content;

    private Integer state;

}
