package service.custom.impl;

import db.DBConnection;
import model.User;
import service.custom.LoginService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServiceimpl implements LoginService {

    private static LoginServiceimpl instance;

    private LoginServiceimpl(){}

    public static LoginServiceimpl getInstance(){
        if (instance==null){
            instance=new LoginServiceimpl();
        }
        return instance;
    }


    @Override
    public User validateUser(String name, String password) {


        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();

          if(resultSet.next()) { return new User(
                  resultSet.getInt(1),
                  resultSet.getString(2),
                  resultSet.getString(3),
                  resultSet.getString(4)
                  );
          }else {return  null;}



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
