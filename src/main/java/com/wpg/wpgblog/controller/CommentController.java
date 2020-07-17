package com.wpg.wpgblog.controller;

import com.wpg.wpgblog.common.ApiResponse;
import com.wpg.wpgblog.common.Status;
import com.wpg.wpgblog.dao.UserDao;
import com.wpg.wpgblog.entity.Comment;
import com.wpg.wpgblog.entity.User;
import com.wpg.wpgblog.service.CommentService;
import com.wpg.wpgblog.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    @ApiOperation("添加评论")
    public ApiResponse addComment(@RequestBody Comment comment, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        User user = userDao.findByEmail(jwtUtil.parseJWT(token).getId());
        comment.setUserId(user.getId());
        int result = commentService.addComment(comment);

        if (result > 0) {
            return ApiResponse.ofSuccess();
        }else {
            return ApiResponse.ofStatus(Status.ERROR);
        }
    }

    @GetMapping("/list")
    @ApiOperation("获得所有评论")
    public ApiResponse list(@RequestParam(value = "email", required = false) String email,
                            @RequestParam(value = "articleId", required = false) Integer articleId) {
        if (email == null && articleId == null) {
            return ApiResponse.ofSuccess(commentService.getAll());
        }else if (email != null) {
            return ApiResponse.ofSuccess(commentService.getCommentByUserId(userDao.findByEmail(email).getId()));
        }else {
            return ApiResponse.ofSuccess(commentService.getCommentByArticleId(articleId));
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除评论")
    public ApiResponse deleteById(@PathVariable("id") Integer id) {
        int result = commentService.deleteCommentById(id);
        if (result > 0) {
            return ApiResponse.ofSuccess();
        }else {
            return ApiResponse.ofStatus(Status.ERROR);
        }
    }
}
