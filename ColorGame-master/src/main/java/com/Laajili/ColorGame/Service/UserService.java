package com.Laajili.ColorGame.Service;

import com.Laajili.ColorGame.Model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUser(String username);
    List<User>getUsers();

}
