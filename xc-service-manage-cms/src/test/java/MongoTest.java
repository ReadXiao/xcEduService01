import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

import java.util.Date;

/**
 * @author 肖宏武
 * @date 2020/8/30 - 13:38
 */
public class MongoTest {

    @Test
    public void Mongo(){//创建mongodb 客户端
        //MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        //或者采用连接字符串
        MongoClientURI connectionString = new MongoClientURI("mongodb://root:123@localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);
        //连接数据库
        MongoDatabase database = mongoClient.getDatabase("xc_cms");
        // 连接collection
        //MongoCollection<Document> collection = database.getCollection("student");
        MongoCollection<Document> collection = database.getCollection("cms_page");
        //查询第一个文档
        Document myDoc = collection.find().first();
        //得到文件内容 json串
        String json = myDoc.toJson();
        System.out.println(json);

    }

}
