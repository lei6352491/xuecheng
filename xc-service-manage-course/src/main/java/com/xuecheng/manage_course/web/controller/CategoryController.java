package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.course.CategoryControllerApi;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杜承旭
 * @ClassNmae: CategoryController
 * @Description: TODO
 * @date 2019/10/25 14:00
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/category")
public class CategoryController implements CategoryControllerApi {

    @Autowired
    private CategoryService categoryService;

    @Override
    @GetMapping(value = "/list")
    public QueryResponseResult findAllCategoryNode() {
        return categoryService.selectAllCategoryNode();
    }
}
