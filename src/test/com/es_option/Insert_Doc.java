package com.es_option;

import com.GulimallSearchApplicationTests;
import com.alibaba.fastjson.JSON;
import com.configuration.GulimallElasticSearchConfig;
import com.entity.KeyWords;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
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
public class Insert_Doc {


    @Resource
    RestHighLevelClient restHighLevelClient;


    @Test
    public void insertData() throws IOException {
        //初始化请求，构造函数中指定index名
        IndexRequest indexRequest = new IndexRequest("keywords");
        //设置id
        indexRequest.id("666");
        KeyWords keyWords = new KeyWords(1 , "A");
        String jsonString = JSON.toJSONString(keyWords);
        //要保存的内容
        indexRequest.source(jsonString, XContentType.JSON);
        //执行操作
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        //提取有用的响应数据
        System.out.println(index);
    }
}
