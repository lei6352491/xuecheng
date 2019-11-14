package com.xuecheng.api.search;

import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.ApiOperation;

/**
 * @author 杜承旭
 * @ClassNmae: EsCourseSearchControllerApi
 * @Description: TODO
 * @date 2019/11/14 16:04
 * @Version 1.0
 **/
public interface EsCourseSearchControllerApi {

    @ApiOperation(value = "搜索课程")
    QueryResponseResult searchCourse(Integer page, Integer size, CourseSearchParam courseSearchParam);
}
