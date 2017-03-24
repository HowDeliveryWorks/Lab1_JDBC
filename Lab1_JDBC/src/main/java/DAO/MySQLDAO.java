package DAO;

import Data.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Tanya on 23.03.2017.
 */
public class MySQLDAO implements UserDAO
{
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/jdbc";
    private static final String ID = "root";
    private static final String PASS = "0000";

    private static final String DELETE = "DELETE FROM users WHERE login=?";
    private static final String FIND_ALL = "SELECT * FROM users ORDER BY login";
    private static final String FIND_BY_NAME = "SELECT * FROM users WHERE login=?";
    private static final String INSERT = "INSERT INTO users(login, password) VALUES(?, ?)";
    private static final String UPDATE = "UPDATE users SET key=? WHERE login=?";

    public MySQLDAO(){
        
    }

    private Connection getConnection() {
        try {
            Class.forName(DRIVER_NAME);
            return DriverManager.getConnection(DB_URL, ID, PASS);
        } catch (Exception e) {
             e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
    private static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    //work
    public boolean create(User user)
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());

            stmt.executeUpdate();


            return true;
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
            //throw new RuntimeException(e);
        } finally {
            close(stmt);
            close(conn);
        }
    }

    public ArrayList<User> readAll()
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<User> list = new ArrayList<User>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(FIND_ALL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                list.add(user);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(stmt);
            close(conn);
        }

        return list;
    }

    public User readByName(String userName)
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(FIND_BY_NAME);
            stmt.setString(1, userName);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));

                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(stmt);
            close(conn);
        }
    }

    public boolean update(String userName, String key, String newValue)
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            String temp = UPDATE;
            temp = temp.replaceFirst("key", key);
            stmt = conn.prepareStatement(temp);
            //need to set key without ' '!
            //stmt.setString(1, key);
            stmt.setString(1, newValue);
            stmt.setString(2, userName);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
             e.printStackTrace();
            return false;
        } finally {
            close(stmt);
            close(conn);
        }
    }

    public boolean delete(String userName)
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(DELETE);
            stmt.setString(1, userName);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
             e.printStackTrace();
            return false;
            //throw new RuntimeException(e);
        } finally {
            close(stmt);
            close(conn);
        }
    }

    public void dropDB()
    {

    }


}
