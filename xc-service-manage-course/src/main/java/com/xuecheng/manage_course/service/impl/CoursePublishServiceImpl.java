package com.xuecheng.manage_course.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.CmsPostPageResult;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.*;
import com.xuecheng.manage_course.model.CoursePublish;
import com.xuecheng.manage_course.service.CoursePublishService;
import com.xuecheng.manage_course.web.feign.CmsPageFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * @author 杜承旭
 * @ClassNmae: CoursePublishServiceImpl
 * @Description: TODO
 * @date 2019/11/7 9:33
 * @Version 1.0
 **/

@Service
@ConfigurationProperties(prefix = "course-publish")
public class CoursePublishServiceImpl implements CoursePublishService {

    @Autowired
    private CoursePublish coursePublish;

    @Autowired
    private CmsPageFeign cmsPageFeign;

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Autowired
    private CoursePicRepository coursePicRepository;

    @Autowired
    private CourseMarketRepository courseMarketRepository;

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private CoursePubRepository coursePubRepository;

    /***
     * 课程信息预览
     */
    @Override
    public CoursePublishResult preview(String courseCode) {
        String dataUrl = coursePublish.getCmsPage().getDataUrl();
        coursePublish.getCmsPage().setDataUrl(dataUrl + courseCode);
        //保存或更新页面信息
        CmsPageResult cmsPageResult = cmsPageFeign.add(coursePublish.getCmsPage());
        //重新初始化dataUrl路劲
        coursePublish.getCmsPage().setDataUrl(dataUrl);

        //*判断页面是否保存成功
        //-若成功返回previewUrl
        //-若失败放回null
        if (cmsPageResult.isSuccess()){
            CmsPage cmsPage = cmsPageResult.getCmsPage();
            return new CoursePublishResult(CommonCode.SUCCESS,coursePublish.getPreviewUrl() + cmsPage.getPageId());
        }else {
            return new CoursePublishResult(CommonCode.FAIL,null);
        }
    }

    /**
     * 发布课程信息页面
     * */
    @Override
    @Transactional
    public CoursePublishResult publish(String courseCode) {
        //定义cmsPage页面名称为课程编号
        CmsPage cmsPage = coursePublish.getCmsPage();
        cmsPage.setPageName(courseCode + ".html");

        //拼接访问数据源地址
        String dataUrl = coursePublish.getCmsPage().getDataUrl();
        coursePublish.getCmsPage().setDataUrl(dataUrl + courseCode);

        try {
            //查询课程信息
            Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseCode);
            if (!courseBaseOptional.isPresent())
                ExceptionCast.cast(CourseCode.COURSE_COURSEMSGISNULL);
            CourseBase courseBase = courseBaseOptional.get();

            //调用cms系统的页面一键发布功能
            CmsPostPageResult cmsPostPageResult = cmsPageFeign.postPageQuick(cmsPage);
            if (!cmsPostPageResult.isSuccess())
                ExceptionCast.cast(CourseCode.COURSE_PUBLISH_ERROR);
            String pageUrl = cmsPostPageResult.getPageUrl();

            //更新课程信息的发布状态
            courseBase.setStatus("202002");
            courseBaseRepository.save(courseBase);

            //发布成功，向coursePub表中添加课程的索引信息
            //-获取coursePub信息
            CoursePub coursePub = createCoursePub(courseCode);
            //-保存coursePub信息
            coursePubRepository.save(coursePub);

            //返回结果集
            return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
        }catch (Exception e){
            e.printStackTrace();
            return new CoursePublishResult(CommonCode.FAIL,null);
        }finally {
            //重新初始化dataUrl路劲
            coursePublish.getCmsPage().setDataUrl(dataUrl);
        }
    }


    /**
     * 获取coursePub对象
     * */
    private CoursePub createCoursePub(String courseCode){
        //创建coursePub JavaBean 对象
        CoursePub coursePub = new CoursePub();

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

        //课程信息封装到coursePub中
        if (courseBase != null)
            BeanUtils.copyProperties(courseBase,coursePub);
        if (coursePic != null)
            BeanUtils.copyProperties(coursePic,coursePub);
        if (courseMarket != null)
            BeanUtils.copyProperties(courseMarket,coursePub);
        if (teachplanNode != null)
            coursePub.setTeachplan(JSON.toJSONString(teachplanNode));

        //补全coursePub信息
        coursePub.setTimestamp(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(coursePub.getTimestamp());
        coursePub.setPubTime(dateString);

        return coursePub;
    }
}
