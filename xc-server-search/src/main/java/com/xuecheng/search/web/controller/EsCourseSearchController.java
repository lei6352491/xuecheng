package com.xuecheng.search.web.controller;

import com.xuecheng.api.search.EsCourseSearchControllerApi;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.search.service.CourseSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杜承旭
 * @ClassNmae: EsCourseSearchController
 * @Description: TODO
 * @date 2019/11/14 16:08
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/search/course")
public class EsCourseSearchController implements EsCourseSearchControllerApi {

    @Autowired
    private CourseSearchService courseSearchService;

    /**
     * 搜索课程信息
     * */
    @GetMapping(value = "/list/{page}/{size}")
    @Override
    public QueryResponseResult searchCourse
            (@PathVariable(value = "page") Integer page,@PathVariable(value = "size") Integer size, CourseSearchParam courseSearchParam) {
        return courseSearchService.courseSearch(page,size,courseSearchParam);
    }
}
