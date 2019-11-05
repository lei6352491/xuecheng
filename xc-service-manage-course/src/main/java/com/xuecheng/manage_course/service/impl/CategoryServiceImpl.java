package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryOneResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_course.dao.CategoryMapper;
import com.xuecheng.manage_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杜承旭
 * @ClassNmae: CategoryServiceImpl
 * @Description: TODO
 * @date 2019/10/25 13:57
 * @Version 1.0
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public QueryResponseResult selectAllCategoryNode() {
        CategoryNode categoryNode = categoryMapper.selectAllCategoryNode();
        if (categoryNode == null)
            return null;
        //去除子分类中children字段为[]，把children字段[]初始化为null
        List<CategoryNode> list = new ArrayList<>();
        list.add(categoryNode);
        initializationChildren(list);
        CategoryNode newCategoryNode = list.get(0);
        QueryOneResult<CategoryNode> queryOneResult = new QueryOneResult<>();
        queryOneResult.setData(newCategoryNode);
        return new QueryResponseResult(CommonCode.SUCCESS,queryOneResult);
    }

    //初始化CategoryNode中children字段为[]
    private void initializationChildren(List<CategoryNode> list){
        list.stream().forEach( s -> {
            if ("0".equals(s.getIsleaf())){
                initializationChildren(s.getChildren());
            }
            if ("1".equals(s.getIsleaf())){
                s.setChildren(null);
            }
        });
    }
}
