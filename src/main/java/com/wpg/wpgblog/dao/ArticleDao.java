package com.wpg.wpgblog.dao;

import com.wpg.wpgblog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleDao {

    int addNewArticle(Article article);

    int updateArticle(Article article);

    Article getArticleByState(int state);

    int updateArticleStateById(int id);

    Article getArticleByArticleId(int id);

    List<Article> getAllArticles();

    List<Article> getArticleByUserId(int id);

    int deleteArticleById(int id);

    List<Article> getAllNormalArticle(@Param("categoryId") Integer categoryId);

    Article getByArticleId(@Param("id") Integer articleId);

    List<Article> getByArticleIds(@Param("ids") List<Integer> ids);

    List<Article> getNoApprovedArticles();

    int score(@Param("id") Integer id, @Param("score") Integer score);
}
