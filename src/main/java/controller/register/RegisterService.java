package controller.register;

import model.User;

import java.util.List;

public interface RegisterService {

    public List<User> getAll();
    public boolean addUser(User user);


}
