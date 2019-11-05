package com.xuecheng.api.course;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.ApiOperation;

/**
 * @author 杜承旭
 * @ClassNmae: CategoryControllerApi
 * @Description: TODO
 * @date 2019/10/25 14:00
 * @Version 1.0
 **/
public interface CategoryControllerApi {

    @ApiOperation(value = "查询所有课程分类信息")
    QueryResponseResult findAllCategoryNode();
}
