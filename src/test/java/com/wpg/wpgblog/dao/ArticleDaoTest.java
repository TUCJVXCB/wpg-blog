package com.wpg.wpgblog.dao;

import com.wpg.wpgblog.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleDaoTest {

    @Autowired
    private ArticleDao articleDao;

    @Test
    void addNewArticle() {
        Article article = new Article();
        article.setTitle("dasd");
        article.setCategoryId(1);
        article.setHtmlContent("3213");
        article.setUserId(2);
        article.setMdContent("31232");
        articleDao.addNewArticle(article);
    }

    @Test
    void updateArticle() {
        Article article = new Article();
        article.setId(4);
        article.setTitle("321312312312312");
        article.setCategoryId(1);
        article.setHtmlContent("321312312312312");
        article.setUserId(2);
        article.setMdContent("321312312312312");
        articleDao.updateArticle(article);
    }

    @Test
    void getArticleByState() {
    }

    @Test
    void updateArticleStateById() {
    }

    @Test
    void getArticleById() {
        Article article = articleDao.getArticleByArticleId(1);
        System.out.println(article);
    }

    @Test
    void getArticleByArticleId() {
    }

    @Test
    void getAllArticles() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        List<Article> result = articleDao.getByArticleIds(list);
        System.out.println(result);
    }

    @Test
    void getArticleByUserId() {
        System.out.println(articleDao.getArticleByUserId(1));
    }

    @Test
    void delelteById() {
        System.out.println(articleDao.deleteArticleById(2));
    }

    @Test
    void test1() {
        System.out.println(articleDao.getAllNormalArticle(1));
    }

    @Test
    void test2() {
        List<Article> noApprovedArticles = articleDao.getNoApprovedArticles();
        System.out.println(noApprovedArticles);
    }
}
