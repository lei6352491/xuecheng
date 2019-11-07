package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import io.swagger.annotations.ApiOperation;

/**
 * @author 杜承旭
 * @ClassNmae: CoursePublishControllerApi
 * @Description: TODO
 * @date 2019/11/7 9:25
 * @Version 1.0
 **/
public interface CoursePublishControllerApi {

    @ApiOperation(value = "获取课程预览的url地址")
    CoursePublishResult preview(String id);
}
