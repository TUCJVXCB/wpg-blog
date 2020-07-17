package com.wpg.wpgblog.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollectDaoTest {

    @Autowired
    private CollectDao collectDao;

    @Test
    void getCollectedArticleIdByUserId() {
        List<Integer> result = collectDao.getCollectedArticleIdByUserId(1);
        System.out.println(result);
    }
}
