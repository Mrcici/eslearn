package com.es.learn.eslearndemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：cici
 * @date ：Created in 2019/9/28 14:34
 * DocumentAPIs
 */
@RestController
@RequestMapping(value = "/document")
@Slf4j
public class ESDocumentAPIController {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 添加索引
     * 添加索引支持同步和异步
     *
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

        //另一种组装json方式
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("1").source(jsonMap);
        ActionListener listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        try {
            //同步执行
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            //异步执行
            client.indexAsync(request, RequestOptions.DEFAULT, listener);
            log.info(indexResponse.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "get/index",method = RequestMethod.GET)
    public GetResponse getResponse() throws IOException {
        GetRequest getRequest = new GetRequest(
                "posts",
                "1");
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        return getResponse;
    }

    /**
     * 删除索引
     */
    @RequestMapping(value = "/del/index" ,method = RequestMethod.DELETE)
    public void delIndex(){
        RestHighLevelClient  client = new RestHighLevelClient( RestClient.builder(
                new HttpHost("127.0.0.1", 9200, "http")));
        DeleteRequest request = new DeleteRequest(
                "posts",
                "1");
        try {
            DeleteResponse deleteResponse = client.delete(
                    request, RequestOptions.DEFAULT);
            log.info("response" + deleteResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 索引是否存在
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/exists",method = RequestMethod.GET)
    public boolean isExists() throws IOException {
        GetRequest getRequest = new GetRequest(
                "posts",
                "1");
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        return exists;
    }

    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public UpdateResponse updateResponse() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("updated", new Date());
        jsonMap.put("reason", "daily update");
        UpdateRequest request = new UpdateRequest("posts", "1")
                .doc(jsonMap);
        UpdateResponse updateResponse = client.update(
                request, RequestOptions.DEFAULT);
        return updateResponse;
    }
}
