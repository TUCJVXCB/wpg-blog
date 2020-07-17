package com.wpg.wpgblog.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CollectDao {
    @Select("select value from tb_user_collect_article where userId = #{userId} and articleId = #{articleId}")
    Boolean getByUserIdAndArticleId(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    @Update("update tb_user_collect_article set value = #{value} where userId = #{userId} and articleId = #{articleId}")
    int updateLiked(@Param("value") Integer value, @Param("userId") Integer userId, @Param("articleId") Integer articleId);

    @Insert("insert into tb_user_collect_article(userId, articleId) values(#{userId}, #{articleId})")
    int addLiked(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    @Select("select articleId from tb_user_collect_article where userId = #{userId} and value = 1")
    List<Integer> getCollectedArticleIdByUserId(@Param("userId") Integer userId);
}
