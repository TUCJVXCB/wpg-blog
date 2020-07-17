package com.wpg.wpgblog.vo;

import com.wpg.wpgblog.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ArticleListVO {

    private int total;

    private List<Article> articleList;
}
