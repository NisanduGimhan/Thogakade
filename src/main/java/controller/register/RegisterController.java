package controller.register;

import db.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterController implements RegisterService{
    private static RegisterController instance;

    private List<User> users=new ArrayList<>();

    private RegisterController(){}

    public static RegisterController getInstance(){
        if (instance==null){
            instance=new RegisterController();
        }
        return instance;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public boolean addUser( User user) {
        Connection connection = DBConnection.getInstance().getConnection();
        int i;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (username,email,password) VALUES (?,?,?)");
            preparedStatement.setObject(1,user.getUserName());
            preparedStatement.setObject(2,user.getEmail());
            preparedStatement.setObject(3,user.getPassword());

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (i>0){
            return true;
        }
        return false;
    }
}
