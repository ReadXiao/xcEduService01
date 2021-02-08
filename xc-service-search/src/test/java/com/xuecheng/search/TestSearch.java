package com.xuecheng.search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

/**
 * @author 肖宏武
 * @date 2021/1/26 - 19:05
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {
    @Autowired
    RestHighLevelClient client;

    @Autowired
    RestClient restClient;

    @Test
    public void testSearchAll() throws IOException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索方式
        //matchAllQuery：搜索全部
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索结果，向ES发起http请求
        SearchResponse searchResponse = client.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度搞得文档
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    @Test
    public void testSearchPage() throws IOException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置分页参数
        //页码
        int page = 1;
        //每页记录数
        int size = 1;
        //每页起始下标
        int from = (page - 1) * size;
        searchSourceBuilder.from(from);//起始下标
        searchSourceBuilder.size(size);//每页显示记录数
        //搜索方式
        //matchAllQuery：搜索全部
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索结果，向ES发起http请求
        SearchResponse searchResponse = client.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度搞得文档
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    @Test
    public void testSearchTerm() throws IOException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置分页参数
        //页码
        int page = 1;
        //每页记录数
        int size = 1;
        //每页起始下标
        int from = (page - 1) * size;
        searchSourceBuilder.from(from);//起始下标
        searchSourceBuilder.size(size);//每页显示记录数
        //搜索方式
        //matchAllQuery：搜索全部
        searchSourceBuilder.query(QueryBuilders.termQuery("name", "spring"));
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索结果，向ES发起http请求
        SearchResponse searchResponse = client.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度搞得文档
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    @Test
    public void testSearchTermByIds() throws IOException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索方式
        //根据id查询
        //定义id
        String[] ids = new String[]{"1", "2"};
        //matchAllQuery：搜索全部
        searchSourceBuilder.query(QueryBuilders.termsQuery("_id", ids));
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索结果，向ES发起http请求
        SearchResponse searchResponse = client.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度搞得文档
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    @Test
    public void testMatchQuery() throws IOException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索方式
        //根据id查询
        //定义id
        String[] ids = new String[]{"1", "2"};
        //matchAllQuery：搜索全部
        searchSourceBuilder.query(QueryBuilders.matchQuery("description", "spring开发框架").minimumShouldMatch("80%"));
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索结果，向ES发起http请求
        SearchResponse searchResponse = client.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度搞得文档
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }


    @Test
    public void testMultiMatchQuery() throws IOException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索方式
        //根据id查询
        //定义id
        String[] ids = new String[]{"1", "2"};
        //matchAllQuery：搜索全部
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("spring css", "name", "description").minimumShouldMatch("50%").field("name", 10));
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索结果，向ES发起http请求
        SearchResponse searchResponse = client.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度搞得文档
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    //布尔查询
    @Test
    public void boolQuery() throws IOException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索方式
        //先定义一个matchQueryBuilder
        MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery("spring css", "name", "description").
                minimumShouldMatch("50%").
                field("name", 10);
        //在定义一个
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "201001");
        //定义一个boolQuery
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(matchQueryBuilder);
        boolQueryBuilder.must(termQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索结果，向ES发起http请求
        SearchResponse searchResponse = client.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度搞得文档
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    //布尔过滤查询
    @Test
    public void boolFilterQuery() throws IOException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索方式
        //先定义一个matchQueryBuilder
        MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery("spring css", "name", "description").
                minimumShouldMatch("50%").
                field("name", 10);
        //定义一个boolQuery
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(matchQueryBuilder);
        //定义一个过滤器
        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel","201001"));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(60).lte(100));
        searchSourceBuilder.query(boolQueryBuilder);
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索结果，向ES发起http请求
        SearchResponse searchResponse = client.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度搞得文档
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    //布尔过滤查询
    @Test
    public void boolFilterQuerySort() throws IOException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索方式
        //先定义一个matchQueryBuilder
        MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery("spring css", "name", "description").
                minimumShouldMatch("50%").
                field("name", 10);
        //定义一个boolQuery
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(matchQueryBuilder);
        //定义一个过滤器
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));
        searchSourceBuilder.query(boolQueryBuilder);
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //添加排序
        searchSourceBuilder.sort("studymodel", SortOrder.DESC);
        searchSourceBuilder.sort("price", SortOrder.ASC);
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索结果，向ES发起http请求
        SearchResponse searchResponse = client.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度搞得文档
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }

    //过滤排序查询高亮
    @Test
    public void boolFilterQuerySortHighLight() throws IOException {
        //搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        //指定类型
        searchRequest.types("doc");
        //搜索构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索方式
        //先定义一个matchQueryBuilder
        MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery("开发框架", "name", "description").
                minimumShouldMatch("50%").
                field("name", 10);
        //定义一个boolQuery
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(matchQueryBuilder);
        //定义一个过滤器
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));
        searchSourceBuilder.query(boolQueryBuilder);
        //source源字段过虑
        searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //添加排序
        searchSourceBuilder.sort("studymodel", SortOrder.DESC);
        searchSourceBuilder.sort("price", SortOrder.ASC);
        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<tag>");
        highlightBuilder.postTags("</tag>");
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        highlightBuilder.fields().add(new HighlightBuilder.Field("description"));
        searchSourceBuilder.highlighter(highlightBuilder);
        //向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);
        //执行搜索结果，向ES发起http请求
        SearchResponse searchResponse = client.search(searchRequest);
        //搜索结果
        SearchHits hits = searchResponse.getHits();
        //匹配到的总记录数
        long totalHits = hits.getTotalHits();
        //得到匹配度搞得文档
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String type = hit.getType();
            String id = hit.getId();
            float score = hit.getScore();
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //源文档的name字段
            String name = (String) sourceAsMap.get("name");
            //取出高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields!=null){
                //取出name高亮字段
                HighlightField highlightField = highlightFields.get("name");
                if (highlightField!=null){
                    
                }
            }
            String studymodel = (String) sourceAsMap.get("studymodel");
            String description = (String) sourceAsMap.get("description");
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(description);
        }
    }
}
