package DAO;
import Data.FoodItem;

import java.util.ArrayList;

/**
 * Created by Tanya on 23.03.2017.
 */
public interface FoodDAO
{
    boolean create(FoodItem foodItem);
    ArrayList<FoodItem> readAll();
    FoodItem readByName(String foodName);
    boolean update(String foodName, String key, Integer newValue);
    boolean delete(String foodName);

    //need for tests
    void dropDB();
}
