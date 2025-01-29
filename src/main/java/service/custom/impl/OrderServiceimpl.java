package service.custom.impl;

import db.DBConnection;
import model.Order;
import service.custom.OrderService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceimpl implements OrderService {
    private static OrderServiceimpl instance;

    private OrderServiceimpl() {
    }

    public static OrderServiceimpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceimpl();
        }
        return instance;
    }


    public List<String> getAllCustomers() {
        List<String> custId = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id  FROM customer");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                custId.add(resultSet.getString("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return custId;
    }

    public List<String> getAllItems() {
        List<String> itemId = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT code FROM item");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                itemId.add(resultSet.getString("code"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemId;

    }

    public boolean placeorder(Order or) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders VALUES (?,?,?)");

            preparedStatement.setString(1, or.getOrderId());
            preparedStatement.setString(2, String.valueOf(or.getDate()));
            preparedStatement.setString(3, or.getCustomerId());
            boolean isaddOrder = preparedStatement.executeUpdate() > 0;
            if (isaddOrder) {
                boolean isAddOrderDetails = new OrderDetailsServiceimpl().addOrderDetails(or.getOrderDetaiList());
                if (isAddOrderDetails) {
                    boolean isUpdateItemQty = ItemServiceimpl.getInstance().updateQty(or.getOrderDetaiList());
                    if (isUpdateItemQty) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}




