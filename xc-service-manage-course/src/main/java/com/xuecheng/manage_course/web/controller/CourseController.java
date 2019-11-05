package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 杜承旭
 * @ClassNmae: CourseController
 * @Description: TODO
 * @date 2019/10/25 15:04
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/course")
public class CourseController implements CourseControllerApi {

    @Autowired
    private CourseService courseService;

    @Override
    @PostMapping(value = "/coursebase/add")
    public ResponseResult addCourse(@RequestBody CourseBase courseBase) {
        return courseService.addCourse(courseBase);
    }

    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseBaseList
            (@PathVariable Integer page,@PathVariable Integer size, CourseListRequest courseListRequest) {

        return courseService.findCourseBaseList(page,size,courseListRequest);
    }

    @Override
    @GetMapping(value = "/coursebase/get/{id}")
    public QueryResponseResult findCourseBaseByCourseId(@PathVariable(value = "id") String courseId) {
        return courseService.findCourseBase(courseId);
    }

    @Override
    @PutMapping(value = "/coursebase/update")
    public ResponseResult updateCourseBase(@RequestBody CourseBase courseBase) {
        return courseService.updateCourseBase(courseBase);
    }


}
