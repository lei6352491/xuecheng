package com.xuecheng.manage_course.model;

import com.xuecheng.framework.domain.cms.CmsPage;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 杜承旭
 * @ClassNmae: CoursePublish
 * @Description: TODO
 * @date 2019/11/7 15:48
 * @Version 1.0
 **/

@Component
@Data
@ToString
@ConfigurationProperties(prefix = "course-publish")
public class CoursePublish {

    //页面对象
    private CmsPage cmsPage;

    //@Value("${course-publish.previewUrl}")
    private String previewUrl;


}
