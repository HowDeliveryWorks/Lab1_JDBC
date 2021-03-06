package DAO;
import Data.User;

import java.util.ArrayList;

public interface UserDAO
{
    boolean create(User user);
    ArrayList<User> readAll();
    User readByName(String userName);
    boolean update(String userName, String key, String newValue);
    boolean delete(String userName);

    //need for tests
    void dropDB();
}
