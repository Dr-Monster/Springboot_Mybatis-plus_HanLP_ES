package com.es_option;


import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
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
public class Batch_Doc {
    @Resource
    RestHighLevelClient restHighLevelClient;


    @Test
    public void batchOption() throws IOException {

        //批量
        BulkRequest bulkRequest = new BulkRequest();

        bulkRequest.add(new IndexRequest("keywords").id("1").source(XContentType.JSON , "keyword" , "A1"));
        bulkRequest.add(new IndexRequest("keywords").id("2").source(XContentType.JSON , "keyword" , "A2"));
        bulkRequest.add(new IndexRequest("keywords").id("3").source(XContentType.JSON , "keyword" , "A3"));

        //执行操作
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        //提取有用的响应数据
        System.out.println(bulk.getItems());
    }
}
