package DAO;

import Data.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by LevelNone on 24.03.2017.
 */
public class MySQLOrderDAO implements OrderDAO {
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/magazine";
    private static final String ID = "root";
    private static final String PASS = "0000";

    private static final String DELETE = "DELETE FROM orders WHERE uuid=?";
    private static final String FIND_ALL = "SELECT * FROM orders ORDER BY uuid";
    private static final String FIND_BY_NAME = "SELECT * FROM orders WHERE uuid=?";
    private static final String INSERT = "INSERT INTO orders(uuid, sum) VALUES(?, ?)";
    private static final String UPDATE = "UPDATE orders SET key=? WHERE uuid=?";

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

    @Override
    public boolean create(Order order) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, order.getUuid().toString());
            stmt.setInt(2, order.getSum());

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

    @Override
    public ArrayList<Order> readAll() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Order> list = new ArrayList<Order>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(FIND_ALL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setUuid(java.util.UUID.fromString(rs.getString("uuid")));
                order.setSum(rs.getInt("sum"));
                list.add(order);
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

    @Override
    public Order readByUUID(UUID uuid) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(FIND_BY_NAME);
            stmt.setString(1, uuid.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Order order = new Order();
                order.setUuid(java.util.UUID.fromString(rs.getString("uuid")));
                order.setSum(rs.getInt("sum"));

                return order;
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

    @Override
    public boolean update(UUID uuid, String key, String newValue) {
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
            stmt.setString(2, uuid.toString());

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

    @Override
    public boolean delete(UUID uuid) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(DELETE);
            stmt.setString(1, uuid.toString());

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

    @Override
    public void dropDB() {

    }
}
