package controller.customers;

import db.DBConnection;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerService{
    private static  CustomerController instance;

    private CustomerController(){
    }

    public static CustomerController getInstance(){
        if(instance==null){
            instance=new CustomerController();
        }
        return instance;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        Connection connection = DBConnection.getInstance().getConnection();
        int i;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customer Values(?,?,?,?)");
            preparedStatement.setObject(1,customer.getId());
            preparedStatement.setObject(2,customer.getName());
            preparedStatement.setObject(3,customer.getAddress());
            preparedStatement.setObject(4,customer.getSalary());

             i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

      if(i>0){
          return true;
      }
      return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        Connection connection = DBConnection.getInstance().getConnection();
        int i;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer SET name=?,address=?,salary=? WHERE id=?");
            preparedStatement.setObject(1,customer.getName());
            preparedStatement.setObject(2,customer.getAddress());
            preparedStatement.setObject(3,customer.getSalary());
            preparedStatement.setObject(4,customer.getId());

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
    public Customer searchCustomer(String id) {
        Connection connection = DBConnection.getInstance().getConnection();
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE id=?");
            preparedStatement.setString(1,id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                return new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)
                );


            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public boolean deleteCustomer(String id) {
        Connection connection = DBConnection.getInstance().getConnection();
        int i;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE id=?");
            preparedStatement.setString(1,id);

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
    public List<Customer> getAll() {
        List<Customer>list=new ArrayList<>();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                list.add(
                        new Customer(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDouble(4)
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}
