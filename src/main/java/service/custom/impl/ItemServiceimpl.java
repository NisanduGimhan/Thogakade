package service.custom.impl;

import db.DBConnection;
import model.Item;
import model.OrderDetails;
import service.custom.ItemService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceimpl implements ItemService {
    private static ItemServiceimpl instance;

    private ItemServiceimpl(){

    }

    public static ItemServiceimpl getInstance(){
        if (instance==null){
            instance=new ItemServiceimpl();
        }
        return instance;
    }

    @Override
    public boolean addItem(Item item) {
        Connection connection = DBConnection.getInstance().getConnection();
        int i;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
            preparedStatement.setObject(1,item.getCode());
            preparedStatement.setObject(2,item.getDescription());
            preparedStatement.setObject(3,item.getPrice());
            preparedStatement.setObject(4,item.getQty());

             i= preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (i>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteItem(String code) {
        Connection connection = DBConnection.getInstance().getConnection();
        int i;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM item WHERE code=?");
            preparedStatement.setString(1,code);
            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (i>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateItem(Item item) {
        Connection connection = DBConnection.getInstance().getConnection();
        int i;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Item SET description = ?, unitPrice = ?, qtyOnHand = ? WHERE code = ?");
            preparedStatement.setObject(1,item.getDescription());
            preparedStatement.setObject(2,item.getPrice());
            preparedStatement.setObject(3,item.getQty());
            preparedStatement.setObject(4,item.getCode());

             i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);


        }
        if (i>0){
            return true;
        }
        return false;
    }

    @Override
    public Item searchItem(String code) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item WHERE code=?");
            preparedStatement.setString(1,code);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Item> getAll() {
        List<Item> list=new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
               list.add(
                       new Item(
                               resultSet.getString(1),
                               resultSet.getString(2),
                               resultSet.getDouble(3),
                               resultSet.getInt(4)
               )) ;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean updateQty(List<OrderDetails> orderD){
        for (OrderDetails orderDetails: orderD){
         boolean isUpdate=  updateQty(orderDetails) ;
            if (!isUpdate){
                return false;
            }
        }
      return true;
    }

    public boolean updateQty(OrderDetails orderD){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE item SET qtyOnHand=qtyOnHand-? WHERE code=?");
            preparedStatement.setInt(1,orderD.getQty());
            preparedStatement.setString(2,orderD.getItemCode());
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
