package com.wpg.wpgblog.dao;

import com.wpg.wpgblog.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryDao {

    @Select("select * from tb_category")
    List<Category> getAllCategory();
}
