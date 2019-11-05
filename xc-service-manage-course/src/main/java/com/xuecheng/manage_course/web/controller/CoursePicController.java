package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.course.CoursePicControllerApi;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CoursePicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.ValueExp;

/**
 * @author 杜承旭
 * @ClassNmae: CoursePicController
 * @Description: TODO
 * @date 2019/10/29 11:10
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/course")
public class CoursePicController implements CoursePicControllerApi {

    @Autowired
    private CoursePicService coursePicService;

    @PostMapping(value = "/coursepic/add")
    @Override
    public ResponseResult addCoursePic(String courseId,String pic){
        return coursePicService.addCoursePic(courseId,pic);
    }

    @Override
    @GetMapping(value = "coursepic/get/{courseId}")
    public CoursePic findCoursePicByCourseId(@PathVariable String courseId) {
        return coursePicService.findCoursePic(courseId);
    }

    @Override
    @DeleteMapping(value = "/coursepic/delete")
    public ResponseResult deleteCoursePic(String courseId) {
        return coursePicService.deleteCoursePic(courseId);
    }


}
