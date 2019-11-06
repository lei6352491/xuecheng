package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseView;

/**
 * @author 杜承旭
 * @ClassNmae: CourseViewService
 * @Description: TODO
 * @date 2019/11/6 14:03
 * @Version 1.0
 **/
public interface CourseViewService {

    CourseView findCourseView(String courseCode);
}
