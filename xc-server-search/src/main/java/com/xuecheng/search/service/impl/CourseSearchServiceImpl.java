package com.xuecheng.search.service.impl;

import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.search.service.CourseSearchService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 杜承旭
 * @ClassNmae: CourseSearchServiceImpl
 * @Description: TODO
 * @date 2019/11/14 16:20
 * @Version 1.0
 **/

@Service
public class CourseSearchServiceImpl implements CourseSearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public QueryResponseResult courseSearch(Integer page, Integer size, CourseSearchParam courseSearchParam) {

        return null;
    }
}
