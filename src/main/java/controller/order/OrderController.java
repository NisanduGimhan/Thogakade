package controller.order;

import db.DBConnection;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderController implements OrderService{
    private static OrderController instance;

    private OrderController(){}

    public static OrderController getInstance(){
        if (instance==null){
            instance=new OrderController();
        }
        return instance;
    }




    public List<String> getAllCustomers(){
        List<String> custId = new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id  FROM customer");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                custId.add(resultSet.getString("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return custId;
    }

    public List<String> getAllItems(){
        List<String> itemId=new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT code FROM item");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                itemId.add(resultSet.getString("code"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
return itemId;

    }


}
