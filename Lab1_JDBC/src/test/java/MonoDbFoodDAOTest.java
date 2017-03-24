import DAO.FoodDAO;
import DAO.MonoDbFoodDAO;
import DAO.MySQLDAO;
import DAO.MySQLFoodDAO;
import Data.FoodItem;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Tanya on 23.03.2017.
 */
public class MonoDbFoodDAOTest
{
    private FoodDAO dao = new MonoDbFoodDAO();
//    private FoodDAO dao = new MySQLFoodDAO();

    @Test
    public void create() throws Exception
    {
        ArrayList<FoodItem> expectedArr = new ArrayList<FoodItem>(5);
        ArrayList<FoodItem> realArr = new ArrayList<FoodItem>(5);
        for(int i = 0; i<5; i++){
            FoodItem foodItem = new FoodItem();
            foodItem.setName("TestFood" + i);
            foodItem.setPrice(i + i + i);
            dao.create(foodItem);
            realArr.add(dao.readByName("TestFood" + i));
            expectedArr.add(foodItem);
        }
        Assert.assertEquals(expectedArr, realArr);
    }

    @Test
    public void readAll() throws Exception
    {
        ArrayList<FoodItem> arr = dao.readAll();
        for (FoodItem foodItem : arr){
            System.out.println(foodItem.toString());
        }
    }

    @Test
    public void readByName() throws Exception
    {
        FoodItem foodItem = dao.readByName("TestFood1");
        System.out.println(foodItem.toString());
        FoodItem excpectedFoodItem = new FoodItem("TestFood1",3);
        Assert.assertEquals(excpectedFoodItem, foodItem);
    }

    @Test
    public void update() throws Exception
    {
        FoodItem foodItem = new FoodItem("TestUpdate", 1);
        dao.create(foodItem);
        dao.update(foodItem.getName(), "price", 2);
        FoodItem res = dao.readByName(foodItem.getName());
        foodItem.setPrice(2);
        Assert.assertEquals(foodItem,res);
        dao.delete(foodItem.getName());
    }

    @Test
    public void delete() throws Exception
    {
        dao.create(new FoodItem("FoodToDelete", 2));
        Assert.assertNotEquals(dao.readByName("FoodToDelete"), null);
        dao.delete("FoodToDelete");
        Assert.assertEquals(null, dao.readByName("FoodToDelete"));
    }

    @Test
    public void dropDB() throws Exception
    {
        dao.dropDB();
    }
}