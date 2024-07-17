package com.vaibhav.news.Service;

import com.vaibhav.news.Entity.User;

import java.util.List;

public interface UserService {
    boolean saveUser(User user);

    boolean saveExistUser(User user);

    List<User> getAll();

    User getByUsername(String username);

    boolean deleteByUserName(String userName);

    User findByUsername(String username);

    boolean updateUser(User user, String username);
}
