package com.es.learn.eslearndemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：cici
 * @date ：Created in 2019/10/7 16:41
 */
@RestController
@RequestMapping(value = "/search")
@Slf4j
public class ESSearchAPIController {

    @Autowired
    private RestHighLevelClient client;

    @RequestMapping(value = "/search")
    public void search(){
//        QueryBuilder
    }
}
