package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.response.CoursePublishResult;

/**
 * @author 杜承旭
 * @ClassNmae: CoursePublishService
 * @Description: TODO
 * @date 2019/11/7 9:32
 * @Version 1.0
 **/
public interface CoursePublishService {

    CoursePublishResult preview(String courseCode);

    CoursePublishResult publish(String courseCode);

}
