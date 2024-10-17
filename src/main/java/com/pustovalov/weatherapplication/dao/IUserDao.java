package com.pustovalov.weatherapplication.dao;

import com.pustovalov.weatherapplication.entity.User;

import java.util.Optional;

public interface IUserDao {

    User save(User user);

    Optional<User> findBy(long id);
}
