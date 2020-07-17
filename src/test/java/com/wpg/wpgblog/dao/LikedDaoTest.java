package com.wpg.wpgblog.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikedDaoTest {

    @Autowired
    private LikedDao likedDao;

    @Test
    void getByUserIdAndArticleId() {
        Boolean result = likedDao.getByUserIdAndArticleId(2, 1);
        System.out.println(result);
    }

    @Test
    void getByUserIdAndArticleId2() {
        likedDao.updateLiked(1, 1, 1);
    }

    @Test
    void get1() {
        likedDao.addLiked(1, 3);
    }
}
