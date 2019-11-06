package com.xuecheng.framework.domain.course;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 杜承旭
 * @ClassNmae: CourseView
 * @Description: TODO
 * @date 2019/11/6 13:48
 * @Version 1.0
 **/

@Data
@ToString
public class CourseView implements Serializable {

    private CourseBase courseBase;//基础信息
    private CourseMarket courseMarket;//课程营销
    private CoursePic coursePic;//课程图片
    private TeachplanNode teachplanNode;//教学计划

}
