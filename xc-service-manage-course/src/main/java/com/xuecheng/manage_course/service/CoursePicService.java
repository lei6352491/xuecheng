package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.model.response.ResponseResult;

/**
 * @author 杜承旭
 * @ClassNmae: CoursePicService
 * @Description: TODO
 * @date 2019/10/29 11:12
 * @Version 1.0
 **/
public interface CoursePicService {

    ResponseResult addCoursePic(String courseId,String fileId);

    CoursePic findCoursePic(String courseId);

    ResponseResult deleteCoursePic(String courseId);

}
