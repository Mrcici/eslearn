package com.es.learn.eslearndemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author ：cici
 * @date ：Created in 2019/9/28 14:34
 */
@RestController
@RequestMapping(value = "/test")
@Slf4j
public class ESTestController {


    /**
     * 添加索引
     */
    @RequestMapping(value = "/addIndex",method = RequestMethod.GET)
    public void addDoc(){
        RestHighLevelClient  client = new RestHighLevelClient( RestClient.builder(
                new HttpHost("127.0.0.1", 9200, "http")));
        IndexRequest request = new IndexRequest("posts");
        request.id("1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            log.info(indexResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
