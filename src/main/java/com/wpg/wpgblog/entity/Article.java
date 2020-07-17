package com.wpg.wpgblog.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Article {

    @JsonProperty("articleId")
    private Integer id;

    private Integer userId;

    private String title;

    private String mdContent;

    private String htmlContent;

    private Integer likes;

    private Integer categoryId;

    private Integer state;

}
