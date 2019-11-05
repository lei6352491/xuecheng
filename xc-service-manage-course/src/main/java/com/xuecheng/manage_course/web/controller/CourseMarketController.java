package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.course.CourseMarketControllerApi;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 杜承旭
 * @ClassNmae: CourseMarketController
 * @Description: TODO
 * @date 2019/10/26 8:56
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/course")
public class CourseMarketController implements CourseMarketControllerApi {

    @Autowired
    private CourseMarketService courseMarketService;

    /**
     * 根据主键id查询课程营销信息
     * */
    @Override
    @GetMapping(value = "/courseMarket/get/{id}")
    public ResponseResult findCourseMarket(@PathVariable(value = "id") String courseMarketId) {
        return courseMarketService.findCourseMarket(courseMarketId);
    }

    /**
     * 保存或者更新课程营销信息
     * */
    @Override
    @PostMapping(value = "/courseMarket/save")
    public ResponseResult saveOrUpdateCourseMarket(@RequestBody CourseMarket courseMarket) {
        return courseMarketService.saveOrUpdateCourseMarket(courseMarket);
    }
}
