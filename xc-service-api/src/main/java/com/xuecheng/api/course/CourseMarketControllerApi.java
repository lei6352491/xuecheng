package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

/**
 * @author 杜承旭
 * @ClassNmae: CourseMarketControllerApi
 * @Description: TODO
 * @date 2019/10/26 8:54
 * @Version 1.0
 **/
public interface CourseMarketControllerApi {

    @ApiOperation(value = "根据主键id查询课程营销信息")
    ResponseResult findCourseMarket(String courseMarketId);

    @ApiOperation(value = "保存或者更新课程营销信息")
    ResponseResult saveOrUpdateCourseMarket(CourseMarket courseMarket);

}
