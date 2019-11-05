package com.xuecheng.manage_course.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.framework.utils.EmptyStringToNullUtil;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.CourseMapper;
import com.xuecheng.manage_course.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 杜承旭
 * @ClassNmae: CourseServiceImpl
 * @Description: TODO
 * @date 2019/10/25 14:55
 * @Version 1.0
 **/

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Autowired
    private CourseMapper courseMapper;

    /**
     * 保存课程信息
     * */
    @Override
    public ResponseResult addCourse(CourseBase courseBase) {
        //课程状态为制作中
        courseBase.setStatus("202001");

        //去除空串
        courseBase = EmptyStringToNullUtil.emptyStringToNullUtil(courseBase);

        courseBaseRepository.save(courseBase);
        return ResponseResult.SUCCESS();
    }

    /**
     * 分页查询课程信息列表
     * */
    @Override
    public QueryResponseResult findCourseBaseList(Integer page, Integer size, CourseListRequest courseListRequest) {
        if (courseListRequest == null){
            courseListRequest = new CourseListRequest();
        }

        //当分页信息不存在时，初始化分页信息
        if (page == null || page <= 0){
            page = 1;
        }
        if (size == null || size <= 0){
            size = 7;
        }
        //设置mybatis分页参数
        PageHelper.startPage(page,size);

        //查询
        Page<CourseBase> courseBasePage = courseMapper.findCourseBaseList(courseListRequest);

        if (courseBasePage != null){
            long total = courseBasePage.getTotal();
            List<CourseBase> courseBaseList = courseBasePage.getResult();
            QueryResult<CourseBase> queryResult = new QueryResult<>();
            queryResult.setTotal(total);
            queryResult.setList(courseBaseList);
            return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        }

        return null;
    }

    /**
     * 根据主键查询课程的基本信息
     * */
    @Override
    public QueryResponseResult findCourseBase(String courseId) {
        CourseBase courseBase = courseMapper.findCourseBaseById(courseId);
        QueryOneResult<CourseBase> queryOneResult = new QueryOneResult<>();
        queryOneResult.setData(courseBase);
        return new QueryResponseResult(CommonCode.SUCCESS,queryOneResult);
    }

    /**
     * 更新课程的基本信息
     * */
    @Override
    public ResponseResult updateCourseBase(CourseBase courseBase) {
        if (courseBase == null || StringUtils.isEmpty(courseBase.getCompanyId()))
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        courseBaseRepository.save(courseBase);
        return ResponseResult.SUCCESS();
    }
}
