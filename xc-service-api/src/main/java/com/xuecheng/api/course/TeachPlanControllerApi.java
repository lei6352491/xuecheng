package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

/**
 * @author 杜承旭
 * @ClassNmae: TeachPlanControllerApi
 * @Description: TODO
 * @date 2019/10/24 14:04
 * @Version 1.0
 **/
public interface TeachPlanControllerApi {

    @ApiOperation("根据课程id查询课程计划")
    QueryResponseResult findTeachPlanNode(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult addTeachPlan(Teachplan teachplan);
}
