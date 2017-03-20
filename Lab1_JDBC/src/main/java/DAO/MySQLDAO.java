package DAO;

import Data.User;

import java.util.ArrayList;

/**
 * Created by Игорь on 20.03.2017.
 */
public class MySQLDAO implements UserDAO
{
    public MySQLDAO(){

    }
    public boolean create(User user)
    {
        return false;
    }

    public ArrayList<User> readAll()
    {
        return null;
    }

    public User readByName(String userName)
    {
        return null;
    }

    public boolean update(String userName, String key, String newValue)
    {
        return false;
    }

    public boolean delete(String userName)
    {
        return false;
    }

    public void dropDB()
    {

    }
}
