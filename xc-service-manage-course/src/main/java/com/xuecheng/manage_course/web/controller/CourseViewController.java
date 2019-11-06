package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.course.CourseViewControllerApi;
import com.xuecheng.framework.domain.course.CourseView;
import com.xuecheng.manage_course.service.CourseViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杜承旭
 * @ClassNmae: CourseViewController
 * @Description: TODO
 * @date 2019/11/6 14:01
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/courseview")
public class CourseViewController implements CourseViewControllerApi {

    @Autowired
    private CourseViewService courseViewService;

    @Override
    @GetMapping(value = "/get/{id}")
    public CourseView findCourseView(@PathVariable(value = "id") String courseCode) {
        return courseViewService.findCourseView(courseCode);
    }
}
