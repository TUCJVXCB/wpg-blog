package com.wpg.wpgblog.dao;

import com.wpg.wpgblog.entity.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Test
    void getByArticleId() {
        List<Comment> byArticleId = commentDao.getByUserId(1);
        System.out.println(byArticleId);
    }
}
