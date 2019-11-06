package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.CourseMarketRepository;
import com.xuecheng.manage_course.dao.CoursePicRepository;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.service.CourseViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author 杜承旭
 * @ClassNmae: CourseViewServiceImpl
 * @Description: TODO
 * @date 2019/11/6 14:05
 * @Version 1.0
 **/

@Service
public class CourseViewServiceImpl implements CourseViewService {

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Autowired
    private CoursePicRepository coursePicRepository;

    @Autowired
    private CourseMarketRepository courseMarketRepository;

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Override
    public CourseView findCourseView(String courseCode) {
        //查询课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseCode);
        CourseBase courseBase = null;
        if (courseBaseOptional.isPresent())
            courseBase = courseBaseOptional.get();

        //查询课程图片信息
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(courseCode);
        CoursePic coursePic = null;
        if (coursePicOptional.isPresent())
            coursePic = coursePicOptional.get();

        //查询课程营销信息
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(courseCode);
        CourseMarket courseMarket = null;
        if (courseMarketOptional.isPresent())
            courseMarket = courseMarketOptional.get();

        //查询课程计划信息
        TeachplanNode teachplanNode = teachplanMapper.selectTeachPlanNodeListByCourseId(courseCode);
        //把子节点children的[]替换成null
        initializationChildren(teachplanNode);

        //返回结果数据
        CourseView courseView = new CourseView();
        courseView.setCourseBase(courseBase);
        courseView.setCoursePic(coursePic);
        courseView.setCourseMarket(courseMarket);
        courseView.setTeachplanNode(teachplanNode);

        return courseView;
    }

    private void initializationChildren(TeachplanNode teachplanNode){
        if (teachplanNode != null && teachplanNode.getChildren() != null && teachplanNode.getChildren().size() > 0){
            List<TeachplanNode> teachplanNodes = teachplanNode.getChildren();
            teachplanNodes.stream().forEach(s -> {
                if (s.getChildren().size()>0){
                    initializationChildren(s);
                }else {
                    s.setChildren(null);
                }
            });
        }
    }
}
