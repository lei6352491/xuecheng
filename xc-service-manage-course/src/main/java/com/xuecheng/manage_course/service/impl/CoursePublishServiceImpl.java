package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.model.CoursePublish;
import com.xuecheng.manage_course.service.CoursePublishService;
import com.xuecheng.manage_course.web.feign.CmsPageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

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
}
