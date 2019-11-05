package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

/**
 * @author 杜承旭
 * @ClassNmae: TeachPlanService
 * @Description: TODO
 * @date 2019/10/24 13:49
 * @Version 1.0
 **/

public interface TeachPlanService {

    //根据课程id获取课程计划
    QueryResponseResult findTeachPlanNodeList(String courseId);

    //添加课程计划
    ResponseResult addTeachPlan(Teachplan teachplan);
}
