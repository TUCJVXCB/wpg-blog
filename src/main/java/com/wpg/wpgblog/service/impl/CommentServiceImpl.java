package com.wpg.wpgblog.service.impl;

import com.wpg.wpgblog.dao.CommentDao;
import com.wpg.wpgblog.dao.UserDao;
import com.wpg.wpgblog.entity.Comment;
import com.wpg.wpgblog.entity.User;
import com.wpg.wpgblog.service.CommentService;
import com.wpg.wpgblog.vo.CommentBackEndVO;
import com.wpg.wpgblog.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<CommentVO> getByArticleId(Integer articleId) {
        List<Comment> commentList = commentDao.getByArticleId(articleId);

        List<CommentVO> commentVOList = new ArrayList<>();

        for (Comment comment : commentList) {
            User user = userDao.findById(comment.getUserId());
            CommentVO commentVO = new CommentVO(comment.getContent(), user.getAvatar(), user.getUsername());
            commentVOList.add(commentVO);
        }

        return commentVOList;
    }

    @Override
    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    @Override
    public List<CommentBackEndVO> getCommentByUserId(Integer id) {
        List<Comment> commentList = commentDao.getByUserId(id);

        return commentList.stream().map(e -> new CommentBackEndVO(e.getId(), userDao.findById(e.getUserId()).getUsername(),
                e.getArticleId(), e.getContent())).collect(Collectors.toList());
    }

    @Override
    public List<CommentBackEndVO> getCommentByArticleId(Integer articleId) {
        List<Comment> commentList = commentDao.getByArticleId(articleId);

        return commentList.stream().map(e -> new CommentBackEndVO(e.getId(), userDao.findById(e.getUserId()).getUsername(),
                e.getArticleId(), e.getContent())).collect(Collectors.toList());
    }

    @Override
    public List<CommentBackEndVO> getAll() {
        List<Comment> commentList = commentDao.getAll();

        return commentList.stream().map(e -> new CommentBackEndVO(e.getId(), userDao.findById(e.getUserId()).getUsername(),
                e.getArticleId(), e.getContent())).collect(Collectors.toList());
    }

    @Override
    public int deleteCommentById(int id) {
        return commentDao.deleteByCommentId(id);
    }
}
