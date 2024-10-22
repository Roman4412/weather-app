package com.pustovalov.weatherapplication.repository;

import com.pustovalov.weatherapplication.entity.User;

import java.util.Optional;

public interface IUserRepository {

    User save(User user);

    Optional<User> findBy(Long id);

    Optional<User> findBy(String login);
}
