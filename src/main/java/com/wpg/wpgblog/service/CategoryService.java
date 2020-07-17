package com.wpg.wpgblog.service;

import com.wpg.wpgblog.entity.Category;
import com.wpg.wpgblog.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    List<CategoryVO> getAllCategory();
}
