package com.pustovalov.weatherapplication.service;

import com.pustovalov.weatherapplication.dao.UserDao;
import com.pustovalov.weatherapplication.dto.CreateUserFormData;
import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.exception.ObjectAlreadyExistException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import static com.pustovalov.weatherapplication.service.mapper.UserMapper.mapper;

@Getter
@Setter
@RequiredArgsConstructor

@Service
public class UserService {

    private final UserDao userDao;

    public User create(CreateUserFormData createUserFormData) {
        if (createUserFormData == null) {
            throw new IllegalArgumentException("createUserDto cannot be null");
        }

        User user = mapper.toEntity(createUserFormData);

        if (userDao.isExist(user)) {
            throw new ObjectAlreadyExistException(
                    String.format("The user with the login %s already exists", user.getLogin()));
        } else {
            return userDao.create(user);
        }
    }
}