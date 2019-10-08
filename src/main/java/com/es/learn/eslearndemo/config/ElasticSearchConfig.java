package com.es.learn.eslearndemo.config;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * @author ：cici
 * @date ：Created in 2019/9/28 13:46
 */
@Configuration
public class ElasticSearchConfig {

    @Bean
    public RestHighLevelClient esClient() throws UnknownHostException {
        RestHighLevelClient  client = new RestHighLevelClient( RestClient.builder(
                new HttpHost("127.0.0.1", 9200, "http")));
        return client;
    }
}
