package com.wpg.wpgblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentBackEndVO {

    private Integer id;

    private String username;

    private Integer articleId;

    private String content;
}
