package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 杜承旭
 * @ClassNmae: TeachplanMapper
 * @Description: TODO
 * @date 2019/10/24 10:44
 * @Version 1.0
 **/

@Mapper
public interface TeachplanMapper {

    TeachplanNode selectTeachPlanNodeListByCourseId(String courseId);

    Teachplan selectParentTeachPlanByCourseId(String courseId);

}
