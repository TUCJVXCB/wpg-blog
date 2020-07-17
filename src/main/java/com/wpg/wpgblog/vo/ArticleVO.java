package com.wpg.wpgblog.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVO {

    @JsonProperty("articleId")
    private Integer id;

    private Integer userId;

    private String avatar;

    @JsonProperty("nickname")
    private String username;

    private String title;

    private String mdContent;

    private String htmlContent;

    private Integer likes;

    private Integer categoryId;

    private Integer state;

    private List<CommentVO> commentVOList;

    private Boolean liked = false;

    private Boolean collected = false;
}
