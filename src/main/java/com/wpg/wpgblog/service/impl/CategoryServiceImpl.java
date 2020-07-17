package com.wpg.wpgblog.service.impl;

import com.wpg.wpgblog.dao.CategoryDao;
import com.wpg.wpgblog.entity.Category;
import com.wpg.wpgblog.service.CategoryService;
import com.wpg.wpgblog.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<CategoryVO> getAllCategory() {
        List<Category> categoryList = categoryDao.getAllCategory();
        return categoryList.stream().map(e -> new CategoryVO(e.getId(), e.getName(), UUID.randomUUID().toString())).collect(Collectors.toList());
    }
}
