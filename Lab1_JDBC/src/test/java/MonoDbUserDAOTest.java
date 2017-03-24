import DAO.MonoDbUserDAO;
import DAO.MySQLDAO;
import DAO.UserDAO;
import Data.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Игорь on 20.03.2017.
 */
public class MonoDbUserDAOTest
{
    private UserDAO dao = new MonoDbUserDAO();
    //private UserDAO dao = new MySQLDAO();

    @Test
    public void create() throws Exception
    {
        ArrayList<User> expectedArr = new ArrayList<User>(5);
        ArrayList<User> realArr = new ArrayList<User>(5);
        for(int i = 0; i<5; i++){
            User user = new User();
            user.setLogin("TestUser" + i);
            user.setPassword(i + i + i + "");
            dao.create(user);
            realArr.add(dao.readByName("TestUser" + i));
            expectedArr.add(user);
        }
        Assert.assertEquals(expectedArr, realArr);
    }

    @Test
    public void readAll() throws Exception
    {
        ArrayList<User> arr = dao.readAll();
        for (User user : arr){
            System.out.println(user.toString());
        }
    }

    @Test
    public void readByName() throws Exception
    {
        User user = dao.readByName("TestUser1");
        System.out.println(user.toString());
        User excpectedUser = new User("TestUser1", "3");
        Assert.assertEquals(excpectedUser, user);
    }

    @Test
    public void update() throws Exception
    {
        User user = new User("TestUpdate", "1");
        dao.create(user);
        dao.update(user.getLogin(), "password", "2");
        User res = dao.readByName(user.getLogin());
        user.setPassword("2");
        Assert.assertEquals(user,res);
        dao.delete(user.getLogin());
    }

    @Test
    public void delete() throws Exception
    {
        dao.create(new User("UserToDelete", "0000"));
        Assert.assertNotEquals(dao.readByName("UserToDelete"), null);
        dao.delete("UserToDelete");
        Assert.assertEquals(null, dao.readByName("UserToDelete"));
    }

    @Test
    public void dropDB() throws Exception
    {
        dao.dropDB();
    }
}