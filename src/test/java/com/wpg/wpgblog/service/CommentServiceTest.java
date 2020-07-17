package com.wpg.wpgblog.service;

import com.wpg.wpgblog.vo.CommentBackEndVO;
import com.wpg.wpgblog.vo.CommentVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    void getByArticleId() {
    }

}
