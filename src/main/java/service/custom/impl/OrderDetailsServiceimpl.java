package service.custom.impl;

import db.DBConnection;
import model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsServiceimpl {

    public boolean addOrderDetails(List<OrderDetails> orderDetails){
         for(OrderDetails orderD: orderDetails){
             boolean isaddDetail = addOrderDetails(orderD);
                if (!isaddDetail){
                    return false;
                }
         }
            return true;
    }

    public  boolean addOrderDetails(OrderDetails orderD){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orderDetail VALUES (?,?,?,?)");
            preparedStatement.setString(1,orderD.getOrderId());
            preparedStatement.setString(2,orderD.getItemCode());
            preparedStatement.setInt(3,orderD.getQty());
            preparedStatement.setDouble(4,orderD.getUnitPrice());
            return preparedStatement.executeUpdate()>0;



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
