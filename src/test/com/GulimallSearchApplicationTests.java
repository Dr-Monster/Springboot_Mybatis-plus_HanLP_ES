package com;

import com.alibaba.fastjson.JSON;
import com.configuration.GulimallElasticSearchConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
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
public class GulimallSearchApplicationTests {


    @Resource
    RestHighLevelClient restHighLevelClient;

    //向对应的index中写入数据
    @Test
    public void indexData() throws IOException {
        //初始化请求，构造函数中指定index名
        IndexRequest indexRequest = new IndexRequest("users");
        //设置id
        indexRequest.id("666");
        User user = new User("zhangsan", "male", 18);
        String jsonString = JSON.toJSONString(user);
        //要保存的内容
        indexRequest.source(jsonString, XContentType.JSON);
        //执行操作
        IndexResponse index = restHighLevelClient.index(indexRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);

        //提取有用的响应数据
        System.out.println(index);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    class User{
        private String userName;
        private String gender;
        private Integer age;


    }

    @Test
    public void contextLoads() {
        System.out.println(restHighLevelClient);
    }

}
