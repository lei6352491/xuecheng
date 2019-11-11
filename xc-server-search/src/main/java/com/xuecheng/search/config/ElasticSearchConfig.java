package com.xuecheng.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 杜承旭
 * @ClassNmae: ElasticSearchConfig
 * @Description: TODO
 * @date 2019/11/11 14:55
 * @Version 1.0
 **/

@Configuration
public class ElasticSearchConfig {

    @Value("${xuecheng.elasticsearch.hostlist}")
    private String hostList;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        //解析hostlist配置信息
        String[] split = hostList.split(",");
        //创建HttpHost数组，其中存放es主机和端口的配置信息
        HttpHost[] httpHostArray = new HttpHost[split.length];
        for(int i = 0; i<split.length; i++){
            String item = split[i];
            httpHostArray[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":") [1]), "http");
        }
        //创建RestHighLevelClient客户端
        return new RestHighLevelClient(RestClient.builder(httpHostArray));
    }

    @Bean
    public RestClient restClient(){
        //解析hostlist配置信息
        String[] split = hostList.split(",");
        //创建HttpHost数组，其中存放es主机和端口的配置信息
        HttpHost[] httpHostArray = new HttpHost[split.length];
        for(int i=0;i<split.length;i++){
            String item = split[i];httpHostArray[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":") [1]), "http");
        }
        return RestClient.builder(httpHostArray).build();
    }
}
