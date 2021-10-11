package com.es_option;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class View_Doc {


    @Resource
    RestHighLevelClient restHighLevelClient;


    @Test
    public void viewData() throws IOException {

        GetRequest getRequest = new GetRequest();

        getRequest.index("keywords").id("666");

        //执行操作
        GetResponse get = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        //提取有用的响应数据
        System.out.println(get);
    }
}
