package com.wpg.wpgblog.dao;

import com.wpg.wpgblog.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDao {

    @Select("select * from tb_comment where articleId = #{articleId} and state = 0 order by id desc")
    List<Comment> getByArticleId(@Param("articleId") Integer articleId);

    @Insert("insert into tb_comment(userId, articleId, content) values(#{userId}, #{articleId}, #{content})")
    int addComment(Comment comment);

    @Select("select * from tb_comment where userId = #{userId} and state = 0 order by id desc")
    List<Comment> getByUserId(@Param("userId") Integer userId);

    @Select("select * from tb_comment where state = 0 order by id desc")
    List<Comment> getAll();

    @Update("update tb_comment set state = 1 where id = #{id}")
    int deleteByCommentId(@Param("id") Integer id);
}
