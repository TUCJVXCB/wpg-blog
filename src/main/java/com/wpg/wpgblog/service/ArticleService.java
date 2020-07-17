package com.wpg.wpgblog.service;

import com.github.pagehelper.PageInfo;
import com.wpg.wpgblog.entity.Article;
import com.wpg.wpgblog.vo.ArticleVO;

import java.util.List;

public interface ArticleService {

    int addArticle(Article article);

    int updateArticle(Article article);

    int deleteArticle(int id);

    PageInfo<Article> getMySelfArticle(int id, int page, int offset);

    PageInfo<Article> getAllArticles(int page, int offset);

    PageInfo<Article> getAllNormalArticles(Integer categoryId, int page, int offset);

    PageInfo<Article> getByArticleIds(List<Integer> ids, int page, int offset);

    ArticleVO getByArticleId(Integer articleId);

    List<Article> getNoApprovedArticle();

    int score(int articleId, int score);
}
