package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.course.TeachPlanControllerApi;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.TeachPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 杜承旭
 * @ClassNmae: TeachPlanController
 * @Description: TODO
 * @date 2019/10/24 14:04
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/course")
public class TeachPlanController implements TeachPlanControllerApi {

    @Autowired
    private TeachPlanService teachPlanService;

    /**
     * 根据courseId查询课程计划
     * */
    @Override
    @GetMapping(value = "/teachplan/list/{id}")
    public QueryResponseResult findTeachPlanNode(@PathVariable(value = "id") String courseId) {
        return teachPlanService.findTeachPlanNodeList(courseId);
    }

    /**
     * 添加课程计划
     * */
    @Override
    @PostMapping(value = "/teachplan/add")
    public ResponseResult addTeachPlan(@RequestBody Teachplan teachplan) {
        return teachPlanService.addTeachPlan(teachplan);
    }
}
