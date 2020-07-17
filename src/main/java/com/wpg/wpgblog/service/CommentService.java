package com.wpg.wpgblog.service;

import com.wpg.wpgblog.entity.Comment;
import com.wpg.wpgblog.vo.CommentBackEndVO;
import com.wpg.wpgblog.vo.CommentVO;

import java.util.List;

public interface CommentService {

    List<CommentVO> getByArticleId(Integer id);

    int addComment(Comment comment);

    /**
     * 给后台用的CommentVO
     * @param id
     * @return
     */
    List<CommentBackEndVO> getCommentByUserId(Integer id);

    List<CommentBackEndVO> getCommentByArticleId(Integer articleId);

    List<CommentBackEndVO> getAll();

    int deleteCommentById(int id);
}
