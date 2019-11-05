package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

/**
 * @author 杜承旭
 * @ClassNmae: CourseService
 * @Description: TODO
 * @date 2019/10/25 14:49
 * @Version 1.0
 **/
public interface CourseService {

    //保存课程信息
    ResponseResult addCourse(CourseBase courseBase);

    //分页查询课程信息列表
    QueryResponseResult findCourseBaseList(Integer page, Integer size, CourseListRequest courseListRequest);

    //根据主键id查询课程信息
    QueryResponseResult findCourseBase(String courseId);

    //更新课程的基本信息
    ResponseResult updateCourseBase(CourseBase courseBase);
}
