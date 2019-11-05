package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

/**
 * @author 杜承旭
 * @ClassNmae: CoursePicControllerApi
 * @Description: TODO
 * @date 2019/10/29 11:06
 * @Version 1.0
 **/
public interface CoursePicControllerApi {

    @ApiOperation("保存课程的图片信息")
    ResponseResult addCoursePic(String courseId,String fileId);

    @ApiOperation("查询课程图片信息")
    CoursePic findCoursePicByCourseId(String courseId);

    @ApiOperation(value = "删除图片有关信息")
    ResponseResult deleteCoursePic(String courseId);
}
