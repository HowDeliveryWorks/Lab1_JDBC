package DAO;

import Data.FoodItem;
import Data.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Tanya on 24.03.2017.
 */
public class MySQLFoodDAO implements FoodDAO {
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3307/Magazine";
    private static final String ID = "root";
    private static final String PASS = "";

    private static final String DELETE = "DELETE FROM FoodMenu WHERE name=?";
    private static final String FIND_ALL = "SELECT * FROM FoodMenu ORDER BY name";
    private static final String FIND_BY_NAME = "SELECT * FROM FoodMenu WHERE name=?";
    private static final String INSERT = "INSERT INTO FoodMenu(name, price) VALUES(?, ?)";
    private static final String UPDATE = "UPDATE FoodMenu SET key=? WHERE name=?";

    public MySQLFoodDAO(){

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
    public boolean create(FoodItem foodItem)
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, foodItem.getName());
            stmt.setString(2, foodItem.getPrice().toString());

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

    public ArrayList<FoodItem> readAll()
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<FoodItem> list = new ArrayList<FoodItem>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(FIND_ALL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                FoodItem foodItem = new FoodItem();
                foodItem.setName(rs.getString("name"));
                foodItem.setPrice(Integer.valueOf(rs.getString("price")));
                list.add(foodItem);
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

    public FoodItem readByName(String foodItemName)
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(FIND_BY_NAME);
            stmt.setString(1, foodItemName);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FoodItem foodItem = new FoodItem();
                foodItem.setName(rs.getString("name"));
                foodItem.setPrice(Integer.valueOf(rs.getString("price")));

                return foodItem;
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

    public boolean update(String foodItemName, String key, Integer newValue)
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
            stmt.setString(1, newValue.toString());
            stmt.setString(2, foodItemName);

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

    public boolean delete(String foodItemName)
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(DELETE);
            stmt.setString(1, foodItemName);

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
