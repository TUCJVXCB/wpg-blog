package com.wpg.wpgblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ArticleVOListVO {

    List<ArticleVO> articleList;

    int total;
}
