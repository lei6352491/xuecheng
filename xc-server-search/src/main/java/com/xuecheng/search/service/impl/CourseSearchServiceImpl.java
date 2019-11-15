package com.xuecheng.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.search.service.CourseSearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author 杜承旭
 * @ClassNmae: CourseSearchServiceImpl
 * @Description: TODO
 * @date 2019/11/14 16:20
 * @Version 1.0
 **/

@Service
public class CourseSearchServiceImpl implements CourseSearchService {

    @Value("${xuecheng.elasticsearch.course.index}")
    private String index;
    @Value("${xuecheng.elasticsearch.course.type}")
    private String type;
    @Value("${xuecheng.elasticsearch.course.source_field}")
    private String source_field;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 全文检索
     * */
    @Override
    public QueryResponseResult courseSearch(Integer page, Integer size, CourseSearchParam courseSearchParam) {
        //创建查询索引对象
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        //创建搜索数据源对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //使用boolean方式查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //设置显示的字段
        String[] source_fields = source_field.split(",");
        searchSourceBuilder.fetchSource(source_fields,new String[]{});
        //关键字采用多个匹配分词查询,若为空查询所有
        if (StringUtils.isNotEmpty(courseSearchParam.getKeyword())){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery
                    (courseSearchParam.getKeyword(), "name", "teachplan", "description");
            //设置出现的占比
            multiMatchQueryBuilder.minimumShouldMatch("95%");
            //设置字段的权重
            multiMatchQueryBuilder.field("name",10);
            //把匹配查询田间到boolean查询方式中
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }else {
            MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
            boolQueryBuilder.must(matchAllQueryBuilder);
        }
        //设置查询结果的过滤
        if (StringUtils.isNotEmpty(courseSearchParam.getMt())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt",courseSearchParam.getMt()));
        }
        if (StringUtils.isNotEmpty(courseSearchParam.getSt())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("st",courseSearchParam.getSt()));
        }
        if (StringUtils.isNotEmpty(courseSearchParam.getGrade())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade",courseSearchParam.getGrade()));
        }
        //设置分页
        if(page<=0){ page = 1; }
        if(size<=0){ size = 20; }
        Integer start = (page - 1) * size;
        searchSourceBuilder.from(start);
        searchSourceBuilder.size(size);
        //把boolean查询方式添加到查询数据源中
        searchSourceBuilder.query(boolQueryBuilder);
        //高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        //设置高亮的字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        //把高亮设置添加到数据源中
        searchSourceBuilder.highlighter(highlightBuilder);
        //把数据源对象添加到搜索请求对象中
        searchRequest.source(searchSourceBuilder);
        //执行搜索
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (searchResponse != null){
            SearchHits searchResponseHits = searchResponse.getHits();
            long totalHits = searchResponseHits.getTotalHits();
            SearchHit[] searchHits = searchResponseHits.getHits();
            ArrayList<CoursePub> list = new ArrayList<>();
            for (SearchHit searchHit : searchHits){
                //创建封装数据对象
                CoursePub coursePub = new CoursePub();
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                String string = JSON.toJSONString(sourceAsMap);
                CoursePub coursePubResponse = JSON.parseObject(string, CoursePub.class);
                //获取高亮
                Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                if (highlightFields != null && highlightFields.size() > 0){
                    HighlightField highlightField = highlightFields.get("name");
                    if (highlightField != null){
                        Text[] texts = highlightField.getFragments();
                        if (texts != null && texts.length > 0){
                            StringBuffer stringBuffer = new StringBuffer();
                            for (Text text : texts){
                                stringBuffer.append(text.string());
                            }
                            coursePubResponse.setName(stringBuffer.toString());
                        }
                    }
                }
                list.add(coursePubResponse);
            }
            QueryResult<CoursePub> queryResult = new QueryResult<>();
            queryResult.setList(list);
            queryResult.setTotal(totalHits);
            return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        }
        return null;
    }
}
