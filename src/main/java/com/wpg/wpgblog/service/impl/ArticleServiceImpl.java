package com.wpg.wpgblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wpg.wpgblog.dao.ArticleDao;
import com.wpg.wpgblog.dao.CommentDao;
import com.wpg.wpgblog.dao.UserDao;
import com.wpg.wpgblog.entity.Article;
import com.wpg.wpgblog.entity.User;
import com.wpg.wpgblog.service.ArticleService;
import com.wpg.wpgblog.service.CommentService;
import com.wpg.wpgblog.vo.ArticleVO;
import com.wpg.wpgblog.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserDao userDao;

    @Override
    public int addArticle(Article article) {
        return articleDao.addNewArticle(article);
    }

    @Override
    public int updateArticle(Article article) {
        return articleDao.updateArticle(article);
    }

    @Override
    public int deleteArticle(int id) {
        return articleDao.deleteArticleById(id);
    }

    @Override
    public PageInfo<Article> getMySelfArticle(int id, int page, int offset) {
        PageHelper.startPage(page, offset);
        List<Article> articles = articleDao.getArticleByUserId(id);

        return new PageInfo<>(articles);
    }


    @Override
    public PageInfo<Article> getAllArticles(int page, int offset) {
        PageHelper.startPage(page, offset);
        List<Article> articles = articleDao.getAllArticles();

        return new PageInfo<>(articles);
    }

    @Override
    public PageInfo<Article> getAllNormalArticles(Integer categoryId, int page, int offset) {
        PageHelper.startPage(page, offset);

        List<Article> articles = articleDao.getAllNormalArticle(categoryId);

        return new PageInfo<>(articles);
    }

    @Override
    public PageInfo<Article> getByArticleIds(List<Integer> ids, int page, int offset) {
        PageHelper.startPage(page, offset);

        List<Article> articles = articleDao.getByArticleIds(ids);

        return new PageInfo<>(articles);

    }

    @Override
    public ArticleVO getByArticleId(Integer articleId) {
        Article article = articleDao.getArticleByArticleId(articleId);
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);

        Integer userId = article.getUserId();
        User user = userDao.findById(userId);

        articleVO.setAvatar(user.getAvatar());
        articleVO.setUsername(user.getUsername());

        List<CommentVO> commentVOList = commentService.getByArticleId(articleId);
        articleVO.setCommentVOList(commentVOList);
        return articleVO;
    }

    @Override
    public List<Article> getNoApprovedArticle() {
        return articleDao.getNoApprovedArticles();
    }

    @Override
    public int score(int articleId, int score) {
        return articleDao.score(articleId, score);
    }
}
