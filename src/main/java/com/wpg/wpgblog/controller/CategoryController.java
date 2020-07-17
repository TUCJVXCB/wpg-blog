package com.wpg.wpgblog.controller;

import com.wpg.wpgblog.common.ApiResponse;
import com.wpg.wpgblog.common.Status;
import com.wpg.wpgblog.service.CategoryService;
import com.wpg.wpgblog.vo.CategoryVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    @ApiOperation("获得所有文章种类")
    public ApiResponse list() {
        List<CategoryVO> categoryVOList = categoryService.getAllCategory();
        if (!ObjectUtils.isEmpty(categoryVOList)) {
            return ApiResponse.ofSuccess(categoryVOList);
        }else {
            return ApiResponse.ofStatus(Status.ERROR);
        }
    }
}
