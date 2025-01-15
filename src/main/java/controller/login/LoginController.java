package controller.login;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController implements LoginService{

    private static  LoginController instance;

    private LoginController(){}

    public static LoginController getInstance(){
        if (instance==null){
            instance=new LoginController();
        }
        return instance;
    }


    @Override
    public boolean validateUser(String name, String password) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();

           return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
