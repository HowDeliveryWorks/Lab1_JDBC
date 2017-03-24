import DAO.MySQLOrderDAO;
import DAO.OrderDAO;
import Data.Order;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by LevelNone on 24.03.2017.
 */
public class OrderDAOTest {
    //private OrderDAO dao = new MongoDBOrderDAO();
    private OrderDAO dao = new MySQLOrderDAO();


    @Test
    public void create() throws Exception
    {
        ArrayList<Order> expectedArr = new ArrayList<Order>(5);
        ArrayList<Order> realArr = new ArrayList<Order>(5);

        for(int i = 0; i<5; i++){
            Order order = new Order();
            order.setSum(i);
            dao.create(order);
            realArr.add(dao.readByUUID(order.getUuid()));
            expectedArr.add(order);
        }
        Assert.assertEquals(expectedArr, realArr);
    }

    @Test
    public void readAll() throws Exception
    {
        ArrayList<Order> arr = dao.readAll();
        for (Order order : arr){
            System.out.println(order.toString());
        }
    }

    @Test
    public void readByName() throws Exception
    {
        Order order = new Order();
        order.setSum(10);
        dao.create(order);
        Order res = dao.readByUUID(order.getUuid());
        System.out.println(res.toString());
        Assert.assertEquals(res, order);
    }

    @Test
    public void update() throws Exception
    {
        Order order = new Order(java.util.UUID.randomUUID(), 0);
        dao.create(order);
        dao.update(order.getUuid(), "sum", "2");
        Order res = dao.readByUUID(order.getUuid());
        order.setSum(2);
        Assert.assertEquals(order,res);
        dao.delete(order.getUuid());
    }

    @Test
    public void delete() throws Exception
    {
        Order order = new Order(java.util.UUID.randomUUID(), 1488);
        dao.create(order);
        Assert.assertNotEquals(dao.readByUUID(order.getUuid()), null);
        dao.delete(order.getUuid());
        Assert.assertEquals(null, dao.readByUUID(order.getUuid()));
    }

    @Test
    public void dropDB() throws Exception
    {
        dao.dropDB();
    }
}
