package com.pustovalov.weatherapplication.dao;

import com.pustovalov.weatherapplication.entity.User;

public interface UserDao {

    User create(User user);

    boolean isExist(User user);
}
