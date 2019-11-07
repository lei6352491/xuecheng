package com.xuecheng.manage_course.web.controller;

import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.manage_course.service.CoursePublishService;
import com.xuecheng.manage_course.web.feign.CmsPageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杜承旭
 * @ClassNmae: CoursePublishController
 * @Description: TODO
 * @date 2019/11/7 9:27
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/course")
public class CoursePublishController {

    @Autowired
    private CoursePublishService coursePublishService;

    @GetMapping(value = "/preview/{id}")
    public CoursePublishResult preview(@PathVariable(value = "id")String courseCode){

        return coursePublishService.preview(courseCode);
    }
}
