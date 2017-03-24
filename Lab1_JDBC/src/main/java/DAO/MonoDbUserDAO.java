package DAO;

import Data.User;
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
public class MonoDbUserDAO implements UserDAO
{
    private static MongoClient mongoClient;

    private String dbName = "Magazine";
    private String tableName = "Users";

    private Gson gson = new Gson();
    private MongoCollection<Document> collection;

    public MonoDbUserDAO(){
        if(mongoClient == null){
            mongoClient = new MongoClient( "localhost" , 27017 );
        }

        MongoDatabase database = mongoClient.getDatabase(dbName);
        collection = database.getCollection(tableName);
    }
    public boolean create(User user)
    {
        try {
            if(readByName(user.getLogin()) != null) return false;
            Document doc = Document.parse(gson.toJson(user));
            collection.insertOne(doc);
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public ArrayList<User> readAll()
    {
        ArrayList<User> res = new ArrayList<User>();
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                res.add(gson.fromJson(cursor.next().toJson(), User.class));
            }
        } finally {
            cursor.close();
        }
        return res;
    }

    public User readByName(String userName)
    {
        try{
            Document doc = collection.find(eq("login", userName)).first();
            return gson.fromJson(doc.toJson(), User.class);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean update(String userName, String key, String newValue)
    {
        try {
            return collection.updateOne(eq("login", userName), set(key, newValue)).isModifiedCountAvailable();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean delete(String userName)
    {
        try {
            collection.deleteOne(eq("login", userName));
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
