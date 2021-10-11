package com.es_option;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.query.QuerySearchRequest;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Iterator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Query {

    @Resource
    RestHighLevelClient restHighLevelClient;

    @Test
    public void batchOption() throws IOException {

        SearchRequest searchRequest = new SearchRequest();

        searchRequest.indices("keywords");

        //全量查询
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();

        //条件查询
//        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("keyword" , "A3");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(queryBuilder);

        //分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);

        //排序
        searchSourceBuilder.sort("keyword" , SortOrder.ASC);

        //过滤
        //包含
        String[] includes = {"keyword"};
        //剔除
        String[] excludes = {""};
        searchSourceBuilder.fetchSource(includes , excludes);


        searchRequest.source(searchSourceBuilder);


        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Iterator<SearchHit> iterator = searchResponse.getHits().iterator();

        while (iterator.hasNext()){
            SearchHit searchHit = iterator.next();
            System.out.println(searchHit);
        }

    }
}
