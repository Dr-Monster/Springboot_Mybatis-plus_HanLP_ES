package com.es_option;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Delete_Doc {

    @Resource
    RestHighLevelClient restHighLevelClient;


    @Test
    public void deleteData() throws IOException {

        DeleteRequest deleteRequest = new DeleteRequest();

        deleteRequest.index("keywords").id("666");

        //执行操作
        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        //提取有用的响应数据
        System.out.println(delete);
    }
}
