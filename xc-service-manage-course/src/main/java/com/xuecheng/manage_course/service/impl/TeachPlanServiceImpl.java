package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryOneResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.TeachPlanRepository;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.service.TeachPlanService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author 杜承旭
 * @ClassNmae: TeachPlanServiceImpl
 * @Description: TODO
 * @date 2019/10/24 13:53
 * @Version 1.0
 **/

@Service
public class TeachPlanServiceImpl implements TeachPlanService {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private TeachPlanRepository teachPlanRepository;

    /**
     * 根据courseId查询课程计划
     * */
    @Override
    public QueryResponseResult findTeachPlanNodeList(String courseId) {
        TeachplanNode teachplanNode = teachplanMapper.selectTeachPlanNodeListByCourseId(courseId);
        QueryOneResult<TeachplanNode> queryOneResult = new QueryOneResult<>();
        queryOneResult.setData(teachplanNode);
        return new QueryResponseResult(CommonCode.SUCCESS,queryOneResult);
    }

    /**
     * 添加课程计划
     * */
    @Override
    @Transactional
    public ResponseResult addTeachPlan(Teachplan teachplan) {
        if (teachplan == null)
            teachplan = new Teachplan();
        if (StringUtils.isEmpty(teachplan.getCourseid())){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        //查询该课程计划的父根节点信息
        //Teachplan parentTeachPlan = teachplanMapper.selectParentTeachPlanByCourseId(teachplan.getCourseid());
        Teachplan parentTeachPlan = teachPlanRepository.findByCourseidAndParentid(teachplan.getCourseid(), "0");

        //判断添加课程计划的父节点是否为根节点
        if (teachplan.getParentid() == null || teachplan.getParentid().equals(""))
            teachplan.setParentid(parentTeachPlan.getId());

        //设置主键
        teachplan.setId(UUID.randomUUID().toString().replace("-",""));

        //保存课程计划
        teachPlanRepository.save(teachplan);
        return ResponseResult.SUCCESS();
    }
}
