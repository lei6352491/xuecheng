package com.xuecheng.manage_course.service;

import com.xuecheng.framework.model.response.QueryResponseResult;

/**
 * @author 杜承旭
 * @ClassNmae: CategoryService
 * @Description: TODO
 * @date 2019/10/25 13:55
 * @Version 1.0
 **/
public interface CategoryService {

    //查询所有的课程分类信息（由树型数据机构显示）
    QueryResponseResult selectAllCategoryNode();
}
