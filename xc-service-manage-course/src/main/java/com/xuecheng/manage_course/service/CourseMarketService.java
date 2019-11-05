package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.ResponseResult;

/**
 * @author 杜承旭
 * @ClassNmae: CourseMarketService
 * @Description: TODO
 * @date 2019/10/26 9:02
 * @Version 1.0
 **/
public interface CourseMarketService {

    ResponseResult findCourseMarket(String courseMarketId);

    ResponseResult saveOrUpdateCourseMarket(CourseMarket courseMarket);

}
