package com.xuecheng.search.service;

import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;

/**
 * @author 杜承旭
 * @ClassNmae: CourseSearchService
 * @Description: TODO
 * @date 2019/11/14 16:19
 * @Version 1.0
 **/
public interface CourseSearchService {

    QueryResponseResult courseSearch(Integer page, Integer size, CourseSearchParam courseSearchParam);
}
