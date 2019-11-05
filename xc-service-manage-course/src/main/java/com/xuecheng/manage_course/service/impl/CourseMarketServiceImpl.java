package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryOneResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseMarketRepository;
import com.xuecheng.manage_course.service.CourseMarketService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 杜承旭
 * @ClassNmae: CourseMarketServiceImpl
 * @Description: TODO
 * @date 2019/10/26 9:04
 * @Version 1.0
 **/

@Service
public class CourseMarketServiceImpl implements CourseMarketService {

    @Autowired
    private CourseMarketRepository courseMarketRepository;

    /**
     * 根据主键id查询课程营销信息
     * */
    @Override
    public ResponseResult findCourseMarket(String courseMarketId) {
        if (StringUtils.isEmpty(courseMarketId))
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);

        Optional<CourseMarket> optionalCourseMarket = courseMarketRepository.findById(courseMarketId);
        if (optionalCourseMarket.isPresent()){
            CourseMarket courseMarket = optionalCourseMarket.get();
            QueryOneResult<CourseMarket> queryOneResult = QueryOneResult.getQueryOneResult(courseMarket);
            return new QueryResponseResult(CommonCode.SUCCESS,queryOneResult);
        }

        return ResponseResult.FAIL();
    }

    /**
     * 保存或者更新课程营销信息
     * */
    @Override
    public ResponseResult saveOrUpdateCourseMarket(CourseMarket courseMarket) {
        if (courseMarket == null || StringUtils.isEmpty(courseMarket.getId()))
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);

        courseMarketRepository.save(courseMarket);

        return ResponseResult.SUCCESS();
    }

}
