package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

/**
 * @author 杜承旭
 * @ClassNmae: CourseControllerApi
 * @Description: TODO
 * @date 2019/10/25 15:03
 * @Version 1.0
 **/
public interface CourseControllerApi {

    @ApiOperation(value = "添加课程信息")
    ResponseResult addCourse(CourseBase courseBase);

    @ApiOperation(value = "分页查询课程信息列表")
    QueryResponseResult findCourseBaseList(Integer page, Integer size, CourseListRequest courseListRequest);

    @ApiOperation(value = "根据课程的主键id查询课程信息")
    QueryResponseResult findCourseBaseByCourseId(String courseId);

    @ApiOperation(value = "更新课程的基本信息")
    ResponseResult updateCourseBase(CourseBase courseBase);
}
