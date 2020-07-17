package com.wpg.wpgblog.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Test
    void getAllCategory() {
        System.out.println(categoryDao.getAllCategory());
    }
}
