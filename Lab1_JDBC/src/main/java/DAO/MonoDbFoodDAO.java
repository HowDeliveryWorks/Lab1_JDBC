package DAO;

import Data.FoodItem;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

/**
 * Created by Tanya on 23.03.2017.
 */
public class MonoDbFoodDAO implements FoodDAO
{
    private static MongoClient mongoClient;

    private String dbName = "Magazine";
    private String tableName = "FoodMenu";

    private Gson gson = new Gson();
    private MongoCollection<Document> collection;

    public MonoDbFoodDAO(){
        if(mongoClient == null){
            mongoClient = new MongoClient( "localhost" , 27017 );
        }

        MongoDatabase database = mongoClient.getDatabase(dbName);
        collection = database.getCollection(tableName);
    }
    public boolean create(FoodItem foodItem)
    {
        try {
            if(readByName(foodItem.getName()) != null) return false;
            Document doc = Document.parse(gson.toJson(foodItem));
            collection.insertOne(doc);
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public ArrayList<FoodItem> readAll()
    {
        ArrayList<FoodItem> res = new ArrayList<FoodItem>();
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                res.add(gson.fromJson(cursor.next().toJson(), FoodItem.class));
            }
        } finally {
            cursor.close();
        }
        return res;
    }

    public FoodItem readByName(String foodName)
    {
        try{
            Document doc = collection.find(eq("name", foodName)).first();
            return gson.fromJson(doc.toJson(), FoodItem.class);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            //return null;
            return null;
        }
    }

    public boolean update(String foodName, String key, Integer newValue)
    {
        try {
            return collection.updateOne(eq("name", foodName), set(key, newValue)).isModifiedCountAvailable();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean delete(String foodName)
    {
        try {
            collection.deleteOne(eq("name", foodName));
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public void dropDB(){
        mongoClient.dropDatabase("TestDB");
    }
}
