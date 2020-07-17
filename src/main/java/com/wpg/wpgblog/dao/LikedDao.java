package com.wpg.wpgblog.dao;

import org.apache.ibatis.annotations.*;

@Mapper
public interface LikedDao {

    @Select("select value from tb_user_like_article where userId = #{userId} and articleId = #{articleId}")
    Boolean getByUserIdAndArticleId(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    @Update("update tb_user_like_article set value = #{value} where userId = #{userId} and articleId = #{articleId}")
    int updateLiked(@Param("value") Integer value, @Param("userId") Integer userId, @Param("articleId") Integer articleId);

    @Insert("insert into tb_user_like_article(userId, articleId) values(#{userId}, #{articleId})")
    int addLiked(@Param("userId") Integer userId, @Param("articleId") Integer articleId);
}
