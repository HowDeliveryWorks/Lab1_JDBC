package DAO;

import Data.Order;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by LevelNone on 23.03.2017.
 */
public interface OrderDAO {
    boolean create(Order order);
    ArrayList<Order> readAll();
    Order readByUUID(UUID uuid);
    boolean update(UUID uuid, String key, String newValue);
    boolean delete(UUID uuid);

    //need for tests
    void dropDB();
}
