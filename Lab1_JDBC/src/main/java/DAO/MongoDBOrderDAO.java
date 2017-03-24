package DAO;

import Data.Order;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

/**
 * Created by LevelNone on 23.03.2017.
 */
public class MongoDBOrderDAO implements OrderDAO {
    private static MongoClient mongoClient;

    private String dbName = "Magazine";
    private String tableName = "Orders";

    private Gson gson = new Gson();
    private MongoCollection<Document> collection;

    public MongoDBOrderDAO(){
        if(mongoClient == null){
            mongoClient = new MongoClient( "localhost" , 27017 );
        }

        MongoDatabase database = mongoClient.getDatabase(dbName);
        collection = database.getCollection(tableName);
    }

    @Override
    public boolean create(Order order) {
        try {
            if(readByUUID(order.getUuid()) != null) return false;
            Document doc = Document.parse(gson.toJson(order));
            collection.insertOne(doc);
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Order> readAll() {
        ArrayList<Order> res = new ArrayList<Order>();
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                res.add(gson.fromJson(cursor.next().toJson(), Order.class));
            }
        } finally {
            cursor.close();
        }
        return res;
    }

    @Override
    public Order readByUUID(UUID uuid) {
        try{
            Document doc = collection.find(eq("uuid", uuid.toString())).first();
            return gson.fromJson(doc.toJson(), Order.class);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(UUID uuid, String key, String newValue) {
        try {
            return collection.updateOne(eq("uuid", uuid.toString()), set(key, newValue)).isModifiedCountAvailable();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    @Override
    public boolean delete(UUID uuid) {
        try {
            collection.deleteOne(eq("uuid", uuid.toString()));
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public void dropDB() {

    }
}
