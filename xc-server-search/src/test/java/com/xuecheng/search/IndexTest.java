package com.xuecheng.search;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

/**
 * @author 杜承旭
 * @ClassNmae: IndexTest
 * @Description: TODO
 * @date 2019/11/11 15:04
 * @Version 1.0
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class IndexTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private RestClient restClient;

    @Test
    public void searchIndex() throws IOException {
        GetRequest getRequest = new GetRequest("xc_course", "doc", "INYSWW4Bgf2KRcRc0z8W");
        GetResponse getResponse = restHighLevelClient.get(getRequest);
        boolean exists = getResponse.isExists();
        if (exists){
            Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
            System.out.println(sourceAsMap);
        }
    }

    @Test
    public void searchRequest() throws IOException {

        //创建查询请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");

        //查询的数据源
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询全部
        //searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //精确匹配
        //searchSourceBuilder.query(QueryBuilders.termQuery("name","spring开发基础"));
        //单个分词匹配查询
        //searchSourceBuilder.query(QueryBuilders.matchQuery("description","spring开发").operator(Operator.OR));
        //设置匹配的占比
        //searchSourceBuilder.query(QueryBuilders.matchQuery("description","前台页面开发框，架构").operator(Operator.OR).minimumShouldMatch("50%"));
        //多个分词匹配查询,
        //searchSourceBuilder.query(QueryBuilders.multiMatchQuery("spring框架","name","description").minimumShouldMatch("50%").field("name",10));
        //布尔查询
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring框架", "name", "description");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "201001");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.must(termQueryBuilder);

        //设置搜索结果过滤
        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel","201001"));

        //排序
        searchSourceBuilder.sort(new FieldSortBuilder("studymodel").order(SortOrder.DESC));

        //高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<tag>");//设置前缀
        highlightBuilder.postTags("</tag>");//设置后缀
        //设置高亮字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        //highlightBuilder.fields().add(new HighlightBuilder.Field("description"));
        searchSourceBuilder.highlighter(highlightBuilder);

        //在源数据中添加匹配规则
        searchSourceBuilder.query(boolQueryBuilder);

        //设置显示的字段和屏蔽的字段
        searchSourceBuilder.fetchSource(new String[]{},new String[]{});

        //分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(2);

        //把数据源添加到查询请求中
        searchRequest.source(searchSourceBuilder);

        //进行查询
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();

        //数据封装
        for (SearchHit searchHit : searchHits){
            String id = searchHit.getId();
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();

            //取出高亮
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

            System.out.println(id);
            System.out.println(sourceAsMap);
            System.out.println(highlightFields);
            System.out.println("-----------------------------------------------------");
        }

    }
}
