package service.custom;

import model.User;

public interface LoginService {
    public User validateUser(String name, String password);
}
