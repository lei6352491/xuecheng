package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseView;
import io.swagger.annotations.ApiOperation;

/**
 * @author 杜承旭
 * @ClassNmae: CourseViewControllerApi
 * @Description: TODO
 * @date 2019/11/6 13:54
 * @Version 1.0
 **/
public interface CourseViewControllerApi {

    @ApiOperation(value = "根据课程id查询课程数据信息")
    CourseView findCourseView(String courseCode);

}
