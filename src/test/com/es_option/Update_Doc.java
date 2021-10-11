package com.es_option;

import com.alibaba.fastjson.JSON;
import com.entity.KeyWords;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
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
public class Update_Doc {

    @Resource
    RestHighLevelClient restHighLevelClient;


    @Test
    public void updateData() throws IOException {

        UpdateRequest updateRequest = new UpdateRequest();

        updateRequest.index("keywords");

        updateRequest.id("666");

        updateRequest.doc(XContentType.JSON , "keyword" , "XX_XX");

        //执行操作
        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        //提取有用的响应数据
        System.out.println(update);
    }
}
